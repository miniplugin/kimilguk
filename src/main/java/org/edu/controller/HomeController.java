package org.edu.controller;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.edu.dao.IF_BoardDAO;
import org.edu.service.IF_BoardService;
import org.edu.service.IF_MemberService;
import org.edu.util.CommonController;
import org.edu.util.SecurityCode;
import org.edu.vo.AttachVO;
import org.edu.vo.BoardVO;
import org.edu.vo.MemberVO;
import org.edu.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 앱을 위한 홈페이지 요청(request)을 처리한다(아래).
 * http://localhost:8080/ 요청을 받았을때, 변수를 만들어서 크롬화면 또는 콘솔에 뿌려준다.
 */
@Controller
public class HomeController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Inject
	private IF_MemberService memberService;
	
	@Inject
	private IF_BoardService boardService;
	
	@Inject
	private IF_BoardDAO boardDAO;
	
	@Inject
	private SecurityCode securityCode;
	
	@Inject
	private CommonController commonController;
	
	//전역 홈페이지에서 스프링 진입전 발생하는 에러 페이지 처리
	@RequestMapping(value="/home/error/404",method=RequestMethod.GET)
	public String error404() throws Exception {
		return "home/error/404";
	}
	
	//사용자 홈페이지 게시판 삭제 매핑
	@RequestMapping(value="/home/board/board_delete",method=RequestMethod.POST)
	public String board_delete(RedirectAttributes rdat, @RequestParam("bno") Integer bno, @RequestParam("page") Integer page) throws Exception {
		//부모 게시판에 첨부파일이 있다면 첨부파일 삭제처리 후 게시글 삭제(아래)
		List<AttachVO> delFiles = boardService.readAttach(bno);
		if(!delFiles.isEmpty()) { //for(변수-한개:레코드-여러개){}
			for(AttachVO file_name:delFiles) {//향상된 for반복문 입니다. 요즘은 이게 기본입니다.
				File target = new File(commonController.getUploadPath(),file_name.getSave_file_name());
				if(target.exists()) {
					target.delete();//실제 업로드된 파일 지우기
				}
			}
		}
		//DB에서 부모 게시판에 댓글이 있다면 댓글삭제처리 후 게시글 삭제처리-서비스에 있음(아래)
		boardService.deleteBoard(bno);
		rdat.addFlashAttribute("msg", "삭제");//msg변수값은 URL에 표시가 나오지 않게 숨겨서 board_list보낸다.
		return "redirect:/home/board/board_list?page="+page;//쿼리스트링변수는 URL에 표시가 됩니다.
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
		List<AttachVO> files = boardService.readAttach(bno);
		//[
		//{'save_file_name':저장된파일명0, 'real_file_name':DB에저장된파일명0},
		//{'save_file_name':저장된파일명1, 'real_file_name':DB에저장된파일명1}
		//]
		String[] save_file_names = new String[files.size()];
		String[] real_file_names = new String[files.size()];
		int cnt = 0;
		for(AttachVO filename:files) {//위에 files데이터에서 값을 뽑아오는 로직
			save_file_names[cnt] = filename.getSave_file_name();
			real_file_names[cnt] = filename.getReal_file_name();
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
	
	@RequestMapping(value="/home/board/board_update",method=RequestMethod.POST)
	public String board_update(RedirectAttributes rdat,@RequestParam("file") MultipartFile[] files, BoardVO boardVO, PageVO pageVO) throws Exception {
		//첨부파일 업로드
		List<AttachVO> delFiles = boardService.readAttach(boardVO.getBno());
		String[] save_file_names = new String[files.length];
		String[] real_file_names = new String[files.length];
		int index = 0;
		for(MultipartFile file:files) {//여기의 file은 신규 저장하는 파일
			if(file.getOriginalFilename() != "") {
				
				int sun = 0;
				for(AttachVO file_name:delFiles) {//실제 폴더에서 기존 첨부파일 삭제처리 
					if(index==sun) {
						File target = new File(commonController.getUploadPath(),file_name.getSave_file_name());//삭제할 파일경로 지정
						if(target.exists()) {
							target.delete();//기존 첨부파일 폴더에서 지우기
							boardDAO.deleteAttach(file_name.getSave_file_name());//DB에서 기존파일 지우기
						}
					}
					sun = sun + 1;
				}
				//신규파일 폴더에 업로드 처리
				save_file_names[index] = commonController.fileUpload(file);//신규파일 폴더에 업로드
				real_file_names[index] = file.getOriginalFilename();//신규파일 한글파일명 저장
			}else{
				save_file_names[index] = null;//신규파일 폴더에 업로드
				real_file_names[index] = null;//신규파일 한글파일명 저장
			}
			index = index + 1; 
		}
		boardVO.setSave_file_names(save_file_names);
		boardVO.setReal_file_names(real_file_names);
		//시큐어코딩 추가(아래)
		String xssData = boardVO.getContent();
		boardVO.setContent(securityCode.unscript(xssData));
		
		boardService.updateBoard(boardVO);//DB에 신규파일 저장기능 호출
		//게시판 테이블 업데이트+첨부파일테이블 업데이트
		rdat.addFlashAttribute("msg", "수정");
		return "redirect:/home/board/board_view?bno="+boardVO.getBno()+"&page="+pageVO.getPage();
	}
	
	@RequestMapping(value="/home/board/board_update",method=RequestMethod.GET)
	public String board_update(Model model, @ModelAttribute("pageVO") PageVO pageVO, @RequestParam("bno") Integer bno) throws Exception {
		BoardVO boardVO = boardService.readBoard(bno);
		//첨부파일처리(아래)
		List<AttachVO> files = boardService.readAttach(bno);
		//아래변수 List<AttachVO>세로배치를 가로배치로 변경할때 필요
		String[] save_file_names = new String[files.size()];
		String[] real_file_names = new String[files.size()];
		int cnt=0;
		//세로데이터를 가로데이터로 변경로직(아래)
		for(AttachVO file_name:files) {
			save_file_names[cnt] = file_name.getSave_file_name();
			real_file_names[cnt] = file_name.getReal_file_name();
			cnt = cnt + 1;
		}
		boardVO.setSave_file_names(save_file_names);
		boardVO.setReal_file_names(real_file_names);
		
		model.addAttribute("boardVO", boardVO);
		
		return "home/board/board_update";
	}
	//사용자 홈페이지 게시판 쓰기 매핑(POST) 오버로드(매개변수의 개수또는 타입이 틀린)메서드이용
	//jsp에서 board_write메서드를 호출합니다 -> 호출할때 폼의 필드값을 컨트롤러로 보냅니다.
	//컨트롤러에서 받을때 사용하는 매개변수 BoardVO boardVO입니다.
	//위에서 받은 boardVO 를 DAO에서 받아서 DB테이블에 쿼리명령으로 입력합니다.
	//POST는 jsp폼에서 서밋할때 전송하는 방식(숨겨서 전송하는 방식)-GET으로하면 브라우저 URL에 노출되어서 전송.
	@RequestMapping(value="/home/board/board_write",method=RequestMethod.POST)
	public String board_write(RedirectAttributes rdat, @RequestParam("file") MultipartFile[] files, BoardVO boardVO) throws Exception {
		//위에서 받은 boardVO를 서비스로 보내기.
		//첨부파일 저장할 배열변수 선언
		String[] save_file_names = new String[files.length];
		String[] real_file_names = new String[files.length];
		int index = 0;
		for(MultipartFile file:files) {
			if(file.getOriginalFilename() != "") {
			save_file_names[index] = commonController.fileUpload(file);//UUID 고유값
			real_file_names[index] = file.getOriginalFilename();//예를 들면 한글파일명
			}
			index = index + 1;
		}
		boardVO.setSave_file_names(save_file_names);
		boardVO.setReal_file_names(real_file_names);
		//보안코딩으로 script 제거(아래)
		String xssData = boardVO.getContent();
		boardVO.setContent(securityCode.unscript(xssData));
		
		boardService.insertBoard(boardVO);//실제 DB에 인서트
		rdat.addFlashAttribute("msg", "저장");
		
		return "redirect:/home/board/board_list";
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
	
	//사용자 홈페이지 회원 마이페이지 수정 매핑
	@RequestMapping(value="/member/mypage_update",method=RequestMethod.POST)
	public String mypage_update(HttpServletRequest request, MemberVO memberVO,RedirectAttributes rdat) throws Exception {
		//스프링시큐리티에서 제공하는 passwordEncoder 암호화 처리(아래)
		if(memberVO.getUser_pw() != "") {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String user_pw_encode = passwordEncoder.encode(memberVO.getUser_pw());
			memberVO.setUser_pw(user_pw_encode);
		}
		memberService.updateMember(memberVO);
		HttpSession session = request.getSession();
		session.setAttribute("session_username", memberVO.getUser_name());//기존세션 덮어쓰기.
		rdat.addFlashAttribute("msg", "회원수정");//model로 값을 보내지 못하는 이유는 redirect 이기때문.
		return "redirect:/member/mypage";
	}
	//사용자 홈페이지 회원 마이페이지 접근 매핑
	@RequestMapping(value="/member/mypage",method=RequestMethod.GET)
	public String mypage(HttpServletRequest request, Model model) throws Exception{
		//마이페이지는 로그인 상태만 접근 가능하기 때문에, 로그인 세션변수중 로그인아이디변수 session_userid를 사용
		HttpSession session = request.getSession();
		MemberVO memberVO = memberService.readMember((String) session.getAttribute("session_userid"));
		model.addAttribute("memberVO", memberVO);
		return "home/member/mypage";
	}
	
	//사용자 홈페이지 회원탈퇴 매핑
	@RequestMapping(value="/member/member_disabled",method=RequestMethod.POST)
	public String member_disabled(HttpServletRequest request, MemberVO memberVO, RedirectAttributes rdat) throws Exception {
		memberService.updateMember(memberVO);
		//세션값 invalidate() 삭제하기.
		request.getSession().invalidate();
		rdat.addFlashAttribute("msg", "회원탈퇴");
		return "redirect:/";
	}
	
	//사용자 홈페이지 회원가입 처리 매핑
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String join(MemberVO memberVO, RedirectAttributes rdat) throws Exception {
		//아래 3줄이 스프링 시큐리티에서 제공하는 패스워드암호화 처리 
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String user_pw_encode = passwordEncoder.encode(memberVO.getUser_pw());
		memberVO.setUser_pw(user_pw_encode);
		
		memberService.insertMember(memberVO);
		rdat.addFlashAttribute("msg", "회원가입");
		return "redirect:/login";
	}
	//사용자 홈페이지 회원가입 접근 매핑
	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join() throws Exception{
		
		return "home/join";
	}
	
	//사용자 홈페이지 루트(최상위) 접근 매핑
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home(Model model) throws Exception{
		PageVO pageVO = new PageVO();
		pageVO.setPage(1);
		pageVO.setPerPageNum(5);//하단페이징
		pageVO.setQueryPerPageNum(5);
		
		//사용자홈 메인페이지에 출력할 게시판 지정, gallery 쿼리1
		pageVO.setBoard_type("gallery");
		List<BoardVO> board_list = boardService.selectBoard(pageVO);
		model.addAttribute("board_list", board_list);
		
		//사용자홈 메인페이지에 출력할 게시판 지정, notice 쿼리2
		pageVO.setBoard_type("notice");
		List<BoardVO> notice_list = boardService.selectBoard(pageVO);
		model.addAttribute("notice_list", notice_list);
		
		//첨부파일 1개만 model클래스를 이용해서 jsp로 보냅니다.
		String[] save_file_names = new String[board_list.size()];
		int cnt = 0;
		for(BoardVO boardVO:board_list) {//board_list변수에는 최대 5개의 레코드가 존재함.
			List<AttachVO> file_list = boardService.readAttach(boardVO.getBno());
			//System.out.println("디버그-file_list" + file_list);
			if(file_list.size() == 0) {//첨부파일이 없을떄
				save_file_names[cnt] = "";
				System.out.println("디버그-[" + cnt + "]" + save_file_names[cnt]);
				//continue;//컨티뉴 아래는 실행 하지 않고 거너띔
			} else {
				for(AttachVO file_name:file_list) {
					String save_file_name = file_name.getSave_file_name();
					String extName = FilenameUtils.getExtension(save_file_name);
					boolean imgCheck = commonController.getCheckImgArray().contains(extName.toLowerCase());
					if(imgCheck) {//첨부파일이 이미지일때
						save_file_names[cnt] = save_file_name;
						System.out.println("디버그[" + cnt + "]" + save_file_names[cnt]);
						break;//이중 반복문에서 현재 for문만 종료
					} else {//첨부파일이 엑셀,한글같은 파일일때
						save_file_names[cnt] = "";
						System.out.println("디버그[" + cnt + "]" + save_file_names[cnt]);
					}
				}
			}
			cnt = cnt + 1;
		}
		
		model.addAttribute("save_file_names", save_file_names);
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
