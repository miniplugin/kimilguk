package kr.or.test;
/**
 * 이 클래스는 안드로이드 시작전, 자바 기초테스트로 사용하기 위해서  작성됨
 * @author kimilguk
 *
 */
public class HelloTest {

	public static void main(String[] args) {
		String str1 = "자바" + "자바";
		String str2 = "자바" + "자바";
		String str3 = new String("자바" + "자바");
		System.out.println(str1 == str2);//객체(주소)비교 : true
		//위, 생각상으론 다른객체처럼 보이지만, 값이 같으면 같은 주소값에 값을 저장한다, 그래서 true.
		System.out.println(str1 == str3);//객체(주소)비교 false
		System.out.println(str1.equals(str2));//객체내 값비교 true
		System.out.println(str1.equals(str3));//객제내 값비교 true
		
		StringBuffer sql1 = new StringBuffer("자바");
		sql1.append("자바");
		StringBuffer sql2 = new StringBuffer("자바");
		sql2.append("자바");
		System.out.println(sql1 == sql2);//객체(주소)비교 : false
		System.out.println(sql1.equals(sql2));//객체내 값비교, 
		//위, 스프링버퍼 객체에서는 equals 메서드가 구현되어 있지 않음 그래서 false
		//단, String 객체에서는 equals 메서드가 오바라이딩되어서 구현되어 있기 때문에 true 가 됨
		
		String s = null;
		if(s !=null && s.length() < 10) {//&& 2개 처럼 하는 회로를 short circuit 이라고함.
			//앞의 조건만 비교해서 false 이기 때문에 더이상 비교없이 조건문을 빠져 나감
			//파이썬에도 쇼트서킷과 같은 명령이 and 가 존재함. 즉, and(&&) 와 & 은 다름.
			System.out.println("OK");
		}
		/**
		if(s !=null & s.length() < 10) {
			//2개를 모두 비교하기 때문에 s.length 에서 NullPointerException 에러발생
			System.out.println("OK");
		}
		*/
		byte a = 3;
		a = (byte) (a * 3);//에러남. 바이트와 int(정수)를 곱하면, int 형이 반환됨, int값을 byte형으로 넣으면 에러
		//위, 그래서 (byte) 로 캐스팅(형변환) 함.
		innterClassTest();//내부 클래스의 메서드 호출 
	}

	private static void innterClassTest() {
		new InnerClass() {
			public void test1() {
				System.out.println("객체 생성하면서, 기능 생성됨.");
			}
		}.test2();
	}

}
class InnerClass {
	public void test2() {
		System.out.println("안드로이드에서는 내부클래스를 빈번하게 사용함.");
	}
}
