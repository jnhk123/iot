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
	
	//���Ͽ� ���� ��� ����ó�� ��û
	@ResponseBody
	@RequestMapping("/board/comment/delete/{id}")
	public void comment_delete(@PathVariable int id){
		
		service.comment_delete(id);
		//�ش� ����� DB���� ������ ��
	}
	
	
	//���Ͽ� ���� ��� ��������ó�� ��û
	@ResponseBody @RequestMapping(value="/board/comment/update",
						produces="application/text; charset=utf-8")
	public String comment_update(@RequestBody CommentVO vo) {
		//�����Ͱ�ü�� ������  DB �� ���������Ѵ�.
		return service.comment_update(vo) ? "����" : "����";
	}
	
	
	//���Ͽ� ���� ��� ��� ��û
	@RequestMapping("/board/comment/{board_id}")
	public String comment_list(@PathVariable int board_id, Model model) {
		//���ϱ�(����) �� ���� ������  DB���� ��ȸ�� �� ��
		model.addAttribute("list", service.comment_list(board_id));
		model.addAttribute("enter", "\r\n");
		//ȭ�鿡�� ����Ѵ�.
		return "board/comment/list";
	}
	
	
	//���Ͽ� ���� ��� ����ó�� ��û
	@ResponseBody @RequestMapping("/board/comment/insert")
	public boolean comment_insert(@RequestParam int board_id,
			@RequestParam String content, HttpSession ss) {
		//ȭ�鿡�� �Է��� ������ DB�� �����Ѵ�.
		HashMap<String, Object> map 
			= new HashMap<String, Object> (); 
		map.put("board_id", board_id);
		map.put("content", content);
		map.put("userid", 
			((MemberVO)ss.getAttribute("login_info")).getUserid());
		return service.comment_insert(map);
	}
	
	
	//���� ��ȭ�� ��û
	@RequestMapping("/detail.bo")
	public String detail(@RequestParam(defaultValue="0") int read,
						@RequestParam int id, Model model) {
		//������ ��ȸ�� ����ó��
		if( read == 1) 	service.readcnt(id);
		
		//������ ������ ������ DB���� ��ȸ�ؿ� ��
		model.addAttribute("vo", service.detail(id) );
		model.addAttribute("page", page);
		model.addAttribute("enter", "\r\n");
		//��ȭ������ ����
		return "board/detail";
	}
	
	// ���ϸ��ȭ�� ��û
	@RequestMapping("/list.bo")
	public String list(Model model, 
			@RequestParam(required=false) Integer pagelist,
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(required=false) String search,
			@RequestParam(defaultValue="") String keyword) {
		model.addAttribute("category", "bo");
		model.addAttribute("iot_title", "����" );
		
		if( pagelist != null ) page.setPageList(pagelist);
		page.setCurPage(curPage);
		page.setSearch( keyword.isEmpty() ? "" : search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.list(page));
		//���ȭ������ ����
		return "board/list";
	}
	
	@Autowired CommonService common;
	//�ű԰����� ����ó�� ��û
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, HttpSession session,
			@RequestParam MultipartFile attach_file) {
		
		//���Ͼ��ε�ó��
		if( attach_file.getSize()>0 ) {
			vo.setFilepath(	common.upload(
							"board", attach_file, session));
			vo.setFilename(attach_file.getOriginalFilename());
		}
		//�������� id �� �����Ͱ�ü�� ��´�.
		vo.setUserid( ((MemberVO)session
						.getAttribute("login_info")).getUserid() );
		//ȭ�鿡�� �Է��� ������ DB�� ������ ��
		service.insert(vo);
		//���ȭ������ ����
		return "redirect:list.bo";
	}
	
	
	//�ű԰����� �ۼ�ȭ�� ��û
	@RequestMapping("/new.bo")
	public String board() {
		//�ű԰����� �ۼ�ȭ������ ����
		return "board/new";
	}
	
}
