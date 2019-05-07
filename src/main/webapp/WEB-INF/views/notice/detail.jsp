<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>공지글 안내</h3>
<table>
<tr><th style="width:140px;">제목</th>
	<td colspan="5">${vo.title}</td>
</tr>
<tr>
	<th>작성자</th>
	<td>${vo.name}</td>
	<th style="width:100px;">작성일자</th>
	<td style="width:100px;">${vo.writedate}</td>
	<th style="width:70px;">조회수</th>
	<td style="width:70px;">${vo.readcnt}</td>
</tr>
<tr>
	<th>내용</th>
	<td style="text-align:left" 
	colspan="5">${fn: replace(vo.content, enter, '<br>')}</td>
</tr>
<tr><th>첨부파일</th>
	<td style="text-align:left" colspan="5">${vo.filename}
	<c:if test="${ !empty vo.filename}">
		<img onclick="location='download.no?id=${vo.id}'" style="cursor:pointer;" 
		src="img/download.png" class="file-img"/>
	</c:if>
	</td>
</tr>
</table><br>
<form method="post" action="list.no">
<input type="hidden" name="curPage" value="${page.curPage}"/>
<input type="hidden" name="search" value="${page.search}"/>
<input type="hidden" name="keyword" value="${page.keyword}"/>
</form>
<a class="btn-fill" onclick="$('form').submit()">목록으로</a>

<!-- 로그인한 사용자가 권한 -->
<c:if test="${!empty login_info }">
<a class="btn-fill" onclick="location='reply.no?id=${vo.id}'">답글쓰기</a>
</c:if>

<!-- 관리자만 수정,삭제 권한 -->
<c:if test="${login_info.admin eq 'Y' }">
<a class="btn-fill" onclick="location='modify.no?id=${vo.id}'">수정</a>
<a class="btn-fill" 
onclick="if( confirm('정말 삭제?') ){ location='delete.no?id=${vo.id}' }">삭제</a>
</c:if> 

</body>
</html>











