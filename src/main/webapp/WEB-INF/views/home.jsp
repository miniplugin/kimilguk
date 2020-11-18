<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>스프링</title>
</head>
<body>
<h1>
	Hello world! 김일국 입니다. 
</h1>
<p> <c:out value="${TomcatserverTime}"></c:out>
<!-- c:out 태그를 사용하는 이유는 해킹 방지용으로 필요합니다. 시큐어코딩(보안코딩) -->
</p>
<P>  게시판글을 불러온다고 예상하면: 현재 서버의 시간은 ${TomcatserverTime}
 입니다.. </P>
</body>
</html>
