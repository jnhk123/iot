<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>방명록목록</h3>
<form action="list.bo" method="post">
<p id="list-top">
	<span style="float:left; display:flex;">
		<select name="search" style="height:24px; width:80px; margin-right:5px; " >
			<option value="all" ${page.search eq 'all' ? 'selected' : ''}>전체</option>
			<option value="title" ${page.search eq 'title' ? 'selected' : ''}>제목</option>
			<option value="userid" ${page.search eq 'writer' ? 'selected' : ''}>작성자</option>
			<option value="content" ${page.search eq 'content' ? 'selected' : ''}>내용</option>
		</select>
		<input name="keyword" type="text" value="${page.keyword}"
		style="margin-right:5px; vertical-align:middle; height: 18px; width:350px;"/>
		<a class="btn-fill" onclick="$('form').submit()">검색</a>
		<select name="pagelist" onchange="$('form').submit()"  style="height:24px; width:70px; margin-left:5px; " >
			<option value="10" ${page.pageList eq 10 ? 'selected' : ''}>기본</option>
			<option value="15" ${page.pageList eq 15 ? 'selected' : ''}>15개씩</option>
			<option value="20" ${page.pageList eq 20 ? 'selected' : ''}>20개씩</option>
		</select>
	</span>
<!-- 로그인한 경우 -->
	<c:if test="${ !empty login_info.admin }">
	<a class="btn-fill" onclick="location='new.bo'">글쓰기</a>
	</c:if>
</p>
<input type="hidden" name="curPage"/>
</form>

<table>
<tr><th style="width:60px;">번호</th>
	<th>제목</th>
	<th style="width:110px;">작성자</th>
	<th style="width:110px;">작성일자</th>
	<th style="width:70px;">첨부파일</th>
</tr>
<c:forEach items="${page.list}" var="vo">
<tr><td>${vo.no}</td>
	<td style="text-align:left;">
		<a onclick="location='detail.bo?read=1&id=${vo.id}'">${vo.title}</a></td>
	<td>${vo.name}</td>
	<td>${vo.writedate}</td>
	<td>${empty vo.filename ? '' : '<img class="file-img" src="img/attach.png" />' }</td>
</tr>
</c:forEach>
</table><br>

<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</body>
</html>










