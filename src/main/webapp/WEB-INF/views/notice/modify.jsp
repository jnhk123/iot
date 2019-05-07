<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/need_check.js"></script>
</head>
<body>
<h3>공지글안내 수정</h3>
<form method="post" action="update.no" enctype="multipart/form-data">
<input type="hidden" name="id" value="${vo.id}"/>
<table>
<tr><th style="width:140px;">제목</th>
	<td><input type="text" class="need" title="제목" value="${vo.title}" name="title" style="width: 99%"/></td>
</tr>
<tr><th>작성자</th>
	<td>${vo.writer}</td>
</tr>
<tr><th>내용</th>
	<td><textarea name="content" class="need" title="내용"
		style="width:98.5%; height:100px;">${vo.content}</textarea> </td>
</tr>
<tr><th>첨부파일</th>
	<td style="text-align:left">
		<img src="img/delete.png" class="file-img" 
									id="delete-file"/>
		<label id="file-name">${vo.filename}</label>
		<label>
			<img src="img/select.png" class="file-img"/>
			<input type="file" name="file" id="attach-file"/>
		</label>
	</td>
</tr>
</table><br>
<a class="btn-fill" 
	onclick="if( necessary() ) { $('form').submit() }">저장</a>
<a class="btn-empty" onclick="location='detail.no?id=${vo.id}'">취소</a>
<input type="hidden" name="attach"/>
</form>
<script type="text/javascript" src="js/attach_file.js"></script>
<script type="text/javascript">
$(function(){
	//첨부파일명이 있으면 파일삭제 이미지 보이게
	if( '${!empty vo.filename}' == 'true' ){
		$('#delete-file').css('display', 'inline-block');
		$('#file-name').css('padding-right', '15px');
	}
	$('#delete-file').on('click', function(){
		//원래첨부된 파일을 삭제하는 경우
		$('[name=attach]').val('n'); 
	});
});
</script>

</body>
</html>




