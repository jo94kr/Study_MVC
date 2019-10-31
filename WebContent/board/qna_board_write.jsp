<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 현재 세션 객체에 "sId" 세션값이 존재하지 않을 경우
	// alert 창에 "로그인이 필요합니다" 출력 후 LoginForm.me 로 이동
	String sId = null;

	if(session.getAttribute("sId") != null) {
		sId = (String)session.getAttribute("sId");
	}
%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	#registForm {
		width: 500px;
		height: 610px;
		border: 1p solid red;
		margin: auto;
	}
	
	h1 {
		text-align: center;
	}
	
	table {
		margin: auto;
		width: 450px;
	}
	
	.td_left {
		width: 150px;
		background: orange;
	}
	
	.td_right {
		width: 300px;
		background: skyblue;
	}
	
	#commandCell {
		text-align: center;
	}
</style>
<script type="text/javascript">
	function checkSession(sId) {
		// 세션 아이디가 null 일 경우 메세지 출력 및 로그인 화면으로 이동
		if(sId == null) {
			alert("로그인이 필요합니다");
			location.href="LoginForm.me";
		}
	}
</script>
</head>
<!-- body 가 로드될 때 자바스크립트 checkSession() 함수 호출 => 파라미터로 세션 아이디 전달 -->
<body onload="checkSession(<%=sId%>)">
	
	<!-- 게시판 등록 -->
	<section id="writeForm">
		<h1>게시판 글 등록</h1>
		<form action="BoardWritePro.bo" method="post" enctype="multipart/form-data" name="boardForm">
			<table>
				<tr>
					<td class="td_left"><label for="board_name">글쓴이</label></td>
					<td class="td_right"><input type="text" name="board_name" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_pass">비밀번호</label></td>
					<td class="td_right"><input type="password" name="board_pass" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_subject">제목</label></td>
					<td class="td_right"><input type="text" name="board_subject" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_content">내용</label></td>
					<td class="td_right">
						<textarea id="board_content" name="board_content" cols="40" rows="15" required="required"></textarea>
					</td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_file">파일 첨부</label></td>
					<td class="td_right"><input type="file" name="board_file" required="required" /></td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp;
				<input type="reset" value="다시쓰기">
			</section>
		</form>
	</section>
	
</body>
</html>