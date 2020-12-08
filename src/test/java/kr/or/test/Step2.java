package kr.or.test;

import kr.or.test.Step2.MemberService;

/**
 * 클래스자료형 사용에 대해서
 * @author 김일국
 *
 */
class MemberVO {
	//멤버변수 만들기(아래)
	private String name;
	private int age;
	private String phoneNum;
	//멤버변수는 보안때문에 private만들었으나, get,set메서드는 public으로 생성됩니다.
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	@Override
	public String toString() {
		return "디버그용 MemberVO [name=" + name + ", age=" + age + ", phoneNum=" + phoneNum + "]";
	}
	
}

class MemberService {//이 서비스 클래스에서는 회원출력에 관련된 메서드를 모아 놓습니다.(아래)
	
}

public class Step2 {

	public static void main(String[] args) {
		// 실행 메서드
		MemberVO memberVO = new MemberVO();//자바 오브젝트 생성하는 방법
		memberVO.setName("홍길동");
		memberVO.setAge(45);
		memberVO.setPhoneNum("000-0000-0000");
		MemberVO memberVO2 = new MemberVO();
		memberVO2.setName("성춘향");
		memberVO2.setAge(18);
		memberVO2.setPhoneNum("111-1111-1111");
		MemberVO memberVO3 = new MemberVO();
		memberVO3.setName("각시탈");
		memberVO3.setAge(28);
		memberVO3.setPhoneNum("222-2222-2222");
		//위 각각 3개의 레코드를 1군데 담을 배열 클래스오브젝트를 하나 생성(아래)
		MemberVO[] members = new MemberVO[3];
		members[0] = memberVO;
		members[1] = memberVO2;
		members[2] = memberVO3;
		//전에 사용했던 출력보다는 개선된 방식으로 출력(아래) MemberService 서비스전용 클래스만들어서 처리.
		MemberService memberService = new MemberService();//자바 오브젝트객체 생성하는 방법
	}

}
