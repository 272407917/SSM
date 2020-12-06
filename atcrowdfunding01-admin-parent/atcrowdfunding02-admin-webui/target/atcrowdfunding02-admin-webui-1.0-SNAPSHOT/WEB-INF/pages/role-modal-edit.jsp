<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/7/4
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="roleModalEdit" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改角色信息</h4>
            </div>
            <div class="modal-body">
                <form method="post" role="form">
                    <input type="hidden" id="roleId">
                    <div class="form-group">
                        <input id="roleName" type="text" class="form-control" placeholder="请输入角色名称">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="editRole" type="button" class="btn btn-primary">修改</button>
                    </div>
                </form>
            </div>

        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->