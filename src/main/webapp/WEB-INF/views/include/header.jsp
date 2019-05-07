<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<style type="text/css">
#header {
  	width:80%;  
	height: 50px;
 	margin: 10px auto;
}
#header > * {  float: left;  }
#header ul li { 
	display: inline;
	padding-left: 35px;		 
}
#sub{
	width: 100%; height: 70px; display: none;
	position: absolute;
	top: 72px;
	background-color: #e5efff;
	border-bottom: 3px solid #3367d6;
	z-index: 999999999;
}
.submenu{
	position: absolute; display: none;
	width: 200px; text-align: left;
}
.submenu ul {
	margin: 0; padding: 0;
}
.submenu ul li{
	display: block; padding-top: 10px;
}
.submenu ul li:hover{
	font-weight: bold;
}
.category { color: red; font-weight: bold; }
</style>
<script type="text/javascript">
	$(document).on('mouseover', '#header ul li', function() {
		if( $('.submenu:eq('+ $(this).index() +')').children().length > 0 ){
			//선택한 서브메뉴 보이게			
			$('#sub, .submenu:eq('+ $(this).index() +')').css('display', 'block');
			//서브메뉴목록 보여질 위치
			$('.submenu').css('left', $(this).children().offset().left );
			//선택한 서브메뉴아닌것은 안보이게
			$('.submenu:not( :eq('+ $(this).index() +') )').css('display', 'none');
			
		}else{
			$('#sub').css('display', 'none');
		}
	}).on('mouseleave', '#sub', function() {
		$('#sub').css('display', 'none');
	});
</script>
<div id="header">
<a><img onclick="location='<c:url value="/" />'" 
		src="img/hanul.logo.png" /></a>
<ul>
<!-- 	관리자로 로그인되어 있는 경우만 고객관리 볼 수 있음 -->
	<c:if test="${login_info.admin eq 'Y'}">
	<li><a class="${category eq 'cs' ? 'category' : ''}" onclick="location='list.cs'">고객관리</a></li>
	</c:if>
	<li><a class="${category eq 'no' ? 'category' : ''}" 
				onclick="location='list.no'">공지사항</a></li>
	<li><a class="${category eq 'bo' ? 'category' : ''}" 
				onclick="location='list.bo'">방명록</a></li>
	<li><a class="${category eq 'pd' ? 'category' : ''}" 
				onclick="location='list.pd'">공공데이터</a></li>				
	<li><a class="${category eq 'vi' ? 'category' : ''}">데이터시각화</a></li>				
</ul>

<c:if test="${ !empty login_info}">
<p style="float:right;">
	${login_info.userid} [ ${login_info.name} ]
	<a class="btn-fill" onclick="go_logout()">로그아웃</a>
</p>
</c:if>

<c:if test="${empty login_info}">
<p style="float:right;">
	<a class="btn-fill" onclick="go_login()">로그인</a>
	<a class="btn-fill" onclick="location='member'">회원가입</a>
</p>
<span style="width:120px; float:right">
	<input type="text" id="userid" placeholder="아이디"
		style="width:100px; height:18px;"/>
	<input onkeypress="if( event.keyCode==13 ) { go_login() }" 
	type="password" id="userpwd" placeholder="비밀번호"
		style="width:100px; height:18px;"/>
</span>
</c:if>
</div><hr>
<div id="sub">
<c:if test="${login_info.admin eq 'Y' }">
	<div class="submenu"></div>
</c:if>
	<div class="submenu"></div>
	<div class="submenu"></div>
	<div class="submenu">
		<ul><li>약국정보</li>
			<li>유기동물정보</li>
		</ul>
	</div>
	<div class="submenu">
		<ul>
			<li><a onclick="location='employees.vi'">사원수</a></li>
			<li><a onclick="location='recruitment.vi'">채용인원</a></li>
		</ul>
	</div>
</div>

<script type="text/javascript">
function go_logout(){
	$.ajax({
		url: 'logout',
		success: function(){
// 			location.reload(); //1. 현재페이지에 남아있도록
			location = "home"; //2. 무조건 홈으로
			
//3. 고객관리에서 로그아웃하는 경우는 홈으로
// 			if( ${category eq 'cs'} ){
// 				location = "home";				
// 			}else{
				//수정화면(modify.no, modify.bo)인 경우 상세화면으로
// 				location.reload(); //1. 현재페이지에 남아있도록				
// 			}
		},
		error: function(req){
			alert(req.status);
		}
	});
}
function go_login(){
	$.ajax({
		type: 'post',
		url: 'login',
		data: { userid:$('#userid').val(), 
				userpwd:$('#userpwd').val() },
		success: function( result ){
			if( result ){
				location.reload();
			}else{
				alert("아이디나 비밀번호가 일치하지 않습니다!");
				$('#userid').val('');
				$('#userpwd').val('');
				$('#userid').focus();
			}
		},
		error: function(req){
			alert(req.status);
		}
	});
}

</script>