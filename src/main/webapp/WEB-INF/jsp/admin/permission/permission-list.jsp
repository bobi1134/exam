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
		<form class="Huiform" target="_self" id="searchForm">
			<input type="text" style="display:none;"/>
			<input type="text" class="input-text" style="width:250px" placeholder="权限名称" id="" name="QUERY-permission_name-s-lk" onkeypress="if (event.keyCode == 13){ doSearch();}"/>
			<button type="button" class="btn btn-success" id="searchButton" name=""><i class="Hui-iconfont">&#xe665;</i> 搜权限节点</button>
		</form>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20">
		<span class="l">
			<a href="javascript:;" onclick="batchDel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
			<a href="javascript:;" onclick="add('添加权限节点','${ctx}/admin/permission/add','','400')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加权限节点</a>
		</span>
	</div>
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
	var gridObj;
	$(function(){
		/** 初始化表格 */
		gridObj = $.fn.bsgrid.init('bsGrid', {
			url: '/admin/permission/list',
			pageSizeSelect: true,
			pageSize: 20
		});

		/** 多条件模糊搜索 */
		$("#searchForm").on("click", "#searchButton", function () {
			$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
		});
	});
	
	function doSearch() {
		$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
	}

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
			case 3:
				return "人脸识别技术";
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
	 * 权限编辑页面
	 */
	function edit(id) {
		layer_show("权限编辑","${ctx}/admin/permission/edit/"+id,800,400);
	}

	/**
	 * 批量删除权限
	 */
	function batchDel() {
		var ids = gridObj.getCheckedValues('id');
		if(ids.length > 0){
			layer.confirm(
				'确定要删除嘛？',
				{
					btn: ['确定', '取消']
				},
				function(index, layero){
					//异步删除
					$.ajax({
						type: 'post',
						url: '${ctx}/admin/permission/del',
						dataType: 'json',
						data : {'ids':ids.toString()},
						success: function(data){
							if(data== true){
								layer.msg('已删除!',{icon:1,time:1000});
								gridObj.refreshPage();
							}else if(data == null){
								layer.msg('你勾选的是无效数据!',{icon:5,time:1000});
							}else{
								layer.msg('删除失败!',{icon:5,time:1000});
							}

						},
						error:function(data) {
							layer.msg('服务器错误，请联系管理员!',{icon:5,time:1000});
						},
					});

				},
				function(index, layero){

				}
			);
		}else{
			layer.msg('你不勾选就想删除，在逗我？',{icon:5,time:2000});
		}
	}

	/**
	 * 权限删除
	 */
	function del(id) {
		if(id != ""){
			layer.confirm(
				'确定要删除嘛？',
				{
					btn: ['确定', '取消']
				},
				function(index, layero){
					//异步删除
					$.ajax({
						type: 'post',
						url: '${ctx}/admin/permission/del',
						dataType: 'json',
						data : {'ids':id+","},
						success: function(data){
							if(data== true){
								layer.msg('已删除!',{icon:1,time:1000});
								gridObj.refreshPage();
							}else{
								layer.msg('删除失败!',{icon:5,time:1000});
							}

						},
						error:function(data) {
							layer.msg('服务器错误，请联系管理员!',{icon:5,time:1000});
						},
					});
				},
				function(index, layero){}
			);
		}
	}


	/**
	 *
	 * @param title
	 * @param url
	 * @param w
     * @param h
     */
	function add(title,url,w,h){
		layer_show(title,url,w,h);
	}
</script>
</body>
</html>