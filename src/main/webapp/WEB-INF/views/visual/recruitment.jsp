<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#kind { margin: 0 auto; padding: 0; }
#kind li { display: inline-block; text-align: left; }
#kind li:not(:last-child) { margin-right: 50px; }
#kind * {vertical-align: top;}
.c3-axis { font-size: 1.5em }
.c3-axis-x .tick { font-weight: bold; }
.c3-chart {font-size: 1.5em; font-weight: bold;}
</style>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.0/c3.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.9.2/d3.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.0/c3.min.css">
<script type="text/javascript">
$(document).on('change','input[name=gubun]', function() {
	make_chart();
}).on('change', '#top3', function() {
	make_chart();
});

$(function() {
	make_chart();
});
function make_chart() {
	$('#chart').html('');
	var gubun = $('input[name=gubun]:checked').val() == 'year' ? '년' : '월';
	//체크박스가 체크된 경우는 상위 3개부서에 대해서
	if( $('#top3').prop('checked') )
		top3_type(gubun); 
	else
		basic_type(gubun);
}
function top3_type(gubun) {
	$.ajax({
		url: 'visual/top3/' + $('input[name=gubun]:checked').val(),
		success: function(data) {
			/* console.log(data); */
			var info = [];
			if( gubun == '년'){
				
			info.push ( new Array(gubun,'2001년', '2002년', '2003년', '2004년', '2005년'
					, '2006년', '2007년', '2008년') );
			$.each(data, function(idx, item) {
				info.push( new Array( item.department_name, item.y2001, item.y2002
						, item.y2003, item.y2004, item.y2005, item.y2006, item.y2007
						, item.y2008) );
				});
			}else{
				info.push( new Array(gubun, '01월', '02월', '03월', '04월', '05월', '06월', 
						'07월', '08월', '09월', '10월', '11월', '12월') );
				$.each(data, function(idx, item) {
					info.push( new Array( item.department_name, item.m01, item.m02, item.m03
							, item.m04, item.m05, item.m06, item.m07, item.m08, item.m09, item.m10
							, item.m11, item.m12));
				});
			}
			top3_chart(info, gubun);
		}, 
		error: function(req) {
			alert(req.status);
		}
	});

}

function top3_chart(info, gubun) {
	var chart = c3.generate({
		bindto: '#chart',
		data: {
			columns: info,
			x: gubun,
			type: gubun=='년' ? 'bar' : 'line',
			labels: true
		},
		axis: { x: {type: 'category' },
				y: {label: { text: 'TOP3 부서별 채용인원수', 
							position : 'outer-top' } }
		},
		padding: { bottom:30 },
		size: { width:1000, height:400 },
		legend: { item: { tile: {width: 15, height: 15 } }, padding: 50 },
		grid: { y: {show:true } }
	});
	$('.c3-legend-item').css('font-size', '16px');
	if( gubun=='월' ) $('.c3-line').css('stroke-width', '3px');
}


function basic_type(gubun) {
	
	$.ajax({
		url: 'visual/recruit/'+ $('input[name=gubun]:checked').val(),
		success: function( data ) {
			console.log( data );
			var info = [];
			info.name = [ gubun ];
			info.cnt = [ '채용인원수' ];
			$(data).each(function(idx, item){
				info.name.push( item.recruit );
				info.cnt.push( item.count );
			});
			info = [ info.name, info.cnt ];
			basic_chart( info, gubun );
		},
		error: function(req) {
			alert( req.status );
		}
	});
}

function basic_chart( info, gubun ) {
	var chart = c3.generate({
		bindto : '#chart',
		data : {
			columns : info,
			x : gubun,
			type : 'bar',
			labels: true
		},
		axis: {
			x : { type: 'category' },
			y : { label: {text: info[1][0],
					position: 'outer-top'} }
		},
		bar: {width:30},
		grid: { y: {show:true} },
		legend:{hide: true},
		size: {height:400, width:1000}
	});
}
</script>
</head>
<body>
<h3>채용인원수</h3>
<ul id="kind">
	<li><label><input type="checkbox" id="top3"/>TOP3 부서</label></li>
	<li><label><input type="radio" name="gubun" id="year" value="year" checked="checked"/>년도별</label></li>
	<li><label><input type="radio" name="gubun" id="month" value="month"/>월별</label></li>
</ul>

<div id="chart" style="padding-top: 20px"></div>
</body>
</html>