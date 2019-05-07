<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
table td{ text-align: left; }
input[name=phone], input[name=post] {
 	width: 50px; text-align: center; }
</style>
<link rel="stylesheet" 
href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
.valid, .invalid { font-size: 9pt; font-weight: bold; }
.valid {color: green;}
.invalid {color: red;}
</style>
<script type="text/javascript" src="js/join_check.js"></script>
<script type="text/javascript">
function validate( tag ){
	var data = $('[name='+ tag +']').val();
	if( tag == 'userid' ){
		data = join.id_status( data );
	}else if( tag == 'userpwd' ){
		data = join.pwd_status( data );
	}else if( tag == 'userpwd_ck' ){
		data = join.pwd_ck_status( data );
	}else if( tag == 'email' ){
		data = join.email_status( data );
	}
	
	$('#' + tag + '_status').text( data.desc );
	$('#' + tag + '_status').removeClass('valid invalid');
	$('#' + tag + '_status').addClass( data.code=='valid' ? 'valid' : 'invalid' );
	return data;
}
function usable(){
	var result = validate('userid');
	if( result.code != 'valid' ){
		alert( result.desc );
		return;
	}
	
	$.ajax({
		type: 'post',
		url: 'id_check',
		data: { userid: $('[name=userid]').val() },
		success: function( data ){
 			data = join.id_usable( data );
 			$('#id_check').val( data.code ); //usable/unusable
			$('#userid_status').text( data.desc );
			$('#userid_status').removeClass('valid invalid');
			$('#userid_status').addClass( 
					data.code=='usable' ? 'valid' : 'invalid' );
		},
		error: function(req){
			alert( req.status );
		}
	});
}
</script>
</head>
<body>
<h3>회원가입</h3>
<form method="post" action="join">
<table style="width: 60%">
<tr><th style="width: 120px;">* 성명</th>
	<td><input type="text" name="name"/></td>
</tr>
<tr><th>* 아이디</th>
	<td><input type="text" name="userid" 
			onkeyup="$('#id_check').val('n'); validate('userid')" /><input 
				id="btn_usable" onclick="usable()" type="button" value="중복확인"/><br>
		<div id="userid_status" class="valid">아이디를 입력하세요(영문소문자,숫자만 사용)</div>
	</td>
</tr>
<tr><th>* 비밀번호</th>
	<td><input type="password" name="userpwd"
						onkeyup="validate('userpwd')" /><br>
		<div id="userpwd_status" class="valid">비밀번호를 입력하세요(영문대/소문자, 숫자를 모두 포함)</div>
	</td>
</tr>
<tr><th>* 비밀번호확인</th>
	<td><input type="password" name="userpwd_ck" 
						onkeyup="validate('userpwd_ck')" /><br>
		<div id="userpwd_ck_status" class="valid">비밀번호를 다시 입력하세요</div>
	</td>
</tr>
<tr><th>* 성별</th>
	<td><label><input type="radio" name="gender" 
					value="남" checked/>남</label>&nbsp;&nbsp;&nbsp;
		<label><input type="radio" name="gender" 
					value="여"/>여</label>
	</td>
</tr>
<tr><th>* 이메일</th>
	<td><input type="text" name="email"
					onkeyup="validate('email')" /><br>
		<div id="email_status" class="valid">이메일을 입력하세요</div>
	</td>
</tr>
<tr><th>생년월일</th>
	<td><input type="text" name="birth" readonly />
		<img id="x" src="img/x.png" style="display:none;"/>
	</td>
</tr>
<tr><th>전화번호</th>
	<td><input type="text" name="phone" maxlength="3"/>
		- <input type="text" name="phone" maxlength="4"/>
		- <input type="text" name="phone" maxlength="4"/>
		</td>
</tr>
<tr><th>주소</th>
	<td><input onclick="go_post()" type="button" value="우편번호찾기"/>
		<input type="text" name="post"/><br>
		<input type="text" name="address" style="width:99%"/>
		<input type="text" name="address" style="width:99%"/>
	</td>
</tr>
</table><br>
<a class="btn-fill" onclick="go_join()">회원가입</a>
<a class="btn-empty" onclick="history.go(-1)">취소</a>
<input type="hidden" id="id_check" value="n"/>
</form>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" 
src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript">
function go_join(){
	if( $('[name=name]').val()=='' ){
		alert('성명을 입력하세요');
		$('[name=name]').focus();
		return;
	}
	// 각 항목의 유효성을 확인한다.
	// 아이디
	// 중복확인하지 않은 경우
	if( $('#id_check').val()=='n' ){
		//유효한 조합인지부터 판단한다
		if( !item_check( 'userid' ) ) return;
		else{
			alert( join.id.valid.desc );
			$('#btn_usable').focus();
			return;
		}
	}else if( $('#id_check').val()=='unusable' ) {
	// 유효하지 않은 경우: 이미 사용중인 경우
		alert( join.id.unusable.desc );
		$('[name=userid]').focus();
		return;
	}
	
	// 비밀번호
	if( ! item_check( 'userpwd' ) ) return;
	// 비밀번호확인
	if( ! item_check( 'userpwd_ck' ) ) return;
	// 이메일
	if( ! item_check( 'email' ) ) return;
	
	$('form').submit();
}

function item_check(item){
	//유효한 형태인지부터 판단한다
	var data = validate( item );
	if( data.code != 'valid' ){
		alert( data.desc );
		$('[name=' + item + ']').focus();
		return false;
	}else
		return true;
}



$(function(){
	$('[name=birth]').datepicker({
		dateFormat: 'yy-mm-dd',
		changeYear: true,
		changeMonth: true,
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		beforeShowDay: after
	});
	
});

$('[name=birth]').change(function(){
	$('#x').css('display', 'inline-block');	
});
$('#x').click(function(){
	$('[name=birth]').val('');
	$('#x').css('display', 'none');	
});

function after(date){
	if( date > new Date() )
		return [false];
	else
		return [true];
}

function go_post(){
	new daum.Postcode({
        oncomplete: function(data) {
        	var address, post;
        	//사용자의 선택 : userSelectedType(J/R)
        	//지번: 주소 jibunAddress
        	if( data.userSelectedType == 'J' ){
        		address = data.jibunAddress;
        		post = data.postcode;
        	}else{
        	//도로명: 주소 roadAddress
        		post = data.zonecode;
        		address = data.roadAddress;
        		
        		var extra = '';
        		if( data.bname != '' ) extra = data.bname;
        		
        		if( data.buildingName != '' )
        			extra += (extra=='' ? '' : ',') + data.buildingName;
        		
        		if(extra != '') address += '(' + extra + ')';
        	}
        	
        	$('[name=post]').val( post );
//         	$('[name=address]').eq(0).val( address );
        	$('[name=address]:eq(0)').val( address );
        }
    }).open();
}

</script>
</body>
</html>




