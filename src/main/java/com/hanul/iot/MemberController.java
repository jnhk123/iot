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
	
	//�α׾ƿ�ó�� ��û
	@ResponseBody @RequestMapping("/logout")
	public void logout(HttpSession session) {
		//���ǿ� �ִ� �α��������� ������ ��
		session.removeAttribute("login_info");
		//��û�� ������ ���ư���.
	}
	
	//�α���ó�� ��û
	@ResponseBody @RequestMapping("/login")
	public Boolean login(@RequestParam String userid,
						@RequestParam String userpwd,
						HttpSession session) {
		//���̵�,��й�ȣ�� DB �� ��ġ�ϴ� ȸ�������� ��ȸ�� ��
		HashMap<String, String> map
			= new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpwd", userpwd);
		MemberVO vo = service.login(map);
		//��ġ�ϴ� ȸ�������� �ִٸ�
		if( vo != null )
			session.setAttribute("login_info", vo);
		
		//�α��ο���(true/false)�� ��û�� ȭ������ ������.
		return (vo == null ? false : true);
	}
	
	@Autowired private CommonService common;
	
	//ȸ������ó�� ��û
	@ResponseBody @RequestMapping(value="/join",
					produces="text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session) {
		//ȭ�鿡�� �Է��� ȸ��������  DB�� ������ ��
		String msg = "<script type='text/javascript'>";
		if( service.insert(vo) ) {
			//�⺻SimpleEmail �� ���
//			common.emailSend(vo.getEmail(), vo.getName());
			//����÷���ϴ� ���
			common.emailSend(vo.getEmail(), vo.getName(), session);
			
			msg += "alert('ȸ�������� ����! ^^'); location='home'";
		}else {
			msg += "alert('ȸ������ ����! �Ф�'); history.go(-2)";
//			msg += "alert('ȸ������ ����! �Ф�'); location='member'";
		}
		msg += "</script>";
		
		//Ȩȭ������ ����
		return msg;
	}
	
	//���̵� �ߺ�Ȯ�� ��û
	@ResponseBody @RequestMapping("/id_check")
	public String id_check(@RequestParam String userid) {
		//ȭ�鿡�� �Է��� ���̵� DB�� �ִ����� ��ȸ�� ��
		//��밡�ɿ��θ� ��ȯ
		return String.valueOf( service.id_check(userid) );
	}
	
	//ȸ������ȭ�� ��û
	@RequestMapping("/member")
	public String member(Model model) {
		model.addAttribute("category", "");
		model.addAttribute("iot_title", "ȸ������" );
		return "member/join";
	}
}










