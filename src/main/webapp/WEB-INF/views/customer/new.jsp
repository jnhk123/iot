<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
input[name=phone]{
	width: 60px;
}
</style>
</head>
<body>
<h3>신규고객등록</h3>
<form method="post" action="insert.cs">
<table>
<tr><th>고객명</th>
	<td><input type="text" name="name"/></td>
</tr>
<tr><th>성별</th>
	<td><label><input type="radio" 
				name="gender" value="남"/>남</label>&nbsp;&nbsp;&nbsp;
		<label><input type="radio" 
				name="gender" value="여" checked/>여</label>
	</td>
</tr>
<tr><th>이메일</th>
	<td><input type="text" name="email" /></td>
</tr>
<tr><th>전화번호</th>
	<td><input type="text" name="phone" />
		- <input type="text" name="phone" />
		- <input type="text" name="phone" />
	</td>
</tr>
</table><br>
<a class="btn-fill" onclick="$('form').submit()">저장</a>
<a class="btn-empty" href="list.cs">취소</a>
</form>

</body>
</html>





