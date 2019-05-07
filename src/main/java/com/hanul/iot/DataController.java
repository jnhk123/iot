package com.hanul.iot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import data.DataService;

@Controller @SessionAttributes({"category", "iot_title"})
public class DataController {
	private String key = "FPgj2NXbJw46TcGkmAfZEiYFDbxilys7KLjk3KaB7AfeJE00ZhPNM0M8unwbsI69fSmT8SNfVEimE6ZZ2U14hA%3D%3D"; 
	@Autowired private DataService service;
	
	private String animal_url
		="http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/";
	
	//보호소 조회 요청
	@RequestMapping("/data/animal/shelter") @ResponseBody
	public ArrayList<HashMap<String, Object>> animal_shelter(
			@RequestParam String sido, @RequestParam String sigungu
			){
		StringBuilder url = new StringBuilder( animal_url + "shelter");
		url.append("?ServiceKey=" + key);
		url.append("&_type=json");
		url.append("&upr_cd=" + sido);
		url.append("&org_cd=" + sigungu);
		return service.json_list(url);
	}
	
	//선택한 축종에 해당하는 품종조회 요청
	@ResponseBody @RequestMapping("/data/animal/kind")
	public ArrayList<HashMap<String, Object>> animal_kind(
			@RequestParam String upkind ){
		StringBuilder url = new StringBuilder( animal_url + "kind"); 
		url.append("?ServiceKey=" + key);
		url.append("&_type=json");
		url.append("&up_kind_cd=" + upkind);
		return service.json_list(url);
	}
	
	//시군구 조회 요청
	@ResponseBody @RequestMapping("data/animal/sigungu")
	public ArrayList<HashMap<String, Object>> animail_sigungu(@RequestParam String sido) {
		StringBuilder url = new StringBuilder( animal_url + "sigungu" );
		url.append("?ServiceKey=" + key);
		url.append("&_type=json");
		url.append("&upr_cd="+ sido);
		return service.json_list(url);
	}
	
	//유기동물조회 요청
	@ResponseBody @RequestMapping("data/animal/list")
	public ArrayList<HashMap<String, Object>> animal_list( @RequestBody HashMap<String, String> map ) {
		StringBuilder url = new StringBuilder( animal_url + "abandonmentPublic" );
		url.append("?ServiceKey=" + key);
		url.append("&_type=json");
		url.append("&upr_cd=" + map.get("sido"));
		url.append("&org_cd=" + map.get("sigungu"));
		url.append("&care_reg_no=" + map.get("shelter"));
		url.append("&upkind=" + map.get("upkind"));
		url.append("&kind=" + map.get("kind"));
		return service.json_list(url);
	}
	
	//유기동물 시도 요청
	@ResponseBody @RequestMapping(value="/data/animal/sido",
			produces = "application/text; charset=utf-8")
	public String animal_sido() {
		StringBuilder url = new StringBuilder( animal_url + "sido" );
		url.append("?ServiceKey=" + key);
		return service.xml_list(url);
	}
	
	//약국정보 조회 요청
	@ResponseBody @RequestMapping(value="/data/pharmacy",
			produces="application/text; charset=utf-8")
	public String pharmacy_list() {
		StringBuilder url = new StringBuilder(
				"http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?ServiceKey="+ key);
		url.append("&pageNo=1");
		return service.xml_list(url);
	}
	
	//공공데이터 조회화면 요청
	@RequestMapping("/list.pd")
	public String list(Model model ) {
		model.addAttribute("category", "pd");
		model.addAttribute("iot_title","공공데이터");
		return "data/list";
	}
}
