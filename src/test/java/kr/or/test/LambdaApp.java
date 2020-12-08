package kr.or.test;

import java.util.function.IntSupplier;

public class LambdaApp {
	//static은 현재 클래스에서 유일한 메서드로 사용하겠다는 명시적인 예약어 
	public static int method(int x, int y) {
		//IntSupplier 계산된 결과값을 반환할때 int타입으로 반환하는 명세가 모여있는 인터페이스
		//위 IntSupplier는 인터페이스로 메서드명만 있고, 구현내용이 없는 영역.
		
		//람다식 적용전 코드(아래)
		IntSupplier intSupplier = new IntSupplier() {//구현내용시작
			@Override
			public int getAsInt() {
				int sum = x+y;
				return sum;
			}
		};
		
		//위 intSupplier오브젝트를 호출해야만 실행이 됩니다.
		int result = intSupplier.getAsInt();
		
		//람다식 적용후 코드(아래)
		IntSupplier intSupplier2 = () -> {//구현내용시작:->new생략, 애로우함수추가, 메서드명생략됨. @오버라이드 생략
				int sum = x+y;
				return sum;		
		};
		
		int result2 = intSupplier2.getAsInt();
		
		return result2;
	}
	public static void main(String[] args) {
		// 자바 애플리케이션의 진입 실행 함수
		System.out.println("람다식 테스트용 메서드 반환값 출력 = " + method(3,5) );
	}
}