<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <jsp:include page="/WEB-INF/commons/head.jsp"></jsp:include>
    <link rel="stylesheet" href="css/main.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        .tree-closed {
            height: 40px;
        }

        .tree-expanded {
            height: auto;
        }
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/commons/navbar.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/commons/sidebar.jsp"></jsp:include>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">数据列表</a></li>
            <li class="active">修改</li>
        </ol>
        <div class="panel panel-default">
            <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
            <div class="panel-body">
                <form action="user/do/adminEdit.html?pageNum=${requestScope.pageNum}&keyWord=${requestScope.keyWord}" method="post" role="form">
                    <c:if test="${empty requestScope.exception.message}">
                    <input type="hidden" name="adminId" value="${requestScope.admin.id}">
                    <div class="form-group">
                        <label for="exampleInputPassword1">登陆账号&nbsp;&nbsp;&nbsp; <span style="color: red">${requestScope.exception.message}</span></label>
                        <input name="loginAcct" value="${requestScope.admin.loginAcct}" type="text" class="form-control" id="exampleInputPassword1" value="test">
                        <input type="hidden" name="originalLoginAcct" value="${requestScope.admin.loginAcct}">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">用户名称</label>
                        <input name="userName" value="${requestScope.admin.userName}" type="text" class="form-control" id="exampleInputPassword2" value="测试用户">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">邮箱地址</label>
                        <input name="email" value="${requestScope.admin.email}" type="email" class="form-control" id="exampleInputEmail1" value="xxxx@xxxx.com">
                        <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                    </div>
                    </c:if>

                    <c:if test="${!empty requestScope.exception.message}">
                        <input type="hidden" name="adminId" value="${param.adminId}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆账号&nbsp;&nbsp;&nbsp; <span style="color: red">${requestScope.exception.message}</span></label>
                            <input name="loginAcct" value="${param.loginAcct}" type="text" class="form-control" id="exampleInputPassword1" value="test">
                            <input type="hidden" name="originalLoginAcct" value="${param.originalLoginAcct}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户名称</label>
                            <input name="userName" value="${param.userName}" type="text" class="form-control" id="exampleInputPassword2" value="测试用户">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input name="email" value="${param.email}" type="email" class="form-control" id="exampleInputEmail1" value="xxxx@xxxx.com">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                    <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                </form>
            </div>
        </div>
    </div>

</div>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
</script>
</body>
</html>
