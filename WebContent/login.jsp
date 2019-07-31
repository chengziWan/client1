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
<input type="hidden" id="service" value="http://cas.example.org:8091/client1/index.jsp"/>

<input type="button" id="button2" value="登录--未实现单点登录" onclick="click2()"/><br><br>
<input type="button" id="button1" value="授权登录" onclick="click1()"/><br><br>
<input type="button" id="button1" value="授权登录" onclick="click2()"/><br><br>

</form>
</body>
<script type="text/javascript">
//客户端登录
function click2() {
	var username = $("#username").val();
	var password = $("#password").val();
	var service = $("#service").val();
	var datas = {'username':username,'password':password,'service':service};
	$.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: "login/login",
        data:datas,
        success: function (msg) {
            console.info("请求成功");
            console.info(msg);
            if (msg.status == 1) {
            	
                // 设置 302 重定向跳转
                window.location.href = msg.data;
            }else{
                // 显示登录页面
                $("#loginDiv").show("slow");
            }

        },
        error: function (msg) {
            console.info("请求Error");
            console.info(msg);
        }
    });
}
//oauth2接入
function click1(){
	window.location.href = "https://cas.example.org:8443/cas5.3.5/oauth2.0/authorize?response_type=code&client_id=100001&redirect_uri=http://cas.example.org:8091/client1";
}
//oauth2接入
function click2(){
	window.location.href = "https://cas.example.org:8443/cas5.3.5/oauth2.0/authorize?response_type=code&client_id=1004&redirect_uri=http://client1.com:8091/client1";
}
//未使用
function click222() {
	$.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: "login/redirectOauth2Login",
        //data:datas,
        success: function(msg) {
            console.info("请求成功");
        },
        error: function(msg) {
            console.info("请求Error");
        }
    });
}
</script>
</html>
