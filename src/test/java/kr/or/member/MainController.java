package kr.or.member;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.net.ntp.NTPUDPClient;

import kr.or.member.MemberService;
import kr.or.member.MemberVO;

public class MainController {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		// 실행 메서드
		MemberVO memberVO = new MemberVO();// 자바 오브젝트 생성하는 방법
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
		// 위 각각 3개의 레코드를 1군데 담을 배열 클래스오브젝트를 하나 생성(아래)
		MemberVO[] members = new MemberVO[3];
		members[0] = memberVO;
		members[1] = memberVO2;
		members[2] = memberVO3;
		// 전에 사용했던 출력보다는 개선된 방식으로 출력(아래) MemberService 서비스전용 클래스만들어서 처리.
		MemberService memberService = new MemberService();// 자바 오브젝트객체 생성하는 방법\
		memberService.printMember(members);// 서비스클래스의 메서드호출
		
		//서버시간 가져오기 코딩(아래)
		//서버시간을 가져올 수 있는 commons-net 메이븐 디펜던시 사용 라이브러리를 추가.
		NTPUDPClient timeClient = new NTPUDPClient();
		timeClient.setDefaultTimeout(1000);
		//네트워크로 서버시간을 가져올때, 응답이 1초를 넘기면, 재 접속해라.
		timeClient.open();
		//pool.ntp.org는 서버시간보내줄 실제 서버 주소.
		InetAddress address = InetAddress.getByName("pool.ntp.org");
		//TimeInfo timeinfo
	}

}
