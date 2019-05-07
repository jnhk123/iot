<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.0/c3.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.9.2/d3.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.0/c3.min.css">
<style type="text/css">
.c3-axis{ font-size: 1.5em; }
.c3-axis-x {font-weight: bold;}
.c3-chart { font-size: 1.5em; font-weight: bold; }
.legend-item {width: 15px; height: 15px; display: inline-block;
				margin-top: 2px; margin-right: 2px;}
#chart-legend ul { display: inline-block; }
#chart-legend li {display: block; height: 10px; float: left;}
#chart-legend li * { vertical-align: middle; }
#chart-legend li:not(:first-child) { margin-left: 30px; }
</style>
<script type="text/javascript">
	$(function() {
		$('.legend-item').each(function(idx, item) {
			$(this).css('background-color', colors[idx]);
		});
		make_chart();
	});
	function make_chart() {
		//1.기본형태로 그리기
		/* basic_type(); */
		//2.파이형태
// 		pie_type();
		//3.막대형태
		bar_type();
	}
	var colors = [ '#6394ff', '#ff7f0e', '#2ca02c', '#d62728', '#9162bc', '#1200d6',
		'#7568ff', '#7f3c01', '#005100', '#ff9b9c', '#6b6982', '#6b02cc', '#124533'];
	//사원수에 따라 색상 지정
	function setColor( v ) {
		if( v <= 10 ) return 0;
		else if( v <= 20 ) return 1;
		else if( v <= 30 ) return 2;
		else if( v <= 40 ) return 3;
		else if( v <= 50 ) return 4;
		else return 5;
	}
	function bar_type() {
		var info = [];
		info.name = ['부서명'];
		info.cnt = ['부서원수'];
		<c:forEach items="${info}" var="vo">
			info.name.push( '${vo.department_name}' );
			info.cnt.push( ${vo.count} );
		</c:forEach>
		info = [ info.name, info.cnt ];
// 		부서명 영업부 총무부
// 		부서원수 10 5
		var chart = c3.generate({
				bindto : '#chart',
				size: { width: 1000, height: 400 },
				data: {
					//columns: [ ['부서원수', 10,20,15,35] ]
// 					columns: [info]
					columns: info,
					x: '부서명',
					type: 'bar',
					labels: true,
					color: function(color, d) {
// 						return colors[d.index];
						return colors[ setColor(d.value) ];
					}
			},
			bar: { width : 30 },
			grid: { y:{ show: true } },
			axis:{
				x: { type: 'category', 
					tick: { rotate: -60 }	
					},
				y: {
					label : {
						text: info[1][0],
						position: 'outer-middle' //outer-top outer-bottom
					}
				}
			},
			legend: { hide:true }
		});
	}
	
	function pie_type() {
		var info = [];
		<c:forEach items="${info}" var="vo">
			info.push( new Array('${vo.department_name}', ${vo.count}) );
		</c:forEach>
		
		var chart = c3.generate({
			bindto: '#chart',
			size : { width: 1000, height: 400},
			data : {
				columns : info, 
// 				type: 'pie'
				type: 'donut'
			}
		});
	}
	
	function basic_type() {
		/* 
		var info = ['부서원수'];
		<c:forEach items="${info}" var="vo">
			info.push( ${vo.count} );
		</c:forEach>
		 */
		var info = [];
		info.name = ['부서명'];
		info.cnt = ['부서원수'];
		<c:forEach items="${info}" var="vo">
			info.name.push( '${vo.department_name}' );
			info.cnt.push( ${vo.count} );
		</c:forEach>
		info = [ info.name, info.cnt ];
		
		var chart = c3.generate({
				bindto : '#chart',
				size: { width: 1000, height: 400 },
				data: {
					//columns: [ ['부서원수', 10,20,15,35] ]
// 					columns: [info]
					columns: info,
					x: '부서명'
			},
			axis:{
				x: { type: 'category' }
			}
		});
	}
$(document).on('click','#graph', function() {
	$('#graph').text( $('#graph').text()=='도넛그래프' ? '막대그래프' : '도넛그래프' );
	if( $('#graph').text()=='도넛그래프' ? true : false ){
		bar_type();
		$('#chart-legend').css('display','block');
	}
	else{
		pie_type();
		$('#chart-legend').css('display','none');
	}
});
</script>
</head>
<body>
	<h3>부서별 사원수
		<a id="graph" class="small-btn-fill" style="position: absolute; margin-left: 15px;">도넛그래프</a>
	</h3>
	<div id="chart" style="padding-top: 20px;"></div>
	<div id="chart-legend">
		<ul>
			<li><span class="legend-item"></span><span>0~10명</span></li>
			<li><span class="legend-item"></span><span>11~20명</span></li>
			<li><span class="legend-item"></span><span>21~30명</span></li>
			<li><span class="legend-item"></span><span>31~40명</span></li>
			<li><span class="legend-item"></span><span>41~50명</span></li>
			<li><span class="legend-item"></span><span>50명 초과</span></li>
		</ul>
	</div>
</body>
</html>