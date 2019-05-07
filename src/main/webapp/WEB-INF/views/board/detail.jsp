<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#comment_regist div { float: left; width: 50%;  margin-top: 20px;}
</style>
</head>
<body>
<h3>방명록 내용</h3>
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
<form method="post" action="list.bo">
<input type="hidden" name="curPage" value="${page.curPage}"/>
<input type="hidden" name="search" value="${page.search}"/>
<input type="hidden" name="keyword" value="${page.keyword}"/>
</form>
<a class="btn-fill" onclick="$('form').submit()">목록으로</a>

<!-- 관리자만 수정,삭제 권한 -->
<c:if test="${login_info.admin eq 'Y' }">
<a class="btn-fill" onclick="location='modify.bo?id=${vo.id}'">수정</a>
<a class="btn-fill" 
onclick="if( confirm('정말 삭제?') ){ location='delete.bo?id=${vo.id}' }">삭제</a>
</c:if> 
<br>
<!-- 댓글부분 -->
<div id="comment_regist"
	style="text-align: center; margin: 0 auto; width: 500px;">
	<div style="text-align: left; ">댓글작성</div>
	<div style="text-align: right;"><a 
			class="small-btn-fill" onclick="go_comment_regist()">등록</a>
	</div>
	<textarea id="comment" style="resize:none; width: 99%; height:60px;"></textarea>
</div>
<div id="comment_list" 
	style="text-align:left; margin: 0 auto; width:500px"></div>

<script type="text/javascript">
$(function(){
	comment_list();
});

function comment_list(){
	$.ajax({
		url: 'board/comment/${vo.id}',
		success: function( data ){
			$('#comment_list').html(data);
		},
		error: function(req){
			alert( req.status );
		}
	});
}

function go_comment_regist(){
	if( ${empty login_info} ){
		alert("댓글을 등록하려면 로그인 하세요");
		return;
	}
	if( $('#comment').val().trim() =='' ){
		alert("등록할 댓글을 입력하세요");
		$('#comment').val('');
		$('#comment').focus();
		return;
	}

	$.ajax({
		type: 'post',
		url: 'board/comment/insert',
		data: { board_id: ${vo.id}, content: $('#comment').val() },
		success: function( data ){
			$('#comment').val('');
			if( data ){
				alert('댓글이 등록되었습니다!');
				comment_list();
			}else{
				alert("댓글 등록 실패!");
			}
		},
		error: function(req){
			alert( req.status );
		}
	});
	
}
</script>

</body>
</html>











