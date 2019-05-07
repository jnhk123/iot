package com.hanul.iot;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import common.CommonService;
import member.MemberService;
import member.MemberVO;

@Controller @SessionAttributes({"category", "iot_title"})
public class MemberController {

	@Autowired private MemberService service;
	
	//로그아웃처리 요청
	@ResponseBody @RequestMapping("/logout")
	public void logout(HttpSession session) {
		//세션에 있는 로그인정보를 삭제한 후
		session.removeAttribute("login_info");
		//요청한 곳으로 돌아간다.
	}
	
	//로그인처리 요청
	@ResponseBody @RequestMapping("/login")
	public Boolean login(@RequestParam String userid,
						@RequestParam String userpwd,
						HttpSession session) {
		//아이디,비밀번호가 DB 에 일치하는 회원정보를 조회한 후
		HashMap<String, String> map
			= new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpwd", userpwd);
		MemberVO vo = service.login(map);
		//일치하는 회원정보가 있다면
		if( vo != null )
			session.setAttribute("login_info", vo);
		
		//로그인여부(true/false)를 요청한 화면으로 보낸다.
		return (vo == null ? false : true);
	}
	
	@Autowired private CommonService common;
	
	//회원가입처리 요청
	@ResponseBody @RequestMapping(value="/join",
					produces="text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session) {
		//화면에서 입력한 회원정보를  DB에 저장한 후
		String msg = "<script type='text/javascript'>";
		if( service.insert(vo) ) {
			//기본SimpleEmail 인 경우
//			common.emailSend(vo.getEmail(), vo.getName());
			//파일첨부하는 경우
			common.emailSend(vo.getEmail(), vo.getName(), session);
			
			msg += "alert('회원가입을 축하! ^^'); location='home'";
		}else {
			msg += "alert('회원가입 실패! ㅠㅠ'); history.go(-2)";
//			msg += "alert('회원가입 실패! ㅠㅠ'); location='member'";
		}
		msg += "</script>";
		
		//홈화면으로 연결
		return msg;
	}
	
	//아이디 중복확인 요청
	@ResponseBody @RequestMapping("/id_check")
	public String id_check(@RequestParam String userid) {
		//화면에서 입력한 아이디가 DB에 있는지를 조회한 후
		//사용가능여부를 반환
		return String.valueOf( service.id_check(userid) );
	}
	
	//회원가입화면 요청
	@RequestMapping("/member")
	public String member(Model model) {
		model.addAttribute("category", "");
		model.addAttribute("iot_title", "회원가입" );
		return "member/join";
	}
}










