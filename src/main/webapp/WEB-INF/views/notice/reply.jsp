<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>답글쓰기</h3>
<form action="reply_insert.no" method="post" enctype="multipart/form-data">
<table>
<tr><th style="width:140px;">제목</th>
	<td><input type="text" name="title" title="제목" class="need"
				style="width: 98.5%"/></td>
</tr>
<tr><th>작성자</th>
	<td style="text-align: left;">
		<input type="text" name="writer" title="작성자"  class="need" /></td>
</tr>
<tr><th>내용</th>
	<td><textarea name="content" class="need" title="내용" 
		style="width: 98.5%; height:100px;"></textarea> </td>
</tr>
<tr><th>첨부파일</th>
	<td style="text-align:left">
		<img src="img/delete.png" id="delete-file" class="file-img"/>
		<label id="file-name"></label>
		<label>
		<img src="img/select.png" class="file-img"/>
		<input type="file" name="attach_file" id="attach-file" />
		</label>
	</td>
</tr>
</table><br>
<a class="btn-fill" onclick="if( necessary() ) { $('form').submit() }">저장</a>
<a class="btn-empty" 
	onclick="location='detail.no?id=${vo.id}'">취소</a>
<input type="hidden" name="root" value="${vo.root}"/>	
<input type="hidden" name="step" value="${vo.step}"/>	
<input type="hidden" name="indent" value="${vo.indent}"/>	
</form>
<script type="text/javascript" src="js/need_check.js"></script>
<script type="text/javascript" src="js/attach_file.js"></script>

</body>
</html>









