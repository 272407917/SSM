<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020/7/4
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="roleModalAdd" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">角色信息录入</h4>
            </div>
            <div class="modal-body">
                <form action="user/do/addAdmin.html" method="post" role="form">
                    <div class="form-group">
                        <input name="roleName" value="" type="text" class="form-control" id="exampleInputPassword1" placeholder="请输入角色名称">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="saveRole" type="button" class="btn btn-primary">保存</button>
                    </div>
                </form>
            </div>

        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
