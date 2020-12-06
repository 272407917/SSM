<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>


    <jsp:include page="/WEB-INF/commons/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="pagination/pagination.css"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="pagination/jquery.pagination.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>


    <script type="text/javascript">
        $(function(){
            // 初始化pagination插件
            initPagination();
            $("*.btn.btn-danger.btn-xs").click(function () {
                if (window.confirm("确定是否删除！")) {
                    window.location.href=this.href;
                }
                return false;
            });

            $("#reset").click(function () {
                document.getElementById("keyWord").value=null;
                return false;
            })
        });

        function initPagination(){
            // 记录总条数
            var maxentries = ${requestScope.adminPageInfo.total};
            $("#Pagination").pagination(maxentries,{
                num_edge_entries: 3,                    //边缘页数
                num_display_entries: 5,                 //主体页数
                callback: pageselectCallback,
                items_per_page:10 ,                       //每页显示10项
                current_page: ${requestScope.adminPageInfo.pageNum} - 1, // 当前页数
                prev_text: "上一页",
                next_text: "下一页"
            })
        }

        // 此函数是在点击分页导航按钮之后被调用的，在这个函数中，可以发送请求
        function pageselectCallback(pageNum){
            // pageNum表示点击的页码数  注意： pageNum 从0开始的
            window.location.href = "user/to/user.html?pageNum="+(pageNum+1)+"&keyWord=${param.keyWord}"
            // 取消按钮的默认行为
            return false;
        }
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
                    <form action="user/to/user.html" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keyWord" class="form-control has-success" name="keyWord" value="${param.keyWord}" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="reset" type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 清空</button>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <a href="to/admin-add.html" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <c:if test="${empty requestScope.adminPageInfo.list}">
                            很抱歉，没有查询到相关信息！
                        </c:if>
                        <c:if test="${!empty requestScope.adminPageInfo.list}">
                            <table class="table  table-bordered">
                                <thead>
                                <tr>
                                    <th width="30">#</th>
                                    <th width="30"><input type="checkbox"></th>
                                    <th>账号</th>
                                    <th>名称</th>
                                    <th>邮箱地址</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.adminPageInfo.list}" var="admin" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/to/assignRole/${admin.id}/${requestScope.adminPageInfo.pageNum}/${param.keyWord}.html" type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                            <a id="editOne" href="user/to/admin-edit/${admin.id}/${requestScope.adminPageInfo.pageNum}/${param.keyWord}.html"  type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                            <a id="deleteOne"  href="user/do/delete/${admin.id}/${requestScope.adminPageInfo.pageNum}/${param.keyWord}.html" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="6" align="center">
                                            <%--<ul class="pagination">
                                            <c:if test="${requestScope.adminPageInfo.hasPreviousPage}">
                                            <li><a href="user/to/user.html?pageNum=${requestScope.adminPageInfo.prePage}&keyWord=${param.keyWord}">上一页</a></li>
                                            </c:if>
                                            <c:if test="${!requestScope.adminPageInfo.hasPreviousPage}">
                                            <li class="disabled"><a href="javaScript:return false;">上一页</a></li>
                                            </c:if>

                                            <c:forEach items="${requestScope.adminPageInfo.navigatepageNums}" var="num">
                                            <c:if test="${requestScope.adminPageInfo.pageNum == num}">
                                            <li class="active"><a href="javaScript:return false;">${num} <span class="sr-only">(current)</span></a></li>
                                            </c:if>
                                            <c:if test="${requestScope.adminPageInfo.pageNum != num}">
                                            <li><a href="user/to/user.html?pageNum=${num}&keyWord=${param.keyWord}">${num}</a></li>
                                            </c:if>
                                            </c:forEach>

                                            <c:if test="${requestScope.adminPageInfo.hasNextPage}">
                                            <li><a href="user/to/user.html?pageNum=${requestScope.adminPageInfo.nextPage}&keyWord=${param.keyWord}">下一页</a></li>
                                            </c:if>
                                            <c:if test="${!requestScope.adminPageInfo.hasNextPage}">
                                            <li class="disabled"><a href="javaScript:return false;">下一页</a></li>
                                            </c:if>
                                            </ul>--%>
                                        <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
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

