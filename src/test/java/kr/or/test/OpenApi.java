package kr.or.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * OpenApi클래스로 HRD-Net에서 제공하는 구직훈련과정API 목록을 출력하는 자바앱
 * @author 김일국
 *
 */
public class OpenApi {
	//외부연계 역할 메서드(아래)
	public static void serviceApi() {
		BufferedReader bufferedReader = null;//HRD넷에서 전송받은 데이터를 임시 저장하는 공간.자동차범퍼.
		String urlStr = "http://www.hrd.go.kr/hrdp/api/apipo/APIPO0101T.do?srchTraEndDt=20210317&pageSize=10&srchTraArea2=44133&srchTraArea1=44&srchTraOrganNm=%ED%9C%B4%EB%A8%BC&srchTraStDt=20201108&sortCol=TR_STT_DT&authKey=yKt63a6Wtxfq282kkIdQAZVXtSlPW5GF&sort=ASC&returnType=XML&outType=1&pageNum=1&srchTraPattern=N1&srchPart=-99&apiRequstPageUrlAdres=/jsp/HRDP/HRDPO00/HRDPOA60/HRDPOA60_1.jsp&apiRequstIp=58.74.90.6";
		try {
			URL url = new URL(urlStr);
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");//URL쿼리스트링으로 파라미터를 보낸다.
				bufferedReader = new BufferedReader(new InputStreamReader());
			} catch (IOException e) {
				// Http 웹접근에러상황발생
				System.out.println("Http 웹접근 에러입니다. 왜냐하면 " + e.toString());
			}
		} catch (MalformedURLException e) {
			// 외부연계 URL에 접근하지 못했을때 에러상황발생
			System.out.println("URL접근에 실패했습니다. 왜냐하면 " + e.toString());
		}
	}
	//스태틱 메서드는 new키워드로 객체오브젝트 생성없이 바로 접근이 기능한 메서드를 말합니다.
	public static void main(String[] args) {
		// 일반메서드와 스태틱 메서드의 호출차이
		//StaticTest staticTest = new StaticTest();
		//staticTest.test2();//노스태틱은 오브젝트생성후 접근이 가능
		//StaticTest.test();//스태틱메서는 오브젝트생성없이 클래스로 바로 접근이 가능 메서드, 변수도 가능.
		
	}

}

class StaticTest {//스태틱은 메서드, 변수에도 해당되는 이론
	public static void test() {//클래스를 읽어들이는 순간 실행이 가능
		//스태틱메서드는 클래스를 읽어 들이는 순간 고정메모리를 할당받음=실행가능.
		System.out.println("스태틱테스트");
	}
	public void test2() {//일반메서드는 new키워드로 오브젝트를 생성할때 메모리 할당받음.(실행가능)
		System.out.println("일반테스트2");
	}
}
