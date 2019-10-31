<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 현재 세션 객체에 "sId" 세션값이 존재할 경우 String 타입 변수 sId 에 저장
	String sId = null;

	if(session.getAttribute("sId") != null) {
		sId = (String)session.getAttribute("sId");
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC_Board</title>
<style type="text/css">
	#login {
		text-align: right;
	}
</style>
</head>
<body>
	<section id="login">
		<!-- 세션에 ID(sId)가 있을 경우 로그아웃 표시(LogoutPro.me), 세션 ID(sId) 가 없을 경우 로그인 표시 -->
	<%if(sId == null) { %>
		<a href="LoginForm.me">로그인</a>
	<%} else { %>
		<%=sId %>님 | <a href="LogoutPro.me">로그아웃</a>
	<%} %>
	</section>
	<h1>MVC_Board Index</h1>
	<h2><a href="BoardWriteForm.bo">글쓰기</a></h2>
	<h2><a href="BoardList.bo">글목록</a></h2>
</body>
</html>














