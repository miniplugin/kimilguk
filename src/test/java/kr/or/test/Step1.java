package kr.or.test;
/**
 * 내부변수와 배열 사용에 대해서
 * @author 김일국
 *
 */
public class Step1 {
	//전역변수(멤버변수)는 클래스 전체에 영향을 미치는 변수=클래스안에서 사용하는 변수
	//내부변수는 메서드내에서만, 영향을 미치는 변수=메서드(함수)안에서만 사용하는 변수
	public static void main(String[] args) {
		//필드=메서드 내부변수<->전역변수(멤버) 만들기(아래)
		String name;//String은 데이터 타입 name이라는 변수가 담을 수 있는 형태를 지정.
		int age;//int타입이라서 숫자만 담을 수 있음.
		String phoneNum;//String타입이라서 문자열만 담을 수 있음.
		//변수에 값을 대입연산자로 대입(아래)
		name="홍길동";
		age=45;
		phoneNum="000-0000-0000";
		//위 내용을 출력하는 메서드 1개 사용(아래)
		printMember(name, age, phoneNum);
		name="성춘향"; age=18; phoneNum="111-1111-1111";
		printMember(name,age,phoneNum);
		name="각시탈"; age=28; phoneNum="222-2222-2222";
		printMember(name,age,phoneNum);
		
		//배열을 이용해서 좀더 간편하게 입력하고, 출력해 보기
		String[] names = {"홍길동","성춘향","각시탈"};
		int[] ages = {45,18,28};
		String[] phoneNums = {"000-0000-0000","111-1111-1111","222-2222-2222"};
		printMember(names,ages,phoneNums);//오버로드형 메서드 다형성의 대표사례
	}

	private static void printMember(String[] names, int[] ages, String[] phoneNums) {
		// member를 출력하는데 배열값을 받아서 구현(아래)
		int dataLength = names.length;//이름배열에 있는 사람의 명수를 구하기 목적은 아래 for반복의 끝값을 구함.
		System.out.println("매개변수로 받은 names 의 사람의 명수는 : "+ names.length);
		//고전for문의 반복문형식은 for(초기값;끝값=반복의조건cnt<2;증가값){구현내용}
		for(int cnt=0;cnt<dataLength;cnt++) {
			System.out.println("cnt변수의 변화는 = " + cnt);
			System.out.println("배열의 이름은: "+names[cnt]+" | 나이는: "+ages[cnt]+" | 연락처는: "+phoneNums[cnt]);
		}
	}

	private static void printMember(String name, int age, String phoneNum) {
		// member를 출력하는 프린트 메서드 구현 내용(아래)
		System.out.println("회원의 이름은: "+name+" | 나이는: "+age+" | 연락처는: "+phoneNum);
	}

}
