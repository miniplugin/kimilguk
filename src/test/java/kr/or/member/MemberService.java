package kr.or.member;

import kr.or.member.MemberVO;

public class MemberService {
	public void printMember(MemberVO[] members) {// members는 레코드가 여러개=3개레코드가들어감
		// 서비스 구현메서드 내용(아래) 향상된 for반복문 사용
		int cnt = 0;
		for (MemberVO member : members) {// members여러 레코드중 1개의 레코드를 member오브젝트객체로 대입
			//여기서 members[0]=MemberVO member -> members[1]=MemberVO member -> members[2]
			//MemberVO클래스형데이터타입을 member 변수로 만듬.
			cnt = cnt + 1;
			System.out.println(cnt + "번째 레코드는123 " + member.toString());
			//member 클래스변수는 출력이 가능하지만, 내용은 확인할 수 없게 출력됩니다.
			//member클래스에서 내용을 확인하려고, 오바라이드된 toString()만들어서 출력하게 됩니다.
		}
		//비지니스 로직에서 처리하는 내용은=데이터를 가공한다.(아래)
		//위처럼 데이터 select가져오기,insert입력하기,update수정하기
		//delete삭제하기 => CRUD를 구현하는 내용
	}
}
