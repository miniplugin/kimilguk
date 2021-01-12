package org.edu.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	//로그인 후 세션 처리 매핑
	@RequestMapping(value="/login_success",method=RequestMethod.GET)
	public String login_success(RedirectAttributes rdat) throws Exception {
		
		rdat.addFlashAttribute("msg", "로그인");
		return "redirect:/";
	}
	
	//사용자 홈페이지 로그인 접근 매핑
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() throws Exception{
		//BCrypt암호화 match 메서드으로 확인
		String rawPassword = "user02";
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(10);
		String bcryptPassword = bcryptPasswordEncoder.encode("user02");//예, user02 -> 암호화 처리됨
		//System.out.println(bcryptPassword);
		System.out.println("2가지 스트링을 매칭 참,거짓: " + bcryptPasswordEncoder.matches(rawPassword, bcryptPassword));

		return "home/login";
	}
}
