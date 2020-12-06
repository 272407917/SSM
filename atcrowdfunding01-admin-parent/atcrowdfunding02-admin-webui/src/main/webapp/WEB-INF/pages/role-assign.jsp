<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <jsp:include page="/WEB-INF/commons/head.jsp"></jsp:include>
    <script src="jquery/jquery-2.1.1.min.js"></script>
    <link rel="stylesheet" href="css/main.css">
    <script type="text/javascript">

        $(function () {

            //将未分配角色添加到右边已分配角色框中
            $("#leftToRight").click(function () {
                $("select:eq(0) option:selected").appendTo($("select:eq(1)"))
            });
            //将选中的已分配角色添加到左边已未配角色框中
            $("#rightToLeft").click(function () {
                $("select:eq(1) option:selected").appendTo($("select:eq(0)"))
            });

            //在分配完后确定提交，需要将右边框中角色信息全都选中,因为from提交时，是提交了选中的
            $("#assignBtn").click(function () {
                $("select:eq(1) option").attr("selected","selected")
            })

        })

    </script>


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
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <ol class="breadcrumb">
        <li><a href="#">首页</a></li>
        <li><a href="#">数据列表</a></li>
        <li class="active">分配角色</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-body">
            <form action="assign/do/assignRole.html" method="post" role="form" class="form-inline">
                <div class="form-group">
                    <label >未分配角色列表</label><br>
                    <select class="form-control" multiple="" size="10" style="width:200px;overflow-y:auto;">
                       <c:forEach items="${requestScope.unAssignRoleList}" var="role">
                           <option value="${role.id}">${role.roleName}</option>
                       </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <ul>
                        <li id="leftToRight" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                        <br>
                        <li id="rightToLeft" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                    </ul>
                </div>
                <div class="form-group" style="margin-left:40px;">
                    <input name="adminId" type="hidden" value="${requestScope.adminId}">
                    <input name="pageNum" type="hidden" value="${requestScope.pageNum}">
                    <input name="keyWord" type="hidden" value="${requestScope.keyWord}">
                    <label>已分配角色列表</label><br>
                    <select name="assignRoleIds" class="form-control" multiple="" size="10" style="width:200px;overflow-y:auto;">
                        <c:forEach items="${requestScope.assignedList}" var="role">
                            <option value="${role.id}">${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <button id="assignBtn" type="submit" class="btn btn-success">分配许可</button>
            </form>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/commons/sidebar.jsp"></jsp:include>
    </div>

</div>
</div>

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
