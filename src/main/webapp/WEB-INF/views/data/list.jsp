<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#popup-background{
	position: absolute;
	width: 100%;
	height: 100%;
	left: 0; top: 0;
	background: #000000; opacity: 0.3;
	display: none; 
}
	
#popup{
	position: absolute;
	width: 800px; height: 600px;
	left: 50%; top: 50%;
	transform: translate(-50%, -50%);
	border: 3px #666666 solid;
	display: none; 
	z-index: 999999; /* 지도가 background 보다 위로 가게 */
}
#list-top select {
height: 28px; margin-right: 10px; float: left;}
#list-top {padding: 20px 7.5%;}


</style>


</head>
<body>
<h3>공공데이터</h3>
<a class="btn-fill" onclick="pharmacy_list()">약국정보조회</a>
<a class="btn-fill" onclick="animal_sido()">유기동물조회</a>
<div id="list-top" style="display: none; width: 85%"></div>
<div style="margin: 0 auto; padding: 20px;" id="data-list">
</div>
<div id="popup"></div>
<div id="popup-background" onclick="$('#popup, #popup-background').css('display', 'none')"></div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCsrerDHJrp9Wu09Ij7MUELxCTPiYfxfBI"></script>
<script type="text/javascript">
$(document).on('change', '#sido', function() {
	animal_sigungu();
}).on('change', '#sigungu', function() {
	animal_shelter();
}).on('change', '#upkind', function() {
	animal_kind();
});

function animal_kind() {
	$('#data-list').html('');
	$('#list-top').find('#kind').remove();
	if( $('#upkind').val()=='' ) return;
	
	$.ajax({
		url: 'data/animal/kind',
		data : { upkind: $('#upkind').val() },
		success: function( data ) {
			var tag = '<select id="kind" style="width:200px;">';
					+ '<option value="">품종 선택</option>';
					$(data).each( function(idx, item){
						tag += '<option value="'+ item.kindCd +'">'+ item.KNm +'</option>';
					});
			tag += '</select>';
			$('#list-top').find('#upkind').after(tag);
		},
		error: function(req) {
			alert(req.status);
		}
	});
	
}

function animal_type() {
	var tag = '<select id="upkind" style="width:90px;">'
			+ '<option value="">축종 선택</option>'
			+ '<option value="417000">개</option>'
			+ '<option value="422400">고양이</option>'
			+ '<option value="429900">기타</option>'
			+ '</select>';
	$('#list-top').find('#sido').after(tag);
}

function animal_shelter() {
	$('#data-list').html('');
	$('#list-top').find('#shelter').remove();
	if( $('#sigungu').val()=='' ) return;
	$.ajax({
		url: 'data/animal/shelter',
		data: { sido: $('#sido').val(), sigungu: $('#sigungu').val() },
		dataType: 'json',
		success: function(data) {
			var tag = '<select id="shelter" style="width=200px">'
					+ '<option value="">보호소 선택</option>';
				$(data).each(function(idx,item) {
					tag+='<option value="'+ item.careRegNo +'">'+ item.careNm +'</option>';
				});
				tag += '</select>';
				$('#list-top').find("#sigungu").after(tag);
		},
		error: function(req) {
			alert(req.status)
		}
	});
}

function animal_sigungu() {
	$('#data-list').html('');
	$('#list-top').find('#sigungu, #shelter').remove();
	/* $('#list-top').find('#shelter').remove(); */
	if( $('#sido').val()=='' ) return;
	$.ajax({
		url: 'data/animal/sigungu',
		data: { sido: $('#sido').val() },
		dataType: 'json', //서버에서 응답받는 데이터의 형식
		success: function( data ) {
			console.log(data);
			var tag = '<select id="sigungu" style="width:120px;">'
					+ '<option value="">시군구 선택</option>';
			$(data).each(function(idx, item) {
				tag += '<option value="'+ item.orgCd +'">'+ item.orgdownNm +'</option>';
			});
			
			tag += '</select>';
			$('#list-top').find('#sido').after(tag);
		},
		error: function() {
			alert(req.status);
		}
	});
	
}

