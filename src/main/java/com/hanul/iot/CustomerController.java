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

	//������ ����ó�� ��û
	@RequestMapping("/delete.cs")
	public String delete(@RequestParam int id) {
		//������ �������� DB���� ������ ��
		service.delete(id);
		//���ȭ������ ����
		return "redirect:list.cs";
	}
	
	//������ ��������ó�� ��û
	@RequestMapping("/update.cs")
	public String update(CustomerVO vo) {
		//ȭ�鿡�� �����Է��� ������ DB �� ��������ó���� ��
		service.update(vo);
		//��ȭ������ ����
		return "redirect:detail.cs?id=" + vo.getId();
	}
	
	//����������ȭ�� ��û
	@RequestMapping("/modify.cs")
	public String modify(Model model, @RequestParam int id) {
		//������ ���� ������ DB���� ��ȸ�� ��
		model.addAttribute("vo", service.detail(id));
		//����ȭ������ ����
		return "customer/modify";
	}
	
	//��������ȭ�� ��û
	@RequestMapping("/detail.cs")
	public String detail(@RequestParam int id, Model model) {
		//������ ���� ������ DB���� ��ȸ�� ��
		model.addAttribute("vo", service.detail(id));
		//��ȭ������ ����
		return "customer/detail";
	}
	
	
	//�ű԰����� ����ó�� ��û
	@RequestMapping("/insert.cs")
	public String insert(CustomerVO vo) {
		//ȭ�鿡�� �Է��� ������ ������
		//DB�� ������ ��
		service.insert(vo);
		//���ȭ������ ����
		return "redirect:list.cs";
	}
	
	
	//�ű԰����ȭ�� ��û
	@RequestMapping("/new.cs")
	public String customer() {
		//�ű԰����ȭ������ ����
		return "customer/new";
	}
	
	//�����ȭ�� ��û
	@RequestMapping("/list.cs")
	public String list(Model model) {
		model.addAttribute("category", "cs");
		model.addAttribute("iot_title", "������" );
		//DB ���� �������� ��ȸ�� ��
		model.addAttribute("list", service.list() );
		
		
		//ȭ������ ����
		return "customer/list";
	}
	
}
