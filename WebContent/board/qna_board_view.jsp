<%@page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// page, BoardBean 객체 파라미터 가져오기
	String nowPage = (String)request.getAttribute("page");
	BoardBean article = (BoardBean)request.getAttribute("article");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<style type="text/css">
	#articleForm {
		width: 500px;
		height: 500px;
		border: 1p solid red;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	#basicInfoArea {
		height: 40px;
		text-align: center;
	}
	
	#articleContentArea {
		background: orange;
		margin-top: 20px;
		height: 350px;
		text-align: center;
		overflow: auto;
		white-space: pre-line;
	}
	
	#commandList {
		margin: auto;
		width: 500px;
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 게시판 상세내용 보기 -->
	<section id="articleForm">
		<h2>글 상세내용 보기</h2>
		<section id="basicInfoArea">
			제 목 : <%= article.getBoard_subject() %><br>
			첨부파일 : 
			<%
					if(article.getBoard_file() != null) {
						%> <a href="file_down?downFile=<%=article.getBoard_file()%>"><%=article.getBoard_file() %></a> <%
					}
			%>
		</section>
		<section id="articleContentArea">
			<%= article.getBoard_content() %>
		</section>
	</section>
	<section id="commandList">
<%-- 		<a href="BoardReplyForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>">[답변]</a> --%>
<%-- 		<a href="BoardModifyForm.bo?board_num=<%=article.getBoard_num()%>">[수정]</a> --%>
<%-- 		<a href="BoardDeleteForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>">[삭제]</a> --%>
<%-- 		<a href="BoardList.bo?page=<%=nowPage%>">[목록]</a> --%>
		<input type="button" value="답변" onclick="location.href='BoardReplyForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>'">
		<input type="button" value="수정" onclick="location.href='BoardModifyForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>'">
		<input type="button" value="삭제" onclick="location.href='BoardDeleteForm.bo?board_num=<%=article.getBoard_num()%>&page=<%=nowPage%>'">
		<input type="button" value="목록" onclick="location.href='BoardList.bo?page=<%=nowPage%>'">
	</section>
</body>
</html>




