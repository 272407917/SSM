//用来生成role-list页面的函数，放在这里role-list引用更加方便
//生成页面的函数
function generatorPage() {
    //1、获取生成页面所需的数据，通过ajax请求取得数据
    var rolePageInfo = getRoleInfoByAjax();

    //2、将获取的数据放进role-list页面中
    fullTbody(rolePageInfo);
}

function getRoleInfoByAjax() {
    var ajaxResult = $.ajax({
        "url"    :    "role/get/role-list.json",
        "data"   :    {
            "pageNum"   :   window.pageNum,
            "pageSize"  :   window.pageSize,
            "keyWord"   :   window.keyWord,
        },
        "type"   :     "post",        //请求类型
        /*dataType="json"，如果服务器端返回的是一个json格式的字符串，Jquery会调用Json.prase()方法将其转换成JSON对象。
        另外如果服务器端返回的不是json格式的数据，就会直接进入error函数回调。
        dataType="text"，预计服务器端返回的数据就是纯文本字符串型，json格式数据还是会以json字符串的形式进入success函数回调
        */
        "dataType"  :  "json",      //后端返回数据类型

        /*默认为false，发送的是异步请求，因为后面的函数需要用到
        本ajax请求的响应数据，所以设置成false，进行同步请求，
        加载完之后才执行后面的，异步的话是没加载完就可以执行后面的*/
        "async"     :  false,
    });
    if (ajaxResult.status != 200){
        /*
       time属性： 指定弹窗的出现时间
       icon属性： 指定弹窗显示的图标
       shift属性： 指定弹窗的动作
        */
        layer.msg("请求发送失败，请联系技术人员！", {time:2000, icon:2, shift:6});
        return;
    }
    //请求发送成功，但是后台获取数据失败
    if (ajaxResult.responseJSON.operationResult == "FAILED") {
        layer.msg(ajaxResult.responseJSON.operationMessage, {time:2000, icon:2, shift:6});
        return;
    }
    return ajaxResult.responseJSON.queryData;

};

function fullTbody(rolePageInfo) {
    //1、清空tbody，因为如果不清空tbody那么每一次添加的内容就会追加到前一次的添加内容后面
    $("#roleTbody").empty();
    //2、将数据填充到tbody中
    var tbodyStr="";
    for (var i = 0;i<rolePageInfo.list.length;i++){
        var roleId = rolePageInfo.list[i].id;
        var roleName = rolePageInfo.list[i].roleName;
        tbodyStr=tbodyStr+
            "                            <tr>" +
            "                                <td>"+(i+1)+"</td>" +
            "                                <td><input class='childCkbox' id='"+roleId+"' name='"+roleName+"' type=\"checkbox\"></td>" +
            "                                <td>"+roleName+"</td>" +
            "                                <td>" +
            "                                    <button type=\"button\" id='"+roleId+"' onclick='showAssignAuthModal(this)' class=\"btn btn-success btn-xs\"><i class=\" glyphicon glyphicon-check\"></i></button>" +
            "                                    <button type=\"button\" id='"+roleId+"' class=\"btn btn-primary btn-xs editBtn\"><i class=\" glyphicon glyphicon-pencil\"></i></button>" +
            "                                    <button type=\"button\" id='"+roleId+"' class=\"btn btn-danger btn-xs deleteBtn\"><i class=\" glyphicon glyphicon-remove\"></i></button>" +
            "                                </td>" +
            "                            </tr>"

    };
    $("#roleTbody").append(tbodyStr);


    // 记录总条数
    var maxentries = rolePageInfo.total;
    console.log(maxentries)
    console.log(rolePageInfo.pageNum)
    $("#Pagination").pagination(maxentries,{
        num_edge_entries: 3,                    //边缘页数
        num_display_entries: 5,                 //主体页数
        callback: pageselectCallback,
        items_per_page:10 ,                       //每页显示10项
        current_page: rolePageInfo.pageNum - 1, // 当前页数
        prev_text: "上一页",
        next_text: "下一页"
    });

}

/*// pagination 回调函数   点击分页按钮时触发的
function pageSelectCallback(pageNum,jquery){
    // 切换页码
    window.pageNum = pageNum + 1;
    generatorPage();
    // 取消分页的默认行为
    return false;
}*/

// 此函数是在点击分页导航按钮之后被调用的，在这个函数中，可以发送请求
function pageselectCallback(pageNum){
    console.log(pageNum)
    // pageNum表示点击的页码数  注意： pageNum 从0开始的
    window.pageNum = pageNum+1;
    generatorPage();
    //换页后取消全选按钮被勾选
    $("#parentCkbox").prop("checked",false);
    // 取消按钮的默认行为
    return false;
}

//显示角色权限分配模态框
function showAssignAuthModal(obj) {
    //显示模态框
    $("#assignRoleAuthModal").modal("show");

    //数据库中查询到权限表并使用zTree显示
    var authsAjax=$.ajax({
        "url"     :    "assign/get/allAuth.json",
        "type"    :    "post",
        "dataType":    "json",
        "async"   :    false     //同步
    });
    console.log(authsAjax);
    if (authsAjax.status !=200){
        layer.msg("请求发送失败，请联系技术人员！", {time:2000, icon:2, shift:6});
        return;
    };
    if (authsAjax.responseJSON.operationResult == "FAILED") {
        layer.msg(ajaxResult.responseJSON.operationMessage, {time:2000, icon:2, shift:6});
        return;
    };

    //如果成功查询到，将数据放进zTree显示
    var setting = {
        data: {
            simpleData: {
                enable: true,
                pIdKey: "categoryId",  //父节点是由数据库中哪一个列表示
            },
            key:{
                name:"title"    //显示节点名称，是根据数据库中那一个列
            }
        },

        check: {
            enable: true    //节点前面加上单选框
        }
    };
    $(document).ready(function(){
        $.fn.zTree.init($("#authTree"), setting, authsAjax.responseJSON.queryData);
    });

    //将树设置成默认展开,得到树的对象
    var treeObj = $.fn.zTree.getZTreeObj("authTree");
    treeObj.expandAll(true);

    window.id = obj.id
    //查询inner_role_auth表，回显点击角色权限,authsAjax覆盖上面的
    var authsAjax=$.ajax({
        "url"   :    "assign/get/authByRoleId.json",
        "type"  :    "post",
        "data"  :    {
            roleId : window.id
        },
        "dataType" : "json",
        "async"    :  false
    });


    if (authsAjax.status !=200){
        layer.msg("请求发送失败，请联系技术人员！", {time:2000, icon:2, shift:6});
        return;
    };
    if (authsAjax.responseJSON.operationResult == "FAILED") {
        layer.msg(authsAjax.responseJSON.operationMessage, {time:2000, icon:2, shift:6});
        return;
    };

    //循环遍历查询到的auth_id
    $.each(authsAjax.responseJSON.queryData,function (index, authId) {
        var node = treeObj.getNodeByParam("id",authId, null);
        treeObj.checkNode(node,true, false);
    })



}


