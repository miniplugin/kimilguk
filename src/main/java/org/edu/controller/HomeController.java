package org.edu.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.edu.service.IF_BoardService;
import org.edu.util.CommonController;
import org.edu.util.SecurityCode;
import org.edu.vo.BoardVO;
import org.edu.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 앱을 위한 홈페이지 요청(request)을 처리한다(아래).
 * http://localhost:8080/ 요청을 받았을때, 변수를 만들어서 크롬화면 또는 콘솔에 뿌려준다.
 */
@Controller
public class HomeController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Inject
	private IF_BoardService boardService;
	
	@Inject
	private SecurityCode securityCode;
	
	@Inject
	private CommonController commonController;
	
	//전역 홈페이지에서 스프링 진입전 발생하는 에러 페이지 처리
	@RequestMapping(value="/home/error/404",method=RequestMethod.GET)
	public String error404() throws Exception {
		return "home/error/404";
	}
	
	//사용자 홈페이지 게시판 상세보기 매핑
	@RequestMapping(value="/home/board/board_view",method=RequestMethod.GET)
	public String board_view(@RequestParam("bno") Integer bno,@ModelAttribute("pageVO") PageVO pageVO, Model model) throws Exception {
		BoardVO boardVO = boardService.readBoard(bno);
		//내용에 대한 시큐어코딩(아래)
		String xssData = securityCode.unscript(boardVO.getContent());
		boardVO.setContent(xssData);//악성코드 제거한 결과를 다시 셋 저장
		//=======================================================
		//첨부파일 데이터 jsp 뷰단으로 보내기(아래)
		List<HashMap<String,Object>> files = boardService.readAttach(bno);
		//[
		//{'save_file_name':저장된파일명0, 'real_file_name':DB에저장된파일명0},
		//{'save_file_name':저장된파일명1, 'real_file_name':DB에저장된파일명1}
		//]
		String[] save_file_names = new String[files.size()];
		String[] real_file_names = new String[files.size()];
		int cnt = 0;
		for(HashMap<String,Object> filename:files) {//위에 files데이터에서 값을 뽑아오는 로직
			save_file_names[cnt] = (String) filename.get("save_file_name");
			real_file_names[cnt] = (String) filename.get("real_file_name");
			cnt = cnt + 1;
		}
		boardVO.setSave_file_names(save_file_names);
		boardVO.setReal_file_names(real_file_names);
		//=============================================
		model.addAttribute("boardVO", boardVO);
		//업로드 한 내용이 이미지인지 일반문서파일인지 구분하는 역할을 jsp로 보냅니다.(아래)
		model.addAttribute("checkImgArray", commonController.getCheckImgArray());
		return "home/board/board_view";
	}
	
	//사용자 홈페이지 게시판 쓰기 매핑(POST) 오버로드(매개변수의 개수또는 타입이 틀린)메서드이용
	//jsp에서 board_write메서드를 호출합니다 -> 호출할때 폼의 필드값을 컨트롤러로 보냅니다.
	//컨트롤러에서 받을때 사용하는 매개변수 BoardVO boardVO입니다.
	//위에서 받은 boardVO 를 DAO에서 받아서 DB테이블에 쿼리명령으로 입력합니다.
	//POST는 jsp폼에서 서밋할때 전송하는 방식(숨겨서 전송하는 방식)-GET으로하면 브라우저 URL에 노출되어서 전송.
	@RequestMapping(value="/home/board/board_write",method=RequestMethod.POST)
	public String board_write(BoardVO boardVO) throws Exception {
		//위에서 받은 boardVO를 서비스로 보내기.
		return "redirect:/home/board/board_view";
	}
	//사용자 홈페이지 게시판 쓰기 매핑(GET) jsp폼에 접근하는 url방식(get) 폼만보여주는 역할
	@RequestMapping(value="/home/board/board_write",method=RequestMethod.GET)
	public String board_write() throws Exception {
		
		return "home/board/board_write";
	}
	
	//사용자 홈페이지 게시판 리스트 매핑
	@RequestMapping(value="/home/board/board_list",method=RequestMethod.GET)
	public String board_list(@ModelAttribute("pageVO") PageVO pageVO, Model model) throws Exception {
		//페이지 처리 추가(아래)
		if(pageVO.getPage() == null) {
			pageVO.setPage(1);
		}
		pageVO.setPerPageNum(5);//페이지 하단 페이징번호 개수
		pageVO.setQueryPerPageNum(10);//1페이지당 보여줄 게시물 개수
		int totalCount = boardService.countBoard(pageVO);//페이징의 게시물 전체개수 구하기 
		pageVO.setTotalCount(totalCount);
		List<BoardVO> board_list = boardService.selectBoard(pageVO);
		model.addAttribute("board_list", board_list);
		return "home/board/board_list";
	}
	
	//사용자 홈페이지 회원 마이페이지 접근 매핑
	@RequestMapping(value="/member/mypage",method=RequestMethod.GET)
	public String mypage() throws Exception{
		
		return "home/member/mypage";
	}
	
	//사용자 홈페이지 회원가입 접근 매핑
	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join() throws Exception{
		
		return "home/join";
	}
	
	//사용자 홈페이지 루트(최상위) 접근 매핑
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home() throws Exception{
		
		return "home/home";
	}
	
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("개발자들이 변수값 확인용으로 사용하는 것이 로그 입니다. 현재 여러분 컴퓨터언어는 {} 입니다.", locale);
		logger.info("환영합니다. 현재 여러분의 컴퓨터 언어는 " + locale + " 입니다..");
		System.out.println("위에 Locale클래스를 사용하는 이유는 다국어 지원때문에 변수로 사용하게 됩니다.");
		System.out.println("함수-C언어나자바스크립트-와 메서드-자바,스프링에서 사용함-는 같은 대상을 가리킵니다.");
		System.out.println("함수-메서드는 함수명('매개변수-입력값'){구현내용}형식이고, 입력값->출력값 구현됩니다.");
		Date date = new Date();//Data 날짜관련 클래스형 변수 date 선언했습니다. date변수 메서드를 사용 가능하게됨
		//data 변수는 실행가능한 변수=클래스형변수=Object오브젝트=인스턴스 라고 합니다.(위)
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		//DateFormat 클래스형 변수 dateFormat 이 선언되었고, 실행가능한 변수로 되었습니다.(위)
		String formattedDate = dateFormat.format(date);//변수가 실행됩니다.
		//위 변수가 실행되어서 출력된 결과값이 String클래스형 변수 formattedDate변수에 담기게 됩니다.
		model.addAttribute("TomcatserverTime", formattedDate );//serverTime 변수값으로 jsp파일로 이동.
		//위 model이라는 클래스형 변수를 이용해서, serverTime변수값을  아래  home(생략 .jsp)로 전송해 줌.
		System.out.println("현재 서버의 시간은 " + formattedDate + " 입니다.");
		return "home";//결과적으로 return 출력값이 home(생락 .jsp)에 연동됩니다.
		//스프링에서 뷰단으로 데이터 이동(전송)에 대해서 살펴 보았습니다.
	}*/
	
}
