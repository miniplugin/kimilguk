<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- html주석시작 jsp: JavaServerPage 자바서버용페이지 -->
<%-- 자바주석시작 하이픈(-)
- jsp파일에서 자바프로그램을 사용할때 쓰는 태그가 <% %> 감싸주게 됩니다.
- 다른언어 PHP예를 들면 <?php ?>꺽쇠로 감싸주게 됩니다.
- @ page골뱅이는 애노테이션으로 발음-페이지지시자는 필수입니다. 랭귀지:java, 
- 콘텐츠타입:text/html; 문자셋:유니코드  페이지인코딩:UTF-8 
자바주석 끝 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>반응형 홈페이지</title>
</head>
<body>
HomeController.java 에서 index.jsp로 매칭 시킨 결과가 나오는 페이지 입니다.
왜 /resoureces/sample/index.html 이렇게 접근하지 않고,
jsp프로그램에서는 위 html처럼 바로접근 못하고, 홈 컨트롤러자바를 거쳐서 index.jsp를 접근하는 이유는 보안때문 입니다.
그래서, 은행, 관공서, 대학에서 대부분 사용합니다.(스프링을 배우는 목적)
다른 예로, php프로그램은 위에 있는 html처럼 파일에 바로 접근이 가능합니다.(그래서, 중소기업홈페이지에서 사용)
</body>
</html>