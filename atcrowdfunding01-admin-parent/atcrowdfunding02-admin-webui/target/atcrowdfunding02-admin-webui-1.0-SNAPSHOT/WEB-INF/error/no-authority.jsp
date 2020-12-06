<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/7/11
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<p style="color: red"><h3>${requestScope.message}</h3></p>
<button id="back" onclick="javaScript:history.back(-1)">返回</button>
</body>
</html>
