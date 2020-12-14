package org.edu.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
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
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class DataSourceTest {

	@Inject
	DataSource dataSource;//자바에서처럼 new 오브젝트를 생성하지 않고, 스프링에서는 @Inject로 오브젝트 생성.
	
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
