<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>한울 IoT과정 : ${iot_title} </title>
<link rel="icon" type="image/x-icon" href="img/hanul.ico">
<link rel="stylesheet" 
	href="css/common.css?v=<%=new java.util.Date().getTime()%>">
<script type="text/javascript"
src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>