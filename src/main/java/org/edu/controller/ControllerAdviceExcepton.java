package org.edu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //컨트롤러에서 발생되는 CRUD액션을 중간에 가로채는 기능클래스 명시
public class ControllerAdviceExcepton {
	
	//ModelAndView클래스는 jsp로 반환시 값을 저장하는 역할
	//언제 아래 메서드가 실행될지 명시하는 클래스(아래)
	@ExceptionHandler(Exception.class) //컨트롤러에서 예외(에러)가 발생했을때 아래 메서드를 자동실행
	public ModelAndView errorModelAndView(Exception ex, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		//AdminController에서 사용한 Model클래스는 jsp로 값을 보낼때 변수.
		//여기서 사용하는 ModelAndView클래스는 jsp로 값을 보내고 + model값을 보낼 jsp경로도 지정
		//모델 값을 보낼 jsp경로지정하는 명령은 셋뷰네임 입니다.
		modelAndView.setViewName("home/error");//반환할 jsp위치 지정.
		
		return null;
	}
}
