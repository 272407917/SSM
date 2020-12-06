<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <jsp:include page="/WEB-INF/commons/head.jsp"></jsp:include>
    <script src="jquery/jquery-2.1.1.min.js"></script>
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="ztree/zTreeStyle.css">
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript" src="js/my-menu.js"></script>
    <script type="text/javascript">

        //页面加载完毕后加载,等价于$(function(){})
        $(document).ready(function() {
            generatorMenu();


            //添加模态框
            $("#menuSaveBtn").click(function () {
                //1、取出新增信息
                var name = $("#menuAddModal [name = name]").val();
                var url = $("#menuAddModal [name = url]").val();
                var ico = $("#menuAddModal [name = icon]:checked").val();

                $.ajax({
                    "url": "menu/do/add.json",
                    "type": "post",
                    "data": {
                        "name": name,
                        "url": url,
                        "ico": ico,
                        "pId": window.id
                    },
                    "dataType": "json",
                    "success": function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6});
                            return;
                        }
                        ;
                        //3、新增成功后，清空模态框中roleName，并隐藏模态框
                        var name = $("#menuAddModal [name = name]").val(null);
                        var url = $("#menuAddModal [name = url]").val(null);
                        var ico = $("#menuAddModal [name = icon]:checked").val(null);
                        $("#menuAddModal").modal("hide");
                        layer.msg("添加成功！", {time: 2000, icon: 1, shift: 2});
                        generatorMenu()
                    },
                    "error": function (response) {
                        layer.msg("新增请求发送失败，请联系管理人员！", {time: 2000, icon: 2, shift: 6});
                    }
                })
            });


            //删除模态框
            $("#confirmBtn").click(function () {
                $.ajax({
                    "url": "menu/do/delete.json",
                    "data": {
                        "id": window.id
                    },
                    "type": "post",
                    "dataType": "json",
                    "success": function (response) {
                        if (response.operationResult == "FAILED") {
                            layer.msg(response.operationMessage, {time: 2000, icon: 2, shift: 6})
                        }
                        ;

                        //请求发送成功，并修改成功
                        layer.msg("删除成功！", {time: 2000, icon: 1, shift: 2});
                        //3.1、关闭模态框
                        $("#menuConfirmModal").modal("hide");
                        //3.3、重新刷新加载显示页面，也为是ajax请求，显示页面不会只会重新刷新而不会返回第一页
                        generatorMenu();

                    },
                    "error": function (response) {
                        layer.msg("删除请求发送失败，请联系管理人员！", {time: 2000, icon: 2, shift: 6});
                    }
                })
            });


            //修改模态框
            $("#menuEditBtn").click(function () {

                var name = $("#menuEditModal [name = name]").val();
                var url = $("#menuEditModal [name = url]").val();
                var ico = $("#menuEditModal [name = icon]:checked").val();
                console.log(name)
                console.log(url)
                console.log(ico)

                $.ajax({
                    "url"         :       "menu/do/edit.json",
                    "data"        :       {
                        "id"      :    window.id,
                        "name"    :    name,
                        "url"     :    url,
                        "ico"     :    ico
                    },
                    "type"        :        "post",
                    "dataType"    :        "json",
                    "success"     :        function (response) {
                        if (response.operationResult == "FAILED"){
                            layer.msg(response.operationMessage,{time:2000, icon:2, shift:6});
                            return;
                        } ;
                        if (response.operationResult == "SUCCESS") {
                            //请求发送成功，并修改成功
                            layer.msg("修改菜单成功",{time:2000, icon:1, shift:4})
                            //3.1、关闭模态框
                            $("#menuEditModal").modal("hide");

                            //3.3、重新刷新加载显示页面，也为是ajax请求，显示页面不会只会重新刷新而不会返回第一页
                            generatorMenu();
                        }



                    },
                    "error"       :        function (response) {
                        layer.msg("修改请求发送失败，请联系管理人员！",{time:2000, icon:2, shift:6});
                    }
                })
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

    <div class="panel panel-default">
        <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
        <div class="panel-body">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
    </div>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/commons/sidebar.jsp"></jsp:include>
    </div>

</div>
</div>
<jsp:include page="modal-menu-add.jsp"></jsp:include>
<jsp:include page="modal-menu-confirm.jsp"></jsp:include>
<jsp:include page="modal-menu-edit.jsp"></jsp:include>
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