function animal_list() {
	var animal = new Object();
	animal.sido = $('#sido').val();
	animal.sigungu = $('#sigungu').length > 0 ? $('#sigungu').val() : '';
	animal.shelter = $('#shelter').length > 0 ? $('#shelter').val() : '';
	animal.upkind = $('#upkind').val();
	animal.kind = $('#kind').length > 0 ? $('#kind').val() : '';
	
	$.ajax({
		data: JSON.stringify(animal),
		contentType: 'application/json', //보내는 데이터의 형식
		type : 'post',	//json 데이터를 보낼때는 post
		url: 'data/animal/list',
		success: function( data ) {
			console.log(data);
			var tag = '<table style="width:85%; border:2px solid;">';
			if(data.length==0){
				tag += '<tr><td>해당 유기동물이 없습니다.</td></tr>';
			}else{
				$(data).each(function(idx, item) {
					if( animal.shelter != '' && idx==0 ){
						//보호소 정보를 한번만 출력
						tag +='<tr><td>'+ item.careNm +'</td>'
						+ '<td>'+ item.careAddr + '</td>'
						+ '<td>'+ item.careTel + '</td>'
						+ '</tr></table>'
						+ '<table style="width:85%; border:2px solid; margin-top:10px;">';
					}
					tag += '<tr ' + ( animal.shelter=='' ? '' : 'style="border-top:2px solid"' ) + ' ><td rowspan="3">'
					+ '<img style="width:100px; height:100px;" src="' + item.filename + '"/>'  +'</td>'
					+ '<th>성별</th><td>'+ item.sexCd +'</td>'
					+ '<th>나이</th><td>'+ item.age +'</td>'
					+ '<th>체중</th><td>'+ item.weight +'</td>'
					+ '<th>색상</th><td>'+ item.colorCd +'</td>'
					+ '<th>접수일</th><td>'+ item.happenDt +'</td>'
					+ '</tr>'
					+ '<tr><th>특징</th><td colspan="9" style="text-align:left">'+ item.specialMark +'</td>'
					+ '</tr>'
					+ '<tr ><th>발견장소</th><td colspan="8" style="text-align:left">'+ item.happenPlace +'</td>'
					+ '<td>'+ item.processState +'</td>'
					+ '</tr>';
				if( animal.shelter == ''){
					tag +='<tr style="border-bottom:2px solid"><td colspan="2">'+ item.careNm +'</td>'
						+ '<td colspan="7">'+ item.careAddr + '</td>'
						+ '<td colspan="2">'+ item.careTel + '</td>'
						+ '</tr>';
					}
				});
			}
			tag += '</table><br/>';
			$('#data-list').html(tag);
		},
		error: function(req) {
			alert(req.status);
		}
	});
}
function animal_sido() {
	$('#data-list').html('');
	$('#list-top').css('display', 'block');
	
	$.ajax({
		url: 'data/animal/sido',
		success: function(data) {
			var tag = '<select id="sido" style="width:130px;">'
			+ '<option value="">시도 선택</option>';
			$(data).find('item').each(function() {
				tag += '<option value="'+ $(this).find('orgCd').text() +'">'+ $(this).find('orgdownNm').text() +'</option>';
			});
			
			tag += '</select>';
			tag += '<a class="btn-fill" onclick="animal_list()">조회</a>';
			$('#list-top').html(tag);
			animal_type();
		},
		error: function(req) {
			alert(req.status);
		}
	});
}


function show_map(x, y, name) {
	$('#popup, #popup-background').css('display', 'block');
	var myLatLng = {lat: y, lng: x};

    // Create a map object and specify the DOM element
    // for display.
    var map = new google.maps.Map(document.getElementById('popup'), {
      center: myLatLng,
      zoom: 15
    });

    // Create a marker and set its position.
    var marker = new google.maps.Marker({
      map: map,
      position: myLatLng,
      title: name
    });
}


function pharmacy_list() {
	$('#list-top').css('display', 'none');
	$.ajax({
		url: 'data/pharmacy',
		success: function( data ) {
			var tag = '<table style="width:85%">' + '<tr><th style="width:50px;">번호</th><th style="width:160px;">약국명</th><th style="width:130px;">전화번호</th><th>주소</th></tr>'
			$(data).find('item').each(function( index ) {
				var xy = $(this).find('XPos').text()+ ","
						+$(this).find('YPos').text()+ ",'"
						+$(this).find('yadmNm').text() +"'";
				
				tag += '<tr><td>'+ (index+1) +'</td>'
				+'<td><a onclick="show_map('+ xy + ')">'+ $(this).find('yadmNm').text() +'</a</td>'
				+'<td>'+ $(this).find('telno').text() +'</td>'
				+'<td>'+ $(this).find('addr').text() +'</td>'
				+ '</tr>';	
			});
			tag += '</table>';
			$('#data-list').html(tag);
			//console.log(data);
		},
		error: function(req) {
			alert(req.status);
		}
	});
}
</script>
</body>
</html>