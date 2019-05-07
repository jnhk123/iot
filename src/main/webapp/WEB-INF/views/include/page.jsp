<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<style type="text/css">
	.page_on, .page_off, .page_last, .page_next, .page_first, .page_prev {
		display: inline-block;
		width: 30px;
		line-height: 28px;
	}
	.page_on {
		border: 1px solid gray;
		font-weight: bold;
		background-color: gray;
		color: #ffffff; 
	}
	.page_last, .page_next, .page_first, .page_prev{
		border: 1px #d0d0d0 solid;
	}
	.page_next{
		background: url("img/page_next.jpg") center no-repeat;
	}
	.page_last{
		background: url("img/page_last.jpg") center no-repeat;
	}
	.page_first{
		background: url("img/page_first.jpg") center no-repeat;
	}
	.page_prev{
		background: url("img/page_prev.jpg") center no-repeat;
	}
	
</style>
  
<c:if test="${page.curBlock gt 1}">
	<a class="page_first" onclick="go_page(1)" title="처음">&nbsp;</a>
	<a class="page_prev" 
		onclick="go_page( ${page.beginPage-page.blockPage} )" title="이전">&nbsp;</a>
</c:if>
  
<c:forEach var="i" begin="${page.beginPage}" end="${page.endPage}">
	<c:if test="${i eq page.curPage}">
		<span class="page_on">${i}</span>
	</c:if>
	<c:if test="${i ne page.curPage}">
		<a class="page_off" onclick="go_page( ${i} )">${i}</a>
	</c:if>
</c:forEach>

<c:if test="${ page.curBlock lt page.totalBlock }">
	<a class="page_next" onclick="go_page( ${page.endPage+1} )" title="다음">&nbsp;</a>
	<a class="page_last" onclick="go_page( ${page.totalPage} )" title="마지막">&nbsp;</a>
</c:if>

<script type="text/javascript">
function go_page(no){
	$('[name=curPage]').val( no );
	$('form').submit();
}
</script>


