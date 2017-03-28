<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
	<link rel="stylesheet" href="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.css"/>
	<style>
		table.bsgrid{
			width:100%;
		}
	</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 权限管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c">
		<form class="Huiform" method="post" target="_self" id="searchForm">
			<input type="text" class="input-text" style="width:250px" placeholder="权限名称" id="" name="QUERY-permission_name-s-lk">
			<button type="button" class="btn btn-success" id="searchButton" name=""><i class="Hui-iconfont">&#xe665;</i> 搜权限节点</button>
		</form>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="admin_permission_add('添加权限节点','admin-permission-add.html','','310')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加权限节点</a></span> </div>

	<table id="bsGrid" class="bsgrid">
		<tr>
			<th w_index="id" w_check="true"></th>
			<th w_index="id">ID</th>
			<th w_index="uri" w_sort="uri">uri</th>
			<th w_index="permissionName" w_sort="permissionName">权限名称</th>
			<th w_index="nowId" w_render="nowIdFn">菜单等级</th>
			<th w_index="parentId" w_sort="uri" w_render="parentIdFn">父级菜单</th>
			<th w_render="toolbar" w_align="center">操作</th>
		</tr>
	</table>

</div>
<!--_footer 作为公共模版分离出去-->
<%@ include file="../jspf/footer.jspf" %>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.js"></script>
<script type="text/javascript">
	$(function(){
		/** 初始化表格 */
		$.fn.bsgrid.init('bsGrid', {
			url: '/admin/permission/list',
			pageSizeSelect: true,
			pageSize: 20
		});

		/** 多条件模糊搜索 */
		$("#searchForm").on("click", "#searchButton", function () {
			$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
		});
	});

	/**
	 * 菜单等级
	 */
	function nowIdFn(row) {
		return (row.nowId == 2) ?  "二级菜单" : ((row.nowId == 3) ? "三级菜单" : "######");
	}

	/**
	 * 父级等级
	 */
	function parentIdFn(row) {
		switch(row.parentId)
		{
			case 1:
				return "会员管理";
				break;
			case 2:
				return "角色权限管理";
				break;
			default:
				return "######";
		}
	}

	/**
	 * 操作
	 */
	function toolbar(row) {
		var id = row.id;
		var html = "";
		html += '<a title="编辑" href="javascript:;" onclick="edit(\''+id+'\')" ><i class="Hui-iconfont">&#xe6df;</i>编辑</a>'
		html +=  '<a title="删除" href="javascript:;" onclick="del(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe6e2;</i>删除</a>';
		return html;
	}

	/**
	 * 权限编辑
	 */
	function edit(id) {
		layer_show("权限编辑","${ctx}/admin/permission/edit/"+id,800,400);
	}

/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*管理员-权限-添加*/
function admin_permission_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-权限-编辑*/
function admin_permission_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}

/*管理员-权限-删除*/
function admin_permission_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").remove();
				layer.msg('已删除!',{icon:1,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}
</script>
</body>
</html>