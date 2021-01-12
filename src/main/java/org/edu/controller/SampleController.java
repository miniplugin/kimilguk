package org.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SampleController {
	/**
	 * 외부(웹브라우저)에서 루트 / 라는 이름으로 요청 받으면, home.jsp파일에 화면출력(렌더링)이 됩니다.(아래)
	 * HomeController.java 를 저장하면, 이클립스에서는 HomeController.class 실행가능한 파일을 생성
	 * 그래서, 저장시 콘솔에 보시면, Reloading Context with... is completed 재실행 되었다고 나옵니다.
	 * 그래서, .java 클래스는 수정 후 약간 기다린 후 Reloading... 메시지 후 결과 확인이 가능합니다.
	 */
	@RequestMapping(value="/sample/contact",method=RequestMethod.POST)//포스트방식은 데이터를 전송하는역할
	public String contact_send() {
		//데이터 전송후에 다른 페이지이동이 필요합니다. 새로고침=자동등록을 방지하기 위해서(게시판테러방지용)
		return "redirect:/sample/blog";//URL경로를 사용.
	}
	@RequestMapping(value="/sample/contact",method=RequestMethod.GET)//겟방식은 폼페이지를 보여주는 역할
	public String contact() {
		return "sample/contact";
	}
	@RequestMapping(value="/sample/blog",method=RequestMethod.GET)
	public String blog() {
		return "sample/blog";
	}
	@RequestMapping(value="/sample/work",method=RequestMethod.GET)
	public String work() {
		return "sample/work";
	}
	@RequestMapping(value="/sample/weare",method=RequestMethod.GET)
	public String weare() {
		//외부에서 /weare경로로 접근했을때, sample/weare.jsp와 매핑시키라는 의미.
		return "sample/weare";
	}
	@RequestMapping(value="/sample",method=RequestMethod.GET)//여기가 절대경로
	public String index() { //메서드(함수)구성: 리턴(반환값출력)형태지정 메서드명(매개변수) {구현내용} String index(){}
		//해석:상단 리퀘스트매핑에서 지정한 경로로 접근을 하게되면, 아래에 있는 index()메서드를 실행해라.
		//메서드명 제일 앞에있는 public는 접근권한에 대한 키워드인데요, public(공공-다른 클래스에서 접근가능)
		//, private(내부-현재 HomeController클래스에서만 접근가능)
		// public String index() { } 자바(스프링) 메서드 기본형태
		// $(document).ready(function(){ }); j쿼리 기본형태
		//리턴반환값으로 사용한 String 클래스는 문자열을 반환하고, 
		//여기서는 index.jsp와 매칭되는 문자열을 반환. 상대경로가 필요합니다.
		//스프링 MVC프로젝트에서 상대경로의 최상위는 views폴더 최상위 root폴더 입니다.
		// 접두사(prefix), 접미사(suffix) index(접두사-파일명).jsp(접미사-확장자)
		return "sample/index";//접두사만 반환값으로 넣습니다. 상대경로.
	}
}
