package org.edu.vo;
/**
 * PageVO클래스로서 회원,게시판 공통으로 사용됩니다.
 * 1페이지당 보여줄 개수를 이용해서 전체데이터를 분할해서 보여주는 역할
 * 왜필요하는가? 회원또는 게시물이 10만건을 한꺼번에 보여주면, 조회속도가 느리게 됩니다.
 * 1페이지당 10개 또는 20개, 30개 등등 제한을 두어서, 속도향상및 사용자화면을 개선시킵니다.
 * 아래 계산식에서 [] 부분이 멤버변수로 생성해야 할 부분.
 * - 1페이지계산 10[1페이지당출력할개수]x(1[몇번째페이지번호]-1) = 0 1페이지일때
 * - 2페이지계산 10x(2-1) = 10[계산결과나온 시작페이지번호] 2페이지일때
 * - SELECT * FROM tbl_board order by bno desc limit 10, 10;# 10-시작인덱스,10-출력할 개수
 * @author 김일국
 *
 */
public class PageVO {
	//예를 들면 변수 중에 boolean(일반형테이터형변수) / Boolean(대문자로시작-클래스형변수-Null로 입력되었을때 처리하는 로직이 들어 있습니다)
	private Integer perPageNum;//1페이지당 출력할 개수값이 들어가는 변수
	private Integer page;//jsp에서 선택한 페이지 번호값이 들어가는 변수
	private Integer startNo;//[계산식]을 이용해서 나오는 값을 쿼리에서 사용될 시작번호값이 들어가는 변수
	private Boolean prev;//[계산식]페이징에서 이전 번호가 있을때 표시값이 들어가는 변수
	private Boolean next;//[계산식]페이징에서 이후 번호가 있을때 표시값이 들어가는 변수
	//위에 생성 프리뷰, 넥스트 변수값이 있는지 없는지 확인하려면, [계산식]이 필요합니다. 계산할 때 필요한 변수 3개가 필요(아래)
	private int totalCount;//회원[게시물] 전체의 갯수값이 들어가는 변수
	private int startPage;//jsp화면에서 보여주는 페이징 리스트의 시작번호
	private int endPage;//jsp화면에서 보여주는 페이징 리스트의 끝번호
	//start페이지와 end페이지 변수가 필요한 이유?
	//예) < 1 2 3 4 5 6 7 8 9 10 > 페이징 리스트의 시작1 과 끝10 값이 바뀌게 됩니다.
	//예) < 11 12 13 14 15 16 17 18 19 20 > 시작 11 과 끝 20
	//검색에 필요한 변수 2개도 포함시켜서, 컨트롤러에서 매개변수 사용을 축소하게 됩니다.
	private String search_type;//검색조건
	private String search_keyword;//검색어
	
	//전체 클래스에서 [계산식]이 4개 필요합니다. 개발자가 만들어야 합니다.
	
	public Integer getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(Integer perPageNum) {
		this.perPageNum = perPageNum;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getStartNo() {
		return startNo;
	}
	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}
	public Boolean getPrev() {
		return prev;
	}
	public void setPrev(Boolean prev) {
		this.prev = prev;
	}
	public Boolean getNext() {
		return next;
	}
	public void setNext(Boolean next) {
		this.next = next;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public String getSearch_type() {
		return search_type;
	}
	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}
	public String getSearch_keyword() {
		return search_keyword;
	}
	public void setSearch_keyword(String search_keyword) {
		this.search_keyword = search_keyword;
	}
	
}
