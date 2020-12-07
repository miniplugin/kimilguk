package kr.or.test;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 자바에서 예외처리하는 테스트 클래스
 * @author User
 *
 */
public class ExceptionTest {

	public static void main(String[] args) {
		// 진입(실행)메서드 구현내용
		//멤버변수(전역)
		int number=0;
		//클래스형 멤버변수(아래)
		//InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		System.out.print("키보드로 0부터 9까지 숫자를 입력해 주세요. ");
		try {
			number=System.in.read();//키보드로 1문자만 입력받아서 바이트데이터 반환
			System.out.println("키보드로 입력받은 바이트문자 = " + number);//엔터쳐서 줄바꿈하는 효과
			//number = inputStreamReader.read();//키보드로 1문자만 입력 받아서 바이트데이터 반환
			//number = 1; number = a;
		} catch (IOException e) {
			System.out.println("숫자를 입력하지 않으셨습니다.");
			e.printStackTrace();
		}
		System.out.println("바이트형으로 반환받은 수를 문자형으로 변환하면  = " + (char)number);
		//byte형 number변수를 1문자형 char형으로 변환하는 방법 (char)number
		//예, 숫자 1을 입력하면, byte형 문자는 49, char형으로 변환하면, 1로 출력이 됩니다.
	}

}
