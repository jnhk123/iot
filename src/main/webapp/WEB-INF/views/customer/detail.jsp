<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>[ ${vo.name} ] 고객정보</h3>
<table>
<tr><th style="width: 140px;">성별</th>
	<td>${vo.gender}</td>
</tr>
<tr><th>이메일</th>
	<td>${vo.email}</td>
</tr>
<tr><th>전화번호</th>
	<td>${vo.phone}</td>
</tr>
</table><br>

<a class="btn-fill" onclick="location='list.cs'">고객목록</a>
<a class="btn-fill" onclick="location='modify.cs?id=${vo.id}'">수정</a>
<a class="btn-fill" 
onclick="if( confirm('정말 삭제??') ){ location='delete.cs?id=${vo.id}' }">삭제</a>
</body>
</html>









