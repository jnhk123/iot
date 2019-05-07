package com.hanul.iot;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller @SessionAttributes("category")
public class HomeController {
	

	@RequestMapping("/error")
	public String error(Model model, HttpServletRequest req) {
		int code = (Integer)req.getAttribute("javax.servlet.error.status_code");
		
		//�߻��� ������ ���� �ľ�
		Throwable err 
			= (Throwable) req.getAttribute("javax.servlet.error.exception");
		StringBuilder msg = new StringBuilder();
		msg.append("<p>");
		while( err != null) {
			msg.append( err.getMessage() + "<br>" );
			err = err.getCause();
		}
		msg.append("</p>");
		
		model.addAttribute("msg", msg.toString());
		model.addAttribute("iot_title", code==404 ? "�������� ã�� �� �����ϴ�." : "���ο���");
		return "error/" + (code == 404 ? "404" : "default");
	}
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/", "home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("category", "");
		model.addAttribute("iot_title", "�����غ��ô�~" );
		return "home";
	}
	
}
