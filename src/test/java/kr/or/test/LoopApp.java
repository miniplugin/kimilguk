package kr.or.test;

public class LoopApp {

	public static void main(String[] args) {
		// 반복문중에 for문
		// 메서드에서 사용하는 변수 아래 2줄
		int[] numbers = {11,21,31,41,51,61,71,81,91,101};//배열변수 선언
		int cnt;//반복횟수 카운트(세는)하는 역할 변수 선언
		//for문(반복의시작값정의;반복의종료값정의;반복할때 증가하는값) { 반복할때 구현내용 }
		for(cnt=0;cnt<10;cnt++) {//cnt++ => cnt=cnt+1; 의미 1씩 증가하는의미
			//System.out.println("현재 카운터는 " + numbers[cnt]);
			//println함수는 한번 출력후 newline 엔터치는 역할.
			//10번 반복을 하는데, numbers[0], numbers[1],.... numbers[9] 출력
		}
		
		//향상된 for문, 스프링 jsp에서 제일많이 사용하게 될 구문.(아래)
		String[] names = {"사용자1","사용자2","사용자3","사용자4","사용자5"};
		//int length = names.length;//length기본형변수에는 5가 들어가게 됩니다.
		//names는 클래스형 변수지만, 인스턴스는 아닙니다.
		char name = '사';//임베디드장비와 자바프로그램 통신시 이것을 사용합니다.
		//임베디드 프로그램에서 char을 사용하는 이유는 메모리를 효율적으로 사용하기 위해서 char를 사용합니다.
		//마지막달에 아두이노 임베디드 프로그램 배우실때는 char 형을 사용하게 됩니다.
		//자바에서 String형 큰따옴표는 문자열데이터 감싸줄때, char형 작은따옴표는 1개문자데이터를 감싸줄때
		//배열타입[] 1차원배열 = 가로선 1row 하나의 줄(라인)
		//향상된 for문에서는 시작값,끝값,증가값 필요없습니다.
		for(String user_name:names) {
			//System.out.println("등록된 사용자이름은 " + user_name);
		}
		
		//향상된 for문, 회원정보를 jsp로 출력하기 현장과 비슷하게 구현
		String[][] members = {
				{"admin","관리자","admin@abc.com","true","2020-12-04","ROLE_ADMIN"},
				{"user","사용자1","user@abc.com","false","2020-12-04","ROLE_USER"}
		};
		//위에 members 2차원배열 변수가 노랗게 나오는 이유는, 정의는 했는데, 사용하지 않아서 그렇습니다.
		//아래쪽에서 사용하면, 노란언더라인 없어집니다.
		//배열타입[][] 2차원배열 = 표 1row, 2row 리스트가 있는 줄(여러개 라인)
		System.out.println("======회원리스트 입니다.======");
		System.out.println("user_id | user_name | email | use | regdate | levels");
		for(String[] member:members) {
			System.out.println(member[0]+"|"+member[1]+"|"+member[2]+"|"+member[3]+
					"|"+member[4]+"|"+member[5]);
		}
	}

}
