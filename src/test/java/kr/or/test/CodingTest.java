package kr.or.test;

public class CodingTest {

	public static void main(String[] args) {
		CodingTest ct = new CodingTest();
		String w = ")(";
		int index = ct.getBalancedIndex(w);
		System.out.println("w 파싱할때 u가 시작되는 위치 " + index);
		String u = w.substring(0, 2);// 2 = 2-1의 인덱스까지
		System.out.println("w를 u와 v로 분리할때 u는 " + u);
		System.out.println("u값을 재배치 "+ ct.reArrange(u));
		boolean balanceCheck = ct.isValidString(")(");
		System.out.println("균형잡힌 문자열인지 체크 " + balanceCheck);
	}

	private String reArrange(String u) {
		String arrangeString = "";
		for (int i = 1; i < u.length() - 1; i++) {
			System.out.println("여기까지");
			String tmpChar = u.substring(i, i + 1);
			if ("(".equals(tmpChar)) {
				arrangeString += ")";
			} else if (")".equals(tmpChar)) {
				arrangeString += "(";
			}
		}
		return arrangeString;
	}

	private int getBalancedIndex(String w) {
		int index = 0;
		int balanceCount = 0;
		for (int i = 0; i < w.length(); i++) {
			String tmpChar = w.substring(i, i + 1);
			if ("(".equals(tmpChar)) {
				balanceCount++;
			} else if (")".equals(tmpChar)) {
				balanceCount--;
			}
			if (balanceCount == 0) {
				index = i;
				break;
			}
		}
		return index;
	}

	// 1회전 balanceCount = -1;
	private boolean isValidString(String u) {
		int balanceCount = 0;
		for (int i = 0; i < u.length(); i++) {
			String tmpChar = u.substring(i, i + 1);
			if ("(".equals(tmpChar)) {
				balanceCount++;
			} else if (")".equals(tmpChar)) {
				balanceCount--;
			}
			if (balanceCount < 0) {
				return false;
			}
		}
		return true;
	}

}
