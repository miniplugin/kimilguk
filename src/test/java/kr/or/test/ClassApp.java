package kr.or.test;
//오리지날 자바프로그램 형식: 자바이론공부 -> 스프링 웹프로그램과는 형식이 틀립니다.
public class ClassApp {

	public static void main(String[] args) {
		// 이 클래스의 진입메서드 main 실행
		/*
		 * circle은 우리가 지정한 클래스 이름인거고, 
		 * Circle c는 클래스 이름 + c(라는 뭔가 값구하는 방식인거고) =  new 클래스네임(반지름값 5)인건가요 
		 */
		Circle c = new Circle(5);//인스턴스(오브젝트 클래스)변수 c 생성 new 키워드 생성자circle(반지름값5)
		//위에서 new 키워드는 프로그램 실행공간을 메모리에 새로 할당한다는 이야기 입니다.
		Circle c2 = new Circle(10);
		//Circle클래스를 c 이름으로 prefix해서 사용하는 겁니다.
		//자바프로그램의 특징은 OOP프로그래밍의 대표적인 사례, Object=위 에서 new키워드로 생성한 c 오브젝트,
		//자바에서 OOP 를 사용방식을 설명하는 예, 스프링에서는 new키워드대신 @Inject를 주로 사용.
		double print_result = c.getKimilguk();
		System.out.println("첫번째 원의 넓이는 " + print_result + " 입니다.");
		double print_result2 = c2.getKimilguk();
		System.out.println("두번째 원의 넓이는 " + print_result2 + " 입니다.");
	}

}

class Circle {
	private int r;//멤버변수 반지름 이클래스에서 전연변수
	public Circle(int a) {//생성자매서드 a인자=매개변수=파라미터 값은 new키워드로 오브젝트 만들어질때 발생
		r = a;
	}
	public double getKimilguk() {
		return r*r*3.14;//원의 넓이를 반환
	}
}
