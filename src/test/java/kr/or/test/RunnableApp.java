package kr.or.test;

public class RunnableApp implements Runnable {

	public static void main(String[] args) {
		// Runnable 인터페이스를 구현하는 내용
		(new Thread(new RunnableApp())).start();
	}

	@Override
	public void run() {
		// 러너블 인터페이스 구현클래스RunnableApp을 start()하면 자동 실행
		System.out.println("러너블시작시 자동실행됩니다.!");
	}	

}
