<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>

    <jsp:include page="/WEB-INF/commons/head.jsp"></jsp:include>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="pagination/pagination.css"/>
    <script type="text/javascript" src="pagination/jquery.pagination.js" ></script>
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <link rel="stylesheet" href="ztree/zTreeStyle.css">
    <link rel="stylesheet" href="css/main.css">

    <script type="text/javascript" src="js/my-role.js"></script>
    <script type="text/javascript">
        //页面加载完毕之后加载函数中内容
        $(function () {
            //layer.msg('请求发送失败，请联系技术人员！', {time:2000, icon:2, shift:6});
            //生成页面
            //1、设置pageNum，pageSize，keyWord存入window中，在其他的页面上可以拿到，并且可以被覆盖
            window.pageNum = 1;
            window.pageSize=10;
            window.keyWord="";
            //2、调用my-role.js中的页面生成函数
            generatorPage();

            //查询功能
            $("#queryBtn").click(function () {
                //1、获取查询条件keyWord
                var keyWord = $("#keyWord").val();
                window.keyWord=keyWord;
                generatorPage();
            });
            //清空查询框
            $("#reset").click(function () {
                var keyWord=$("#keyWord").val(null)
            });


            //角色信息录入
            //1、点击新增打开模态框
            $("#roleAdd").click(function () {
                $("#roleModalAdd").modal("show");
            });
            //2、点击保存将数据插入到数据库中
            $("#saveRole").click(function () {
                //中间有空格，表示#roleModalAdd后代元素中name=roleName的值
                var roleName = $("#roleModalAdd [name=roleName]").val();
                $.ajax({
                    "url"        :        "role/do/addRole.json",
                    "type"       :        "post",
                    "data"       :        {
                        "roleName" : roleName
                    },
                    "dataType"   :        "json",
                    "success"    :function (response) {
                        if (response.operationResult == "FAILED"){
                            layer.msg(response.operationMessage,{time:2000, icon:2, shift:6});
                            return;
                        };
                        //3、新增成功后，清空模态框中roleName，并隐藏模态框
                        $("#roleModalAdd [name=roleName]").val(null);
                        $("#roleModalAdd").modal("hide");
                        //4、新增内容在最后一页，要跳转到最后一页
                        window.pageNum = 2147483647;
                        generatorPage();
                    },
                    "error"      :function (response) {
                        layer.msg("新增请求发送失败，请联系管理人员！",{time:2000, icon:2, shift:6});
                    }
                });

            });


            //修改功能
            /*
            * 修改按钮绑定点击函数不可行，第一个加载页面可以使用，但是点击换页后绑定的点击函数失效
            * 原因是点击函数是在页面加载完毕后绑定到按键上的，如果使用.class绑定，它只会绑定第一页的十个按键，
            * 所以我要通过到页面不会变的roleTbody来找到修改按钮，然后将点击事件绑定上去
            * */
            //1、绑定修改按钮，打开修改模态框
            /*
            * click:点击事件
            * editBtn:roleTbody中
            * */
            $("#roleTbody").on("click",".editBtn",function () {
                $("#roleModalEdit").modal("show");
            /**
             * 2、拿到roleName进行数据回显,roleName是放在单元格中的,通过父辈元素定位到单元格中的数据
             * prev():同辈元素中的上一级元素，就是前一个元素
             * roleTbody的父辈元素就是存放按键的单元格，它的上一级同辈元素就是前一个单元格
            */
                var roleName=$(this).parent().prev().text();
                $("#roleName").val(roleName);
                //将查询到的roleId放进editBtn中的id中，这样点击修改按钮就能获取到id，然后将roleId放进window中
                window.roleId = this.id;
            })

            //3、点击修改按钮进行数据修改
                $("#editRole").click(function () {
                    var roleName = $("#roleModalEdit [id=roleName]").val();
                    var roleId = window.roleId;
                    $.ajax({
                        "url"         :       "role/do/editRole.json",
                        "data"        :       {
                            "roleName":        roleName,
                            "id"  :        roleId
                        },
                        "type"        :        "post",
                        "dataType"    :        "json",
                        "success"     :        function (response) {
                            if (response.operationResult == "FAILED"){
                                layer.msg(response.operationMessage,{time:2000, icon:2, shift:6})
                            } ;

                            //请求发送成功，并修改成功
                            //3.1、关闭模态框
                            $("#roleModalEdit").modal("hide");
                            //3.2、清空修改框中roleName
                            $("#roleModalEdit [id=roleName]").val(null);
                            //3.3、重新刷新加载显示页面，也为是ajax请求，显示页面不会只会重新刷新而不会返回第一页
                            generatorPage();

                        },
                        "error"       :        function (response) {
                            layer.msg("修改请求发送失败，请联系管理人员！",{time:2000, icon:2, shift:6});
                        }
                    })
                });


            //删除功能，批量删除和单条删除
            //1、全选
            //页面刷新初始化全选框为false
            $("#parentCkbox").prop("checked",false);
            $("#parentCkbox").click(function () {
               var parenChecked = this.checked;
               var childCkboxList = $(".childCkbox");
               $.each(childCkboxList,function (index,obj) {
                   obj.checked=parenChecked;
               })
            });
            //2、单选
            /*
            * $("#parentCkbox")[0]将$("#parentCkbox")jQuery对象转换成DOM对象
            * jquery对象设置勾选框：$("#parentCkbox").prop("checked",false)
            * */
            $("#roleTbody").on("click",".childCkbox",function () {
                var childCkboxList = $(".childCkbox");
                //初始化设置全选框被选中，下面循环判断，只要有childCkbox没被选中就将全选框设置为false
                $("#parentCkbox")[0].checked = true;
                $.each(childCkboxList,function (index, obj) {
                    if (!obj.checked) {
                        $("#parentCkbox")[0].checked = false;
                    }
                })
            });

            //3、删除实现
            //3.1、点击删除按钮模态框出现,并显示删除信息
            $("#deleteMainBtn").click(function () {
                //创建一个数组，存放取出所有被勾选的信息
                var roleNameList = [];
                var childCkboxList = $(".childCkbox");
                $.each(childCkboxList,function (index,obj) {
                    if (obj.checked) {
                        roleNameList.push(obj.name);
                    }
                });

                //判断是否有被选中的信息
                if (roleNameList.length <= 0) {
                    layer.msg("请勾选需要删除的信息！",{time:2000,icon:0,shift:6});
                    return
                }
                //将要被删除的信息添加到模态框中显示
                $("#deleteModalDiv").empty();
                var str = "";
                for (var i = 0; i < roleNameList.length; i++) {
                    str += "<div style=\"margin:0 auto;text-align: center\">"+roleNameList[i]+"</div>"
                };
                $("#deleteModalDiv").append(str);
                $("#roleModalDelete").modal("show");

            });

            //3.2、模态框删除按钮点击事件绑定
            $("#deleteRole").click(function () {
                //获取到所有被勾选的id
                var roleIdList = [];
                var childCkboxList = $(".childCkbox");
                $.each(childCkboxList,function (index, obj) {
                    if (obj.checked) {
                        roleIdList.push(obj.id);
                    }
                });

                deleteRole(roleIdList);
                $("#parentCkbox").prop("checked",false);
            });


            //单条删除
            $("#roleTbody").on("click",".deleteBtn",function () {
                //拿到roleId、roleName
                var roleId = this.id;
                var roleName = $(this).parent().prev().text();
                //将roleId转换成一个数组
                var ids = [roleId];
                //使用layer询问框
                layer.confirm("是否删除"+roleName+"?",  {icon: 3, title:'提示'}, function(cindex){
                    deleteRole(ids);
                    layer.close(cindex);
                }, function(cindex){
                    layer.close(cindex);
                });
            })

            //权限设置
            $("#assignRoleBtn").click(function () {
                //取出勾选的auth_id
                var treeObj = $.fn.zTree.getZTreeObj("authTree");
                var nodes = treeObj.getCheckedNodes(true);
                console.log(nodes)
                var authIds = [];
                for (var i = 0; i < nodes.length; i++) {
                    authIds.push(nodes[i].id);
                }
                console.log(authIds);

                var data = {
                    "authIds"   :   authIds,
                    "roleId"     :   [window.id]    //统一发送数据的格式，接收时好接收
                };
                var strJSON = JSON.stringify(data);
                $.ajax({
                    "url"   :    "assign/do/editAuth.json",
                    "data"  :    strJSON,    //角色id，权限id
                    "contentType" :  "application/json;charset=UTF-8",
                    "dataType":  "json",
                    "type"    :   "post",
                    "success" :   function (response) {
                        if(response.operationResult == "FAILED"){
                            layer.msg(response.operationMessage,{time:2000, icon:2, shift:6});
                            return;
                        };
                        layer.msg("修改成功！",{time:2000, icon:1, shift:2});
                        //关闭模态框
                        $("#assignRoleAuthModal").modal("hide")
                    },
                    "error"   :   function (response) {
                        layer.msg("修改请求发送失败，请联系管理人员！",{time:2000, icon:2, shift:6});
                    }
                });

                return false;
            })

        });

        //实现删除函数（多选和单删共用）
        function deleteRole(roleIdList) {
            //将id数组转换成json数据
            var data = JSON.stringify(roleIdList);
            $.ajax({
                "url"         :        "role/do/deleteRole.json",
                "data"        :        data,
                "type"        :        "post",
                "dataType"    :        "json",
                "contentType" :        "application/json",
                "success"     :        function (response) {
                    if (response.operationResult == "FAILED"){
                        layer.msg(response.operationMessage,{time:2000, icon:2, shift:6});
                        return
                    } ;

                    //请求发送成功，并修改成功
                    //关闭模态框
                    $("#roleModalDelete").modal("hide");
                    //重新刷新加载显示页面，也为是ajax请求，显示页面不会只会重新刷新而不会返回第一页
                    generatorPage();
                },
                "error"       :        function (response) {
                    layer.msg("删除请求发送失败，请联系管理人员！",{time:2000, icon:2, shift:6});
                }
            })
        };
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

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/commons/sidebar.jsp"></jsp:include>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keyWord" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        <button id="reset" type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 清空</button>
                    </form>
                    <button id="deleteMainBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="roleAdd" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="parentCkbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleTbody">

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
<jsp:include page="role-modal-add.jsp"></jsp:include>
<jsp:include page="role-modal-edit.jsp"></jsp:include>
<jsp:include page="role-modal-delete.jsp"></jsp:include>
<jsp:include page="modal-role-auth.jsp"></jsp:include>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script src="layer/layer.js"></script>
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
