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

	//답글저장처리 요청
	@RequestMapping("/reply_insert.no")
	public String reply_insert(NoticeVO vo, HttpSession session,
			@RequestParam MultipartFile attach_file,
			RedirectAttributes redirect ) {

		//답글쓰기화면에서 입력한 정보를 DB에 저장한 후
		if( attach_file.getSize() > 0 ) {
			vo.setFilename( attach_file.getOriginalFilename() );
			vo.setFilepath( 
				common.upload("notice", attach_file, session) );
		}
		service.reply_insert(vo);
		//목록화면으로 연결
		redirect.addAttribute("curPage", page.getCurPage());
		redirect.addAttribute("search", page.getSearch());
		redirect.addAttribute("keyword", page.getKeyword());
		return "redirect:list.no";
	}	
	
	//답글쓰기화면 요청
	@RequestMapping("/reply.no")
	public String reply(Model model, @RequestParam int id) {
		//원글의 정보를 조회한 후
		model.addAttribute("vo", service.detail(id));
		//답글쓰기화면으로 연결
		return "notice/reply";
	}
	
	//첨부파일 다운로드 요청
	@ResponseBody @RequestMapping("/download.no")
	public File download(@RequestParam int id,
			HttpSession session, HttpServletResponse response) {
		//선택한 글의 첨부파일 정보를 조회한 후
		NoticeVO vo = service.detail(id);
		
		//해당 위치에서 파일을 다운로드한다.
		return common.download(vo.getFilepath(), vo.getFilename(),
						session, response);
	}
	
	//공지글안내 삭제처리 요청
	@RequestMapping("/delete.no")
	public String delete(int id, HttpSession session) {
		//첨부된 파일이 있었다면 파일삭제
		File f = new File( session.getServletContext()
				.getRealPath("resources") 
				+ service.detail(id).getFilepath() );
		if( f.exists() ) f.delete();
		
		service.delete(id);
		return "redirect:list.no";
	}
	
	//공지글안내 수정저장처리 요청
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, @RequestParam String attach,
						@RequestParam MultipartFile file,
						HttpSession session) {
		//파일정보를 위해 공지글정보를 조회해온다.
		NoticeVO notice = service.detail( vo.getId() );
		String uuid 
			= session.getServletContext().getRealPath("resources")
		 	+ notice.getFilepath();
		
		//1. 원래첨부된 파일이 없었으나 파일을 첨부하는 경우
		//   원래첨부된 파일이 있었는데 파일을 변경해서 첨부한 경우
		if( file.getSize()>0 ) {
			//첨부한 파일을 서버에 업로드한다.
			vo.setFilepath( common.upload("notice", file, session) );
			vo.setFilename( file.getOriginalFilename() );
			
			//원래 첨부된 파일이 있었다면 해당 파일은 삭제
			File f = new File( uuid );
			if( f.exists() ) f.delete();
		}else {
			//원래첨부된 파일이 있었는데 파일을 삭제하는 경우
			if( attach.equals("n") ) {
				//서버에 업로드되어 있는 파일은 삭제
				File f = new File( uuid );
				if( f.exists() ) f.delete();
			}else {
			//원래첨부된 파일이 있던지 없던간 
			//원래 파일정보를 그대로 사용하는 경우
				vo.setFilename( notice.getFilename() );
				vo.setFilepath( notice.getFilepath() );
			}
		}
		
		//화면에서 변경입력한 정보를  DB에 변경저장처리한 후
		service.update(vo);
		//상세화면으로 연결
		return "redirect:detail.no?id=" + vo.getId();
	}
	
	//공지글안내 수정화면 요청
	@RequestMapping("/modify.no")
	public String modify(@RequestParam int id, Model model) {
		//선택한 공지글 정보를 DB에서 조회해온 후
		model.addAttribute("vo", service.detail(id) );
		//수정화면으로 연결
		return "notice/modify";
	}
	
	//공지글안내 상세화면 요청
	@RequestMapping("/detail.no")
	public String detail(@RequestParam(defaultValue="0") int read,
						@RequestParam int id, Model model) {
		//공지글 조회수 증가처리
		if( read == 1) 	service.read(id);
		
		//선택한 공지글 정보를 DB에서 조회해온 후
		model.addAttribute("vo", service.detail(id) );
		model.addAttribute("page", page);
		model.addAttribute("enter", "\r\n");
		//상세화면으로 연결
		return "notice/detail";
	}
	
	@Autowired private CommonService common;
	
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo, HttpSession session,
			@RequestParam MultipartFile attach_file) {
		
		//파일업로드처리
		if( attach_file.getSize()>0 ) {
			vo.setFilepath(	common.upload(
							"notice", attach_file, session));
			vo.setFilename(attach_file.getOriginalFilename());
		}
		//관리자의 id 를 데이터객체에 담는다.
		vo.setWriter( ((MemberVO)session
						.getAttribute("login_info")).getUserid() );
		//화면에서 입력한 정보를 DB에 저장한 후
		service.insert(vo);
		//목록화면으로 연결
		return "redirect:list.no";
	}
	
	//신규공지글 작성화면 요청
	@RequestMapping("/new.no")
	public String notice() {
		//신규공지글 작성화면으로 연결
		return "notice/new";
	}
	
	@Autowired private NoticePage page;
	
	//공지글목록화면 요청
	@RequestMapping("/list.no")
	public String list(Model model, 
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(required=false) String search,
			@RequestParam(defaultValue="") String keyword) {
		model.addAttribute("category", "no");
		model.addAttribute("iot_title", "공지사항" );
		
		page.setCurPage(curPage);
		page.setSearch( keyword.isEmpty() ? "" : search);
		page.setKeyword(keyword);
		model.addAttribute("page", service.list(page));
		/*	
	  	if( keyword.isEmpty() )
			//DB에서 공지글목록을 조회한 후
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
		//목록화면으로 연결
		return "notice/list";
	}
}











