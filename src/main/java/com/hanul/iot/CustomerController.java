package com.hanul.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import customer.CustomerServiceImpl;
import customer.CustomerVO;

@Controller @SessionAttributes({"category", "iot_title"})
public class CustomerController {
	@Autowired private CustomerServiceImpl service;

	//고객정보 삭제처리 요청
	@RequestMapping("/delete.cs")
	public String delete(@RequestParam int id) {
		//선택한 고객정보를 DB에서 삭제한 후
		service.delete(id);
		//목록화면으로 연결
		return "redirect:list.cs";
	}
	
	//고객정보 수정저장처리 요청
	@RequestMapping("/update.cs")
	public String update(CustomerVO vo) {
		//화면에서 변경입력한 정보를 DB 에 변경저장처리한 후
		service.update(vo);
		//상세화면으로 연결
		return "redirect:detail.cs?id=" + vo.getId();
	}
	
	//고객정보수정화면 요청
	@RequestMapping("/modify.cs")
	public String modify(Model model, @RequestParam int id) {
		//선택한 고객의 정보를 DB에서 조회한 후
		model.addAttribute("vo", service.detail(id));
		//수정화면으로 연결
		return "customer/modify";
	}
	
	//고객상세정보화면 요청
	@RequestMapping("/detail.cs")
	public String detail(@RequestParam int id, Model model) {
		//선택한 고객의 정보를 DB에서 조회한 후
		model.addAttribute("vo", service.detail(id));
		//상세화면으로 연결
		return "customer/detail";
	}
	
	
	//신규고객정보 저장처리 요청
	@RequestMapping("/insert.cs")
	public String insert(CustomerVO vo) {
		//화면에서 입력한 정보를 수집해
		//DB에 저장한 후
		service.insert(vo);
		//목록화면으로 연결
		return "redirect:list.cs";
	}
	
	
	//신규고객등록화면 요청
	@RequestMapping("/new.cs")
	public String customer() {
		//신규고객등록화면으로 연결
		return "customer/new";
	}
	
	//고객목록화면 요청
	@RequestMapping("/list.cs")
	public String list(Model model) {
		model.addAttribute("category", "cs");
		model.addAttribute("iot_title", "고객관리" );
		//DB 에서 고객정보를 조회한 후
		model.addAttribute("list", service.list() );
		
		
		//화면으로 연결
		return "customer/list";
	}
	
}
