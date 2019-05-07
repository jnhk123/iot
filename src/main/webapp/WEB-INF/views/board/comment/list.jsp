<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<c:forEach items="${list}" var="vo" varStatus="status">
${status.index eq 0 ? '<hr>'  : ''}
<%-- <div>${vo.name} [ <fmt:formatDate value="${vo.writedate}"  --%>
<%-- 								pattern="yyyy-MM-dd hh:mm:ss"/>  ] --%>
	<div>${vo.name} [ ${vo.writedate} ]
<!-- 	해당 댓글을 작성한 사용자가 로그인한 경우만 수정/삭제 가능 -->
	<c:if test="${vo.userid eq login_info.userid }">
	<span style="float: right;">
		<a class="small-btn-fill"     id="btn-modify-save${vo.id}" 
			onclick="go_modify_save(${vo.id})">수정</a>
		<a class="small-btn-fill"     id="btn-delete-cancel${vo.id}"
			onclick="go_delete_cancel(${vo.id})">삭제</a>
	</span>
	</c:if>
</div>
<div id="original${vo.id}">${fn: replace(vo.content, newLineChar, '<br>')}</div>
<div id="modify${vo.id}" style="display:none;"></div>
<hr>
</c:forEach>
<!-- <hr> -->

<script type="text/javascript">
function go_delete_cancel(id){
// 	취소버튼 클릭시
	if( $('#btn-delete-cancel' + id).text() == '취소' ){
		display_mode(id, 'd');		
	} else {
		if( confirm('정말 삭제?') ){
			$.ajax({
				url: 'board/comment/delete/'+id,
				success: function() {
					comment_list();
				},
				error: function(req) {
					alert(req.status);
				}
			});
		}
	}
} 

function go_modify_save(id){
// 	수정버튼 클릭시
	if( $('#btn-modify-save'+id).text()=='수정' ){
	// 	원래 댓글의 내용이 변경할 수 있는 태그에 보여져야 한다.
		var tag = "<textarea id='modify-input"+ id 
				+"' style='margin-top:5px; width:98.3%; height:40px;'>" 
				+ $('#original'+id).html().replace(/<br>/g,'\n') 
				+ "</textarea>";
		$('#modify'+id).html( tag );
		display_mode(id, 'm');
	}else{
		var comment = new Object();
		comment.id = id;
		comment.content = $('#modify-input'+id).val();
		
		$.ajax({
			type: 'post',
			url: 'board/comment/update',
			data: JSON.stringify(comment), /* 객체를 json 데이터로 만든다 */
// 			보내는 json데이터에 한글이 있는 경우의 처리
			contentType: 'application/json; charset=utf-8',
			success: function(data){
				alert( "댓글변경 " + data );
				comment_list();
			},
			error: function(req){
				alert( req.status );
			}
		});
		
	}
}
function display_mode(id, mode){
	$('#modify'+id).css('display', mode=='m'? 'block' : 'none');
	$('#original'+id).css('display', mode=='m'? 'none' : 'block');
// 	수정->저장, 삭제->취소
	$('#btn-modify-save'+id).text( mode=='m' ? '저장' : '수정');
	$('#btn-delete-cancel'+id).text( mode=='m' ? '취소' : '삭제');
}
</script>







