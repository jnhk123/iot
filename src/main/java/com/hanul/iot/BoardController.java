package com.hanul.iot;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import board.BoardPage;
import board.BoardService;
import board.BoardVO;
import board.CommentVO;
import common.CommonService;
import member.MemberVO;

@Controller @SessionAttributes({"category", "iot_title"})
public class BoardController {
	
	@Autowired BoardPage page;
	@Autowired BoardService service;
	
	//방명록에 대한 댓글 삭제처리 요청
	@ResponseBody
	@RequestMapping("/board/comment/delete/{id}")
	public void comment_delete(@PathVariable int id){
		
		service.comment_delete(id);
		//해당 댓글을 DB에서 삭제한 후
	}
	
	
	//방명록에 대한 댓글 변경저장처리 요청
	@ResponseBody @RequestMapping(value="/board/comment/update",
						produces="application/text; charset=utf-8")
	public String comment_update(@RequestBody CommentVO vo) {
		//데이터객체의 정보를  DB 에 변경저장한다.
		return service.comment_update(vo) ? "성공" : "실패";
	}
	
	
	//방명록에 대한 댓글 목록 요청
	@RequestMapping("/board/comment/{board_id}")
	public String comment_list(@PathVariable int board_id, Model model) {
		//방명록글(원글) 에 대한 정보를  DB에서 조회해 온 후
		model.addAttribute("list", service.comment_list(board_id));
		model.addAttribute("enter", "\r\n");
		//화면에서 출력한다.
		return "board/comment/list";
	}
	
	
	//방명록에 대한 댓글 저장처리 요청
	@ResponseBody @RequestMapping("/board/comment/insert")
	public boolean comment_insert(@RequestParam int board_id,
			@RequestParam String content, HttpSession ss) {
		//화면에서 입력한 정보를 DB에 저장한다.
		HashMap<String, Object> map 
			= new HashMap<String, Object> (); 
		map.put("board_id", board_id);
		map.put("content", content);
		map.put("userid", 
			((MemberVO)ss.getAttribute("login_info")).getUserid());
		return service.comment_insert(map);
	}
	
	
	//방명록 상세화면 요청
	@RequestMapping("/detail.bo")
	public String detail(@RequestParam(defaultValue="0") int read,
						@RequestParam int id, Model model) {
		//공지글 조회수 증가처리
		if( read == 1) 	service.readcnt(id);
		
		//선택한 공지글 정보를 DB에서 조회해온 후
		model.addAttribute("vo", service.detail(id) );
		model.addAttribute("page", page);
		model.addAttribute("enter", "\r\n");
		//상세화면으로 연결
		return "board/detail";
	}
	
	// 방명록목록화면 요청
	@RequestMapping("/list.bo")
	public String list(Model model, 
			@RequestParam(required=false) Integer pagelist,
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(required=false) String search,
			@RequestParam(defaultValue="") String keyword) {
		model.addAttribute("category", "bo");
		model.addAttribute("iot_title", "방명록" );
		
		if( pagelist != null ) page.setPageList(pagelist);
		page.setCurPage(curPage);
		page.setSearch( keyword.isEmpty() ? "" : search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.list(page));
		//목록화면으로 연결
		return "board/list";
	}
	
	@Autowired CommonService common;
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, HttpSession session,
			@RequestParam MultipartFile attach_file) {
		
		//파일업로드처리
		if( attach_file.getSize()>0 ) {
			vo.setFilepath(	common.upload(
							"board", attach_file, session));
			vo.setFilename(attach_file.getOriginalFilename());
		}
		//관리자의 id 를 데이터객체에 담는다.
		vo.setUserid( ((MemberVO)session
						.getAttribute("login_info")).getUserid() );
		//화면에서 입력한 정보를 DB에 저장한 후
		service.insert(vo);
		//목록화면으로 연결
		return "redirect:list.bo";
	}
	
	
	//신규공지글 작성화면 요청
	@RequestMapping("/new.bo")
	public String board() {
		//신규공지글 작성화면으로 연결
		return "board/new";
	}
	
}
