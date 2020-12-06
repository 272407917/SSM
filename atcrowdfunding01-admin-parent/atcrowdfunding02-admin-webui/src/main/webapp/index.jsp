<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/6/29
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--
    base标签的作用： 在页面中所有的相对路径对相对于base标签中的路径进行定位
                     pageContext.request.contextPath相对路径，
    --%>
    <%--<base href="http://localhost:8080/crowd/">--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">

    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $("#testAjax").click(function () {
                $.ajax({
                    url     :     this.href,
                    data    :     {"a":1},
                    type    :     "post",
                    success :     function (result) {
                        alert(result)
                    },
                    fail    :     function (result) {
                        alert(result)
                    },
                });
                return false;
            });

            $("#testJson1").click(function () {
                $.ajax({
                    url       :       this.href,
                    data      :       {"ids":[0,1,2]},
                    type      :       "post",
                    success   :       function (result) {
                        alert(result)
                    },
                    fail      :       function (result) {
                        alert(result)
                    }
                });
                return false;
            });

            var ids=[0,1,2];
            var idsJson = JSON.stringify(ids)

            $("#testJson2").click(function () {
                $.ajax({
                    url         :      this.href,
                    data        :      idsJson,
                    type        :      "post",
                    contentType :      "application/json;charset=UTF-8",
                    success     :      function (result) {
                        alert(result)
                    },
                    fail        :      function (result) {
                        alert(result)
                    }
                });
                return false;
            });

            $("#testjudgeRequestType").click(function () {
                $.ajax({
                    url     :     this.href,
                    data    :     {"a":1},
                    type    :     "post",
                    success :     function (result) {
                        alert(result)
                    },
                    fail    :     function (result) {
                        alert(result)
                    },
                });
                return false;
            });
        })
    </script>


</head>
<body>
    <!--在Tomcat中配置了crowd，所以每次发送请求都需要加上/crowd
        以前是使用pageContext.request.contextPath，
        但是多个请求的话都需要手动的加很麻烦
        使用base标签解决，要引入jsp-api依赖
    -->
    协议名：${pageContext.request.scheme}
    <br>
    主机名：${pageContext.request.serverName}
    <br>
    端口号：${pageContext.request.serverPort}
    <br>
    项目名：${pageContext.request.contextPath}
    <br>

    <a href="admin/test.html">test</a>
    <br>
    <a id="testAjax" href="admin/testAjax.json">testAjax</a>
    <br>
    <a id="testJson1" href="admin/testJson1.json">testJson1</a>
    <br>
    <a id="testJson2" href="admin/textJson2.json">textJson2</a>
    <br>
    <a id="testjudgeRequestType" href="admin/testjudgeRequestType.json">testjudgeRequestType</a>
    <br>
    <a href="admin/testCrowdExceptionResolver.html">testCrowdExceptionResolver</a>
</body>
</html>
