package com.hanul.iot;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import visual.VisualService;

@Controller @SessionAttributes({"category", "iot_title"})
public class VisualizationController {
	
	@Autowired private VisualService service;
	
	@ResponseBody @RequestMapping("/visual/top3/{gubun}")
	public List<HashMap<String, Object>> top3(@PathVariable String gubun) {
		return gubun.equals("year") ? service.year_top3() : service.month_top3();
	}
	
	//년도별/월별 채용인원 조회 요청
	@ResponseBody @RequestMapping("/visual/recruit/{gubun}")
	public List<HashMap<String, Object>> recruit(@PathVariable String gubun) {
		if( gubun.equals("year") )
			return service.year_recruit();
		else
			return service.month_recruit();
	}
	
	//부서별 사원정보
	@RequestMapping("/employees.vi")
	public String employees(Model model) {
		model.addAttribute("category", "vi");
		model.addAttribute("iot_title", "데이터시각화");
		model.addAttribute("info",service.department_count() );
		
		return "visual/employees";
	}
	
//	채용인원 조회 화면 요청
	@RequestMapping("/recruitment.vi")
	public String recruitment() {
		return "visual/recruitment";
	}
}
