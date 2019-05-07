package com.hanul.iot;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.CommonService;
import member.MemberVO;
import notice.NoticePage;
import notice.NoticeService;
import notice.NoticeVO;

@Controller @SessionAttributes({"category", "iot_title"})
public class NoticeController {
	@Autowired private NoticeService service;

	//�������ó�� ��û
	@RequestMapping("/reply_insert.no")
	public String reply_insert(NoticeVO vo, HttpSession session,
			@RequestParam MultipartFile attach_file,
			RedirectAttributes redirect ) {

		//��۾���ȭ�鿡�� �Է��� ������ DB�� ������ ��
		if( attach_file.getSize() > 0 ) {
			vo.setFilename( attach_file.getOriginalFilename() );
			vo.setFilepath( 
				common.upload("notice", attach_file, session) );
		}
		service.reply_insert(vo);
		//���ȭ������ ����
		redirect.addAttribute("curPage", page.getCurPage());
		redirect.addAttribute("search", page.getSearch());
		redirect.addAttribute("keyword", page.getKeyword());
		return "redirect:list.no";
	}	
	
	//��۾���ȭ�� ��û
	@RequestMapping("/reply.no")
	public String reply(Model model, @RequestParam int id) {
		//������ ������ ��ȸ�� ��
		model.addAttribute("vo", service.detail(id));
		//��۾���ȭ������ ����
		return "notice/reply";
	}
	
	//÷������ �ٿ�ε� ��û
	@ResponseBody @RequestMapping("/download.no")
	public File download(@RequestParam int id,
			HttpSession session, HttpServletResponse response) {
		//������ ���� ÷������ ������ ��ȸ�� ��
		NoticeVO vo = service.detail(id);
		
		//�ش� ��ġ���� ������ �ٿ�ε��Ѵ�.
		return common.download(vo.getFilepath(), vo.getFilename(),
						session, response);
	}
	
	//�����۾ȳ� ����ó�� ��û
	@RequestMapping("/delete.no")
	public String delete(int id, HttpSession session) {
		//÷�ε� ������ �־��ٸ� ���ϻ���
		File f = new File( session.getServletContext()
				.getRealPath("resources") 
				+ service.detail(id).getFilepath() );
		if( f.exists() ) f.delete();
		
		service.delete(id);
		return "redirect:list.no";
	}
	
	//�����۾ȳ� ��������ó�� ��û
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, @RequestParam String attach,
						@RequestParam MultipartFile file,
						HttpSession session) {
		//���������� ���� ������������ ��ȸ�ؿ´�.
		NoticeVO notice = service.detail( vo.getId() );
		String uuid 
			= session.getServletContext().getRealPath("resources")
		 	+ notice.getFilepath();
		
		//1. ����÷�ε� ������ �������� ������ ÷���ϴ� ���
		//   ����÷�ε� ������ �־��µ� ������ �����ؼ� ÷���� ���
		if( file.getSize()>0 ) {
			//÷���� ������ ������ ���ε��Ѵ�.
			vo.setFilepath( common.upload("notice", file, session) );
			vo.setFilename( file.getOriginalFilename() );
			
			//���� ÷�ε� ������ �־��ٸ� �ش� ������ ����
			File f = new File( uuid );
			if( f.exists() ) f.delete();
		}else {
			//����÷�ε� ������ �־��µ� ������ �����ϴ� ���
			if( attach.equals("n") ) {
				//������ ���ε�Ǿ� �ִ� ������ ����
				File f = new File( uuid );
				if( f.exists() ) f.delete();
			}else {
			//����÷�ε� ������ �ִ��� ������ 
			//���� ���������� �״�� ����ϴ� ���
				vo.setFilename( notice.getFilename() );
				vo.setFilepath( notice.getFilepath() );
			}
		}
		
		//ȭ�鿡�� �����Է��� ������  DB�� ��������ó���� ��
		service.update(vo);
		//��ȭ������ ����
		return "redirect:detail.no?id=" + vo.getId();
	}
	
	//�����۾ȳ� ����ȭ�� ��û
	@RequestMapping("/modify.no")
	public String modify(@RequestParam int id, Model model) {
		//������ ������ ������ DB���� ��ȸ�ؿ� ��
		model.addAttribute("vo", service.detail(id) );
		//����ȭ������ ����
		return "notice/modify";
	}
	
	//�����۾ȳ� ��ȭ�� ��û
	@RequestMapping("/detail.no")
	public String detail(@RequestParam(defaultValue="0") int read,
						@RequestParam int id, Model model) {
		//������ ��ȸ�� ����ó��
		if( read == 1) 	service.read(id);
		
		//������ ������ ������ DB���� ��ȸ�ؿ� ��
		model.addAttribute("vo", service.detail(id) );
		model.addAttribute("page", page);
		model.addAttribute("enter", "\r\n");
		//��ȭ������ ����
		return "notice/detail";
	}
	
	@Autowired private CommonService common;
	
	//�ű԰����� ����ó�� ��û
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo, HttpSession session,
			@RequestParam MultipartFile attach_file) {
		
		//���Ͼ��ε�ó��
		if( attach_file.getSize()>0 ) {
			vo.setFilepath(	common.upload(
							"notice", attach_file, session));
			vo.setFilename(attach_file.getOriginalFilename());
		}
		//�������� id �� �����Ͱ�ü�� ��´�.
		vo.setWriter( ((MemberVO)session
						.getAttribute("login_info")).getUserid() );
		//ȭ�鿡�� �Է��� ������ DB�� ������ ��
		service.insert(vo);
		//���ȭ������ ����
		return "redirect:list.no";
	}
	
	//�ű԰����� �ۼ�ȭ�� ��û
	@RequestMapping("/new.no")
	public String notice() {
		//�ű԰����� �ۼ�ȭ������ ����
		return "notice/new";
	}
	
	@Autowired private NoticePage page;
	
	//�����۸��ȭ�� ��û
	@RequestMapping("/list.no")
	public String list(Model model, 
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(required=false) String search,
			@RequestParam(defaultValue="") String keyword) {
		model.addAttribute("category", "no");
		model.addAttribute("iot_title", "��������" );
		
		page.setCurPage(curPage);
		page.setSearch( keyword.isEmpty() ? "" : search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.list(page));
		/*	
	  	if( keyword.isEmpty() )
			//DB���� �����۸���� ��ȸ�� ��
			model.addAttribute( "list", service.list() );
		else {
			HashMap<String, String> map
				= new HashMap<String, String>();
			map.put("search", search);
			map.put("keyword", keyword);
			model.addAttribute( "list", service.list(map) );
			model.addAttribute("search", search);
			model.addAttribute("keyword", keyword);
		}
		*/
		//���ȭ������ ����
		return "notice/list";
	}
}











