package org.edu.controller;

import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.edu.service.IF_MemberService;
import org.edu.vo.MemberVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@Inject
	private IF_MemberService memberService;
	
	//로그인 후 세션 처리 매핑
	@RequestMapping(value="/login_success",method=RequestMethod.GET)
	public String login_success(HttpServletRequest request, RedirectAttributes rdat) throws Exception {
		//이미 인증이 마치고 진입한 메서드 그래서, 아래 authentication변수에는 인증정보가 들어 있습니다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userid = "";//로그인아이디가 들어갈 변수 선언
		String levels = "";//권한이 들어갈 변수 선언
		Boolean enabled = false;
		Object principal = authentication.getPrincipal();
		if(principal instanceof UserDetails) {
			//인증이 처리되는 로직
			enabled = ((UserDetails)principal).isEnabled();
		}
		//세션 정보(로그인아이디,레벨,회원이름..) 저장 시작(아래)
		HttpSession session = request.getSession();//진입 전 단계에서 발생한 세션(로그인 절차로 생생된 세션)가져오는 절차
		if(enabled) { //인증처리가 true라면 아래에서 세션 등록 시작
			//자바8이상에서만 지원되는 람다식 사용해서 DB쿼리에서 GET값 getAuthority()비교구문 처리 levels변수에 권한값 지정.
			Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
			if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ANONYMOUS")).findAny().isPresent())
			{levels = "ROLE_ANONYMOUS";}
			if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_USER,")).findAny().isPresent())
			{levels = "ROLE_USER,";}
			if(authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent())
			{levels = "ROLE_ADMIN";}
			//사용자 아이디 값 지정.
			userid = ((UserDetails)principal).getUsername();
			//로그인 세션 저장시작: 사용처는 jsp뷰단,java클래스 모두 사용가능
			session.setAttribute("session_enabled", enabled);
			session.setAttribute("session_userid", userid);
			session.setAttribute("session_levels", levels);
			//상단까지의 세션변수는 스프링시큐리티에서 기본 제공하는 변수
			//하단부터는 비지니스 로직에 따라서 개발쪽에서 발생시키는 세션변수 시작
			MemberVO memberVO = memberService.readMember(userid);
			session.setAttribute("session_username", memberVO.getUser_name());
		}
		rdat.addFlashAttribute("msg", "로그인");
		return "redirect:/";
	}
	
	//사용자 홈페이지 로그인 접근 매핑
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() throws Exception{
		//BCrypt암호화 match 메서드으로 확인
		String rawPassword = "1234";
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(10);
		String bcryptPassword = bcryptPasswordEncoder.encode("1234");//예, user02 -> 암호화 처리됨
		System.out.println(bcryptPassword);//테스트시 이 암호를 DB회원테이블에 입력하시면 됩니다. 
		System.out.println("2가지 스트링을 매칭 참,거짓: " + bcryptPasswordEncoder.matches(rawPassword, bcryptPassword));

		return "home/login";
	}
}
