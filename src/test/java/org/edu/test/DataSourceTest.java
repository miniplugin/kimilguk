package org.edu.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.edu.dao.IF_BoardDAO;
import org.edu.dao.IF_MemberDAO;
import org.edu.vo.BoardVO;
import org.edu.vo.MemberVO;
import org.edu.vo.PageVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * JUnit 자바단위테스트이고, DataSource 지정후 DB(Hsql,Mysql,Oracle) 접속,
 * 데이터베이스 테이블에 Create, Read, Update, Delete 테스트용 클래스
 * 제이유닛4클래스를 사용.
 * 단위테스트는 톰캣이 실행되지 않아도 작동이 되야 합니다.
 * 그래서, 테스트 클래스 상단에 servelet-context.xml 이러한 설정파일을 불러들여서 실행이 가능
 * @author 김일국
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@PropertySource("classpath:properties/local.properties")//현재클래스에서 전역변수사용시 필요 
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class DataSourceTest {

	@Inject
	DataSource dataSource;//자바에서처럼 new 오브젝트를 생성하지 않고, 스프링에서는 @Inject로 오브젝트 생성.
	
	@Inject
	IF_MemberDAO memberDAO;
	
	@Inject
	IF_BoardDAO boardDAO;
	
	@Inject//사용하면 않되는 이유: 클래스상단에 @Controller, @Service, @Repository, @Component 이런내용만 @Inject합니다.
	MemberVO memberVO;//기존자바처럼 new MemberVO() 오브젝트를 생성하지않고, 주입해서사용. 
	
	public String memberPrimaryKey() throws Exception {
		//사용자 프라이머리키 생성하는 메서드 년월일시분처 + 밀리초 대량더미데이터입력시Uniq에러발생->countMember로 변경
		/*
		Date primaryKey = new Date();
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.out.println("프라이머리키 : " + newFormat.format(primaryKey));
		return "dummy_" + newFormat.format(primaryKey);
		*/
		PageVO pageVO = new PageVO();
		pageVO.setPage(1);
		pageVO.setPerPageNum(8);//리스트하단에 보이는 페이징번호의 개수
		pageVO.setQueryPerPageNum(10);//쿼리에서 1페이지당 보여줄 게시물수 10개로 입력 놓았습니다.
		//검색된 전체 게시물수 구하기 서비스 호출
		int countMember = 0;
		countMember = memberDAO.countMember(pageVO);
		return "dummy_" + (countMember+1);
	}
	
	@Test
	public void updateMember() throws Exception {
		//CRUD 중 Update 테스트 구현 특징, user_id는 프라이커리키 이기 때문에 수정대상이 아닙니다.
		//MemberVO memberVO = new MemberVO();
		memberVO.setUser_id("dummy_1");
		memberVO.setUser_name("홍길동");
		memberVO.setUser_pw("");//이 셋을 적용하면, 공백값 memberVO.getUser_pw() == ""
		//memberVO.setUser_pw("");//이 셋을 주석으로 적용하면, 아예보내지 않음 null값 memberVO.getUser_pw() == null
		memberVO.setEmail("test@test.com");
		memberVO.setPoint(100);
		memberVO.setEnabled(true);
		memberVO.setLevels("ROLE_ADMIN");
		String user_id = memberVO.getUser_id();//memberVO의 오브젝트의 데이터는 1개의 레코드이기때문에 반환값이 1개만
		memberDAO.updateMember(memberVO);
	}
	
	@Test
	public void readMember() throws Exception {
		//CRUD 중 Read 테스트 구현
		//MemberVO memberVO = new MemberVO();
		memberVO = memberDAO.readMember("admin");
		System.out.println("admin 에 대한 상세정보 입니다.");
		System.out.println(memberVO.toString());
	}
	
	@Test
	public void deleteMember() throws Exception {
		//CRUD 중 Delete 테스트 구현(쿼리 -> DAO -> memberDAO주입받은 오브젝트사용)
		memberDAO.deleteMember("user_20201215145621755");//삭제메서드 -> 쿼리 호출
	}
	
	@Test
	public void insertBoard() throws Exception {
		BoardVO boardVO = new BoardVO();
		boardVO.setBoard_type("gallery");
		boardVO.setTitle("더미게시물");
		boardVO.setContent("더미 내용 입니다.");
		boardVO.setWriter("일반사용자");
		
		//boardVO.setBno(프라이머리키);
		for(int cnt=0;cnt<=0;cnt++) {//더미게시물 100입력
			Date reg_date = new Date();
			Calendar cal = Calendar.getInstance();//+
			cal.setTime(reg_date);//+
			cal.add(Calendar.SECOND, cnt);//+ cnt초 더하기
			//???????????????????????????????????????????
			boardVO.setReg_date(cal.getTime());//이부분이 작동을 하지 않아서 문제
			//???????????????????????????????????????????
			boardDAO.insertBoard(boardVO);
		}
	}
	
	@Test
	public void insertMember() throws Exception {
		//CRUD 중 Create 테스트
		//MemberVO memberVO = new MemberVO();
		//사용자 생성 규칙: user_ 시작(prefix),suffix(접미사)는 년월일시분초 
		//사용자 생성결과 예: user_20201215142132
		//String memberIdKey = memberPrimaryKey();
		
		memberVO.setUser_name("사용자03");
		//패스워드 암호화 처리(필수이지만, 스프링 시큐리티 엔코더처리 아래)
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberVO.setUser_pw(passwordEncoder.encode("1234"));
		memberVO.setEmail("user03@abc.com");
		memberVO.setPoint(100);
		memberVO.setEnabled(true);
		memberVO.setLevels("ROLE_USER");
		
		for(int cnt=0;cnt<=0;cnt++) {//더미사용자 100명 입력
			memberVO.setUser_id(memberPrimaryKey());
			Date reg_date = new Date();
			Calendar cal = Calendar.getInstance();//+
			cal.setTime(reg_date);//+
			cal.add(Calendar.SECOND, cnt);//+ cnt초 더하기
			//???????????????????????????????????????????
			memberVO.setReg_date(cal.getTime());//이부분이 작동을 하지 않아서 문제
			//???????????????????????????????????????????
			memberDAO.insertMember(memberVO);
		}		
	}
	
	@Test
	public void selectMember() throws Exception {
		//"user_name","홍길동"
		PageVO pageVO = new PageVO();
		pageVO.setSearch_type("user_id");
		pageVO.setSearch_keyword("admin");
		//아래3개줄은 초기 페이징처리에 필요한 필수값 저장
		pageVO.setPage(1);
		pageVO.setPerPageNum(5);//리스트하단에 보이는 페이징번호의 개수
		pageVO.setQueryPerPageNum(10);//쿼리에서 1페이지당 보여줄 회원수 10명으로 입력 놓았습니다.
		
		List<MemberVO> memberList =	memberDAO.selectMember(pageVO);
		System.out.println("회원리스트 테스트 입니다.");
		System.out.println(memberList.toString());
	}
	@Test
	public void oldQueryTest() throws Exception {
		//Connection connection = dataSource.getConnection();//root-context사용
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:hsqldb:file:c:/egov/workspace/embeded/hsql_file.db;hsqldb.lock_file=false","sa","");
		/* mysql(마리아DB)
		 * .getConnection("jdbc:log4jdbc:mysql://127.0.0.1:3306/edu","root","apmsetup");
		 */
		//직접 쿼리를 날립니다.(아래)
		Statement stmt = connection.createStatement();
		/* 인서트 쿼리실행(아래) 
		for(int cnt=0;cnt<=100;cnt++) { //고전 방식으로 더미 데이터 입력하기(아래)
		stmt.executeQuery("INSERT INTO tbl_board VALUES("
				+ "(select count(*) from tbl_board)+1"
				+ ",'강제 수정된 글입니다.', '수정 테스트 ', 'user00', now(),now(), 0, 0)");
		}
		*/
		/* 셀렉트 쿼리실행(아래) */
		ResultSet rs = stmt.executeQuery("select * from tbl_board");
		System.out.println("번호\t\t제목\t\t내용\t\t작성자");
		while(rs.next()) {
			System.out.print(rs.getString("bno"));
			System.out.print(rs.getString("title"));
			System.out.print(rs.getString("content"));
			System.out.print(rs.getString("writer"));
			System.out.println();
		}
		if(rs !=null)rs.close();
		if(stmt !=null)stmt.close();
		if(connection !=null)connection.close();
	}
	
	@Test
	public void dbConnectionTest() throws Exception {
		try {//내부에서 {} 에러발생시 실행을 중지하고, catch{}구문이 실행 됩니다. 예외처리라고 합니다.
			Connection connection = dataSource.getConnection();
			System.out.println("데이터베이스 접속이 성공하였습니다.");
		} catch (SQLException e) {
			System.out.println("데이터베이스 접속에 실패햐였습니다. 왜냐하면 " + e);
			//e.printStackTrace();
		}//DB커넥션 오브젝트 만드는 과정. new 사용안함.
		//try~catch는 테스트에서만 사용하고, 스프링에서는 throws Exception으로 예외를 스프링으로 보냅니다.
	}
	@Test
	public void test() {
		//fail("Not yet implemented");
		System.out.println("JUnit테스트 시작 입니다.");
	}

}
