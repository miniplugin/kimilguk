package kr.or.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HelloWorldApp {

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
		Date reg_date = new Date();
		for(int cnt=0;cnt<=10;cnt++) {//더미게시물 100입력
			Calendar cal = Calendar.getInstance();//+
			cal.setTime(reg_date);//+
			cal.add(Calendar.SECOND, cnt);//+ cnt초 더하기
			System.out.println(format.format(cal.getTime()));
		}
		// 위 main()메서드는 자바프로그램의 실행 진입지점 입니다.
		System.out.println("헬로우 월드!");
		MemberVO memberVO = new MemberVO();
		sumFunc(memberVO,4);//플러스함수를 실행할때 매개변수 값을 줍니다
		sumFunc(3,4);
		//메서드의 특징, 위와 같이 같은메서드명 로드된 매개변수가 다른 방식 오버로드된 메서드라고 합니다.
		String testNull = null;
		String testEmpty = "";
		System.out.println("null출력-" + testNull + "/ empty출력-" + testEmpty);
		//System.out.println("isEmpty메서드null값출력-" + testNull.isEmpty(값이null이면에러));//에러NullPointer
		System.out.println("isEmpty메서드공백값출력-" + testEmpty.isEmpty());		
	}
	
	//메서드의 매개변수에 대해서
	public static void sumFunc(MemberVO memberVO, int b) {
		System.out.println("매개변수 memberVO + b = " + (memberVO.toString()+b) + " 입니다.");
	}
	
	public static void sumFunc(int a, int b) {
		System.out.println("매개변수 a + b = " + (a+b) + " 입니다.");
	}
}
