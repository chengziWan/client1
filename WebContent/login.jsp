<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>login page</title>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<h1>login page</h1>
<p></p>
<hr>
<form action="/">
<input type="text" id="username" /><br><br>
<input type="password" id="password" /><br><br><br><br>

<input type="button" id="button2" value="登录" onclick="click2()"/><br><br>
<input type="button" id="button1" value="授权登录" onclick="click1()"/><br><br>

</form>
</body>
<script type="text/javascript">
function click2() {
	//var code = $("#code").val();
	var datas = {'grant_type':'authorization_code','client_id':'100001','client_secret':'100001abcdeft',
				 'redirect_uri':'http://127.0.0.1:8080/client1','code':'1'};
	$.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: "login/hello",
        data:datas,
        success: function (msg) {
            console.info("请求成功");
            console.info(msg);

        },
        error: function (msg) {
            console.info("请求Error");
            console.info(msg);
        }
    });
}
function click1(){
	
	window.location.href = "https://cas.example.org:8443/cas5.3.5/oauth2.0/authorize?response_type=code&client_id=100001&redirect_uri=http://127.0.0.1:8080/client1";
}
</script>
</html>
