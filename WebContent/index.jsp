<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CAS Example Java Web App</title>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>

</head>
<body>

<h1>CAS Example Java Web App</h1>
<p>A sample web application that exercises the CAS protocol features via the Java CAS Client.</p>
<hr>

<p><b>Authenticated User Id:</b> <a href="https://cas.example.org:8443/cas5.3.5/logout?service=http://127.0.0.1:8080/client1/login.jsp" title="Click here to log out">Click here to log out <%= request.getRemoteUser() %>
</a></p>

<%	
	 
    if (request.getUserPrincipal() != null) {
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();

        final Map attributes = principal.getAttributes();

        if (attributes != null) {
            Iterator attributeNames = attributes.keySet().iterator();
            out.println("<b>Attributes:</b>");

            if (attributeNames.hasNext()) {
                out.println("<hr><table border='3pt' width='100%'>");
                out.println("<th colspan='2'>Attributes</th>");
                out.println("<tr><td><b>Key</b></td><td><b>Value</b></td></tr>");

                for (; attributeNames.hasNext(); ) {
                    out.println("<tr><td>");
                    String attributeName = (String) attributeNames.next();
                    out.println(attributeName);
                    out.println("</td><td>");
                    final Object attributeValue = attributes.get(attributeName);

                    if (attributeValue instanceof List) {
                        final List values = (List) attributeValue;
                        out.println("<strong>Multi-valued attribute: " + values.size() + "</strong>");
                        out.println("<ul>");
                        for (Object value : values) {
                            out.println("<li>" + value + "</li>");
                        }
                        out.println("</ul>");
                    } else {
                        out.println(attributeValue);
                    }
                    out.println("</td></tr>");
                }
                out.println("</table>");
            } else {
                out.print("No attributes are supplied by the CAS server.</p>");
            }
        } else {
            out.println("<pre>The attribute map is empty. Review your CAS filter configurations.</pre>");
        }
    } else {
        out.println("<pre>The user principal is empty from the request object. Review the wrapper filter configuration.</pre>");
    }
	out.print("code="+request.getParameter("code")+".</p>");
	request.getContextPath();
	
%>
<form action="/">
code:<input type="text" id="code" value="<%=request.getParameter("code")%>" size="80px" border="0px"/>
accessToken:<input type="text" id="token"  size="80px" border="0px"/><br><br>
<input type="text" id="profile"  size="200px"/><br><br>
<input type="button" id="button2" value="2.获取accesstoken" onclick="click2()"/><br><br>
<input type="button" id="button1" value="3.获取用户信息" onclick="click1()"/><br><br>
</form>
</body>
<script type="text/javascript">
function click2() {
	var code = $("#code").val();
	var datas = {'grant_type':'authorization_code','client_id':'100001','client_secret':'100001abcdeft',
				 'redirect_uri':'http://127.0.0.1:8080/client1','code':code};
	$.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: "login/getAccessToken",
        data:datas,
        success: function (msg) {
            console.info("请求成功");
            console.info(msg);
            $("#token").val(msg);

        },
        error: function (XMLHttpRequest,textStatus,errorThrown) {
            console.info("请求Error");
            console.info(XMLHttpRequest.status);
        }
    });
}
function click1() {
	//var datas = $("#token").val();
	var datas = {"access_token":$("#token").val()};
	$.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: "login/getProfile",
        data:datas,
        success: function (msg) {
            console.info("请求成功");
            console.info(msg);
            $("#profile").val(msg);

        },
        error: function (XMLHttpRequest,textStatus,errorThrown) {
            console.info("请求Error");
            console.info(XMLHttpRequest.status);
        }
    });
}

function click3() {
	var code = $("#code").val();
	$.ajax({
        method: "GET",
        url: "https://cas.example.org:8443/cas5.3.5/oauth2.0/accessToken?grant_type=authorization_code&client_id=100001&client_secret=100001abcdeft&redirect_uri=http://127.0.0.1:8080/client1&code="+code,
        //data: {'service': service},
        xhrFields: {
            withCredentials: true
        },
        crossDomain:true,
        dataType: "jsonp",
        jsonp: "callback",
        // cache: false,
        success: function (result) {
            console.info("请求成功");
            console.info(result.access_token);
           
        },
        error: function (result) {
            console.info("请求失败");
            console.info(result);
        }
    });
}

</script>
</html>
