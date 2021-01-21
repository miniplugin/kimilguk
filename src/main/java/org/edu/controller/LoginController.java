package org.edu.controller;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.edu.service.IF_MemberService;
import org.edu.vo.MemberVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
public class LoginController {
	
	@Inject
	private IF_MemberService memberService;
	
	@Inject
	private NaverLoginController naverLoginController;
	
	//로그인 후 세션 처리 매핑 - 네이버 아이디 로그인 로직일때
	//session(인증토큰정보),state(유효성검증용UUID정보),code(인증성공/실패 코드 예,00 OK/01 Error )
	@RequestMapping(value="/login_callback",method= {RequestMethod.GET, RequestMethod.POST})
	public String login_callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, RedirectAttributes rdat) throws IOException, ParseException {
		
		OAuth2AccessToken oauthToken;//토큰으로 시용할 변수 선언
		//NaverLoginController 의 메서드 호출(아래)
		oauthToken = naverLoginController.getAccessToken(session, code, state);
		//네이버로 로그인 한 사용자 정보(profile)을 읽어온다(아래)
		String apiResult = naverLoginController.getUserProfile(oauthToken);//이름,이메일 자료
		//위 String형 apiResult 값을 json형태로 파싱 합니다.(아래)
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);//apiResult문자열값 -> HashMap<키:값> Json형태로 변환
		JSONObject jsonObj = (JSONObject) obj;//jsonObj 파싱한 1차 데이터
		/* Json데이터 구조 */
		/** apiResult json 구조
	    {"resultcode":"00",
	    "message":"success",
	    "response":{"id":"33666449","nickname":"shinn****"
	    ,"age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}
	    }
	    **/
		//위 1차 데이터를 response 키로 분리한 값(아래)
		JSONObject response_obj = (JSONObject) jsonObj.get("response");
		//위 response_obj 파싱(분리) 아래
		
		rdat.addFlashAttribute("msg", "네이버 아이디 로그인");
		return "redirect:/";
	}
	
	//로그인 후 세션 처리 매핑 - 스프링 시큐리티 로그인 로직일때
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
