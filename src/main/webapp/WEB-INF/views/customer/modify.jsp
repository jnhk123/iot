<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
input[name=phone]{ width: 50px; text-align: center;}
</style>
</head>
<body>
<h3>[ ${vo.name} ] 고객정보수정</h3>
<form method="post" action="update.cs">
<input type="hidden" name="id" value="${vo.id}"/>
<table>
<tr><th style="width:140px;">성별</th>
	<td><label><input ${vo.gender eq '남' ? 'checked' : ''} 
			name="gender" type="radio" value="남"/>남</label>&nbsp;&nbsp;&nbsp;
		<label><input ${vo.gender eq '여' ? 'checked' : ''} 
			name="gender" type="radio" value="여"/>여</label>
	</td>
</tr>
<tr><th>이메일</th>
	<td><input name="email" type="text" value="${vo.email}"/></td>
</tr>
<tr><th>전화번호</th>
<!-- vo.phone : 010-1234-5678
	 split(vo.phone, '-') : [0]010 [1]1234 [2]5678
  -->
	<td><input name="phone" maxlength="3" type="text" 
				value="${fn: split(vo.phone, '-')[0]}"/>
		- <input name="phone" maxlength="4" type="text" 
				value="${fn: split(vo.phone, '-')[1]}"/>
		- <input name="phone" maxlength="4" type="text" 
				value="${fn: split(vo.phone, '-')[2]}"/>
	</td>
</tr>
</table><br>
<a class="btn-fill" onclick="$('form').submit()">저장</a>
<a class="btn-empty" onclick="location='detail.cs?id=${vo.id}'">취소</a>
</form>

</body>
</html>