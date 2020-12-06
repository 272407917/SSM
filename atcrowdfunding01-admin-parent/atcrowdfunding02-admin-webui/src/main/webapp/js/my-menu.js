function generatorMenu() {
    var setting = {
        data: {
            key: {
                //因为节点是一个超链接，说以取消掉节点上的href路径，给一个不存在的路径就可以取消掉
                //给了一个不存在的路径，herf会变成JavaScript：就是取消跳转
                url: "xUrl"
            }
        },
        view: {
            //设置是否允许同时选中多个节点。
            selectedMulti: false,

            addDiyDom: function (treeId, treeNode) {
                //通过字符串拼接，选中节点对象
                var icoObj = $("#" + treeNode.tId + "_ico"); // tId = treeDemo_2, $("#treeDemo_2_ico"
                //treeNode.ico数据库中每个节点的样式
                if (treeNode.ico) {
                    //移除节点初始样式，添加数据库中样式
                    icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.ico).css("background", "");
                }
            },

            //实现鼠标移动到节点上显示删除、修改、添加
            addHoverDom: function(treeId, treeNode){
                var aObj = $("#" + treeNode.tId + "_a"); //tId = treeDemo_2, $("#treeDemo_2_ico")
                /*该语句左右是，取消选中节点href点击跳转功能，与上面url功能相同可以去掉
                aObj.attr("href", "javascript:;");*/
                /*
                * treeNode.editNameFlag记录节点是否处于编辑名称状态,为true时return，不会弹出删除、修改、添加
                * "#btnGroup"+treeNode.tId).length>0
                * 两个判断条件
                * */
                if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
                var s = '<span id="btnGroup'+treeNode.tId+'">';
                /**
                 * treeNode.level:表示当前节点等级，这里只有三层节点
                 * 0：根节点，只出现添加
                 * 1：根节点下一级节点
                 * 2：叶子节点
                 * onclick是js原始的事件，click是jquery中新增的方法，click方法是执行会触发onclick事件，原生的js中没有click方法
                 * 页面加载完成时候页面显示的元素（DOM节点已全部加载完）可以用 on ， 也可以用click ，
                 * 但是在页面加载完成之后后期再追加元素（DOM节点元素还没完全显示出来）只能用on
                 * click:通过jQuery对象进行绑定，在页面加载完后绑定事件，但对于后面出现的元素是无法绑定的
                 * onclick：jsp元素中自带的，点击这个元素出发事件
                 * */
                if ( treeNode.level == 0 ) {
                    //根节点,onclick="showAddMenu(this)"将对象传过去,treeNode.id得到当前节点的id作为添加节点的pid
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="'+treeNode.id+'" onclick="showAddMenu(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if ( treeNode.level == 1 ) {
                    //子节点
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="'+treeNode.id+'" onclick="showEditModal(this)" style="margin-left:10px;padding-top:0px;"  href="javaScript:;" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    if (treeNode.children.length == 0) {
                        //该节点下没有子节点，那么就就显示删除，如果有叶子节点那么就不显示删除
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" id="'+treeNode.id+'" onclick="showDeleteModal(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="'+treeNode.id+'" onclick="showAddMenu(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if ( treeNode.level == 2 ) {
                    //叶子节点，只显示修改和删除，叶子节点是没有子节点的所以不显示添加
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="'+treeNode.id+'" onclick="showEditModal(this)" style="margin-left:10px;padding-top:0px;"  href="javaScript:;" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" id="'+treeNode.id+'" onclick="showDeleteModal(this)" style="margin-left:10px;padding-top:0px;" href="javaScript:;">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                }

                s += '</span>';
                aObj.after(s);
            },
            /*
            * addHoverDom要和removeHoverDom一起用
            * removeHoverDom：鼠标移开节点，添加、删除、修改就被移除（不显示）
            * */
            removeHoverDom: function(treeId, treeNode){
                $("#btnGroup"+treeNode.tId).remove();
            }
        },
    }


    //通过ajax请求取出t_menu表中数据，并通过ztree显示
    $.ajax({
        "url"    :    "menu/getAll.json",
        "dataType" :  "json",
        "type"     :  "get",
        "success"  :   function (response) {
            if (response.operationResult == "FAILED") {
                layer.msg(response.operationMessage,{time:2000, icon:2, shift:6});
            };
            if (response.operationResult == "SUCCESS") {
                //初始化zTree
                $.fn.zTree.init($("#treeDemo"), setting, response.queryData);
            }
        },
        "error"    :   function (response) {
            layer.msg(response.operationMessage,{time:2000, icon:2, shift:6});
        }
    });

}

//添加模态框
function showAddMenu(obj) {
    //显示添加模态框
    $("#menuAddModal").modal("show");
    //保存点击节点的id，作为新增节点的pid
    window.id = obj.id;
}

function showDeleteModal(obj) {
    $("#menuConfirmModal").modal("show");
    //取得当前节点的id作为删除条件
    window.id = obj.id;
}

function showEditModal(obj) {
    $("#menuEditModal").modal("show");


    window.id = obj.id;
    //根据 treeId 获取 zTree 对象的方法
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    //根据节点id查询数据
    var node = treeObj.getNodeByParam("id", window.id, null);


    //数据回显
    $("#menuEditModal [name = name]").val(node.name);
    $("#menuEditModal [name = url]").val(node.url);
    $("#menuEditModal [name = icon]").val([node.ico])
    //$("#menuEditModal [name='icon'][value='"+node.ico+"']").attr("checked",true);
}