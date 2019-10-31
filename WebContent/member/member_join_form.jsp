<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	// 아이디, 패스워드 유효성 검사 결과를 저장하는 전역변수
	var checkIdResult = false, checkPasswdResult = false;

	function changeDomain(domain) {
		// SELECT-OPTION 태그에서 선택된 항목을 email2 필드에 표시
		document.joinForm.email2.value = domain.value;
	}
	
	function checkId(id) {
		// 4-16자리 영문자,숫자 조합 유효성 검사
		var regex = /^[A-Za-z0-9_]{4,16}$/g;
// 		var pattern = new RegExp("/^[A-Za-z0-9]{4,16}$/g");
		
		// 아이디 항목 우측의 DIV 태그에 대한 id 값을 지정하여 해당 태그 Element 가져오기
		var element = document.getElementById('checkIdResult'); // 태그 중 checkIdResult 에 해당하는 id 찾기
		
		if(regex.exec(id.value)) { // 입력받은 id 가져와서 정규표현식을 통해 유효성 검사 수행
// 			alert('유효성 검사 통과');
			element.innerHTML = "사용 가능한 아이디";
			checkIdResult = true;
		} else {
// 			alert('유효성 검사 탈락');
			element.innerHTML = "사용 불가능한 아이디";
			checkIdResult = false;
		}
		
	}
	
	function checkPasswd(passwd) {
		// 8-20자리 영문자,숫자,특수문자 조합 유효성 검사
		var lengthCaseRegex = /[A-Za-z0-9!@#$%^&*()_+]{8,20}/; // 길이
		var upperCaseRegex = /[A-Z]/; // 대문자
		var lowerCaseRegex = /[a-z]/; // 소문자
		var digitCaseRegex = /[0-9]/; // 숫자
		var specialCharRegex = /[!@#$%^&*()_+]/ // 특수문자
		
		// 패스워드 항목 우측의 DIV 태그에 대한 id 값을 지정하여 해당 태그 Element 가져오기
		var element = document.getElementById('checkPasswdResult');
		
		// 검사 항목에 대한 모든 결과가 true 이면 사용 가능, 아니면 불가능
		if(lengthCaseRegex.exec(passwd.value) && upperCaseRegex.exec(passwd.value)
				&& lowerCaseRegex.exec(passwd.value) && digitCaseRegex.exec(passwd.value)
				&& specialCharRegex.exec(passwd.value)) {
			element.innerHTML = "사용 가능한 패스워드";
			checkPasswdResult = true;
		} else {
			element.innerHTML = "사용 불가능한 패스워드";
			checkPasswdResult = false;
		}
		
	}
	
	function checkSubmit() {
		// 아이디와 패스워드 유효성 검사가 완료되었을 때만 회원가입 수행
		// => 아이디, 패스워드를 별도로 호출하여 유효성 검사를 수행하므로 상태를 저장할 전역변수 필요
		if(checkIdResult == true && checkPasswdResult == true) {
			return true;
		} else {
			alert('아이디 또는 패스워드 규칙 확인 필수!');
			return false;
		}
	}
	
</script>
</head>
<body>
	<h1>회원 가입</h1>
	<form action="MemberJoinPro.me" method="post" name="joinForm" onsubmit="return checkSubmit()">
		<table border="1">
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" required="required" size="20"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<input type="radio" name="gender" value="남">남&nbsp;&nbsp;
					<input type="radio" name="gender" value="여">여
				</td>
			</tr>
			<tr>
				<td>나이</td>
				<td><input type="text" name="age" required="required" size="10"></td>
			</tr>
			<tr>
				<td>E-Mail</td>
				<td>
					<input type="text" name="email1" required="required" size="10">@
					<input type="text" name="email2" required="required" size="10">
					<select name="selectDomain" onchange="changeDomain(this)">
						<option value="">직접입력</option>	
						<option value="naver.com">naver.com</option>
						<option value="nate.com">nate.com</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>
					<input type="text" name="id" required="required" size="20" 
						placeholder="4-16자리 영문자,숫자 조합" onkeyup="checkId(this)">
					<span id="checkIdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
				</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>
					<input type="password" name="passwd" required="required" size="20" 
						placeholder="8-20자리 영문자,숫자,특수문자 조합" onkeyup="checkPasswd(this)">
					<span id="checkPasswdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="회원가입">
					<input type="button" value="취소" onclick="history.back()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>