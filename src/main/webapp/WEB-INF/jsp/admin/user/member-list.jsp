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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户中心 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form  method="post" id="searchForm">
		<div class="text-c"> 日期范围：
			<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;" name="QUERY-last_login_time-d-ge"/>
			-
			<input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;" name="QUERY-last_login_time-d-le"/>
			<input type="text" class="input-text" style="width:250px" placeholder="输入会员名称、电话、邮箱" id="" name="QUERY-username-s-lk"/>
			<button type="button" class="btn btn-success radius" id="searchButton" name=""><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="batchDel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="member_add('添加用户','${ctx}/admin/user/add')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span> <span class="r">共有数据：<strong>88</strong> 条</span> </div>
	<div class="mt-20">
		<table id="bsGrid" class="bsgrid">
			<tr>
				<th w_index="id" w_check="true"></th>
				<th w_index="id">ID</th>
				<th w_index="username" w_sort="username">用户名</th>
				<th w_index="pwd">密码</th>
				<th w_index="email" w_sort="email">邮箱</th>
				<th w_index="reallyName">真实姓名</th>
				<th w_index="time">登录次数</th>
				<th w_index="lastLoginIp">上次登录ip</th>
				<th w_index="lastLoginTime" w_render="fmtLastLoginTime" w_sort="lastLoginTime">上次登录时间</th>
				<th w_index="roleId" w_render="roleIdFn">角色</th>
				<th w_render="toolbar" w_align="center">操作</th>
			</tr>
		</table>
	</div>
</div>

	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/js/lang/grid.zh-CN.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/laypage/1.2/laypage.js"></script>
	<script type="text/javascript">
		var gridObj;
		$(function(){
			/** 初始化表格 */
			gridObj = $.fn.bsgrid.init('bsGrid', {
				url: '/admin/user/list',
				pageSizeSelect: true,
				pageSize: 20
			});

			/** 多条件模糊搜索 */
			$("#searchForm").on("click", "#searchButton", function () {
				$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
			});

			/** 多条件回车模糊搜索 */
			$("#searchForm input").keydown(function (event) {
				var key_code = event.keyCode;
				if (key_code == 13) {
					$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
				}
			});

		});

		//格式化上次登录时间
		function fmtLastLoginTime(row) {
			var lastLoginTime = row.lastLoginTime;
			return (new Date(lastLoginTime)).Format("yyyy-MM-dd hh:mm:ss");
		}

		/**
		 * 格式化日期函数
		 * @param fmt
		 * @returns {*}
		 * @constructor
		 */
		Date.prototype.Format = function(fmt) { //author: meizz
			var o = {
				"M+": this.getMonth() + 1,
				//月份
				"d+": this.getDate(),
				//日
				"h+": this.getHours(),
				//小时
				"m+": this.getMinutes(),
				//分
				"s+": this.getSeconds(),
				//秒
				"q+": Math.floor((this.getMonth() + 3) / 3),
				//季度
				"S": this.getMilliseconds() //毫秒
			};
			if (/(y+)/.test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			}
			for (var k in o) {
				if (new RegExp("(" + k + ")").test(fmt)) {
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				}
			}
			return fmt;
		}

		/**
		 * 所属角色
		 */
		function roleIdFn(row) {
			switch(row.roleId)
			{
				case 1:
					return "admin";
					break;
				case 2:
					return "web_manager";
					break;
				case 3:
					return "web_user";
					break;
				default:
					return "######";
			}
		}

		/**
		 * 操作按钮
		 */
		function toolbar(row) {
			var id = row.id;
			var html = "";
			html += '<a title="编辑" href="javascript:;" onclick="member_edit(\''+id+'\')" ><i class="Hui-iconfont">&#xe6df;</i>编辑</a>'
			html +=  '<a title="删除" href="javascript:;" onclick="member_del(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe6e2;</i>删除</a>';
			return html;
		}

		/**
		 * 用户-添加
		 */
		function member_add(title,url){
			layer_show(title,url);
		}

		/**
		 * 用户-修改
		 */
		function member_edit(id){
			layer_show("修改用户", "${ctx}/admin/user/edit/"+id);
		}

		/**
		 * 用户-批量删除
		 */
		function batchDel() {
			var ids = gridObj.getCheckedValues('id');
			if(ids.length > 0){
				layer.confirm('确认要删除吗？',function(index){
					$.ajax({
						type: 'POST',
						url: '${ctx}/admin/user/batchDel',
						dataType: 'json',
						data : {'ids':ids.toString()},
						success: function(data){
							if(data== true){
								layer.msg('删除成功！',{icon:1,time:1000});
								gridObj.refreshPage();
							}else{
								layer.msg('删除失败！',{icon:5,time:1000});
							}
						},
						error:function(data) {
							layer.msg('服务器错误，请联系管理员!',{icon:5,time:1000});
						},
					});
				});
			}else{
				layer.msg('未勾选数据！',{icon:5,time:2000});
			}
		}

		/**
		 * 用户 - 删除
		 * @param id
         */
		function member_del(id){
			layer.confirm('确认要删除吗？',function(index){
				$.ajax({
					type: 'POST',
					url: '${ctx}/admin/user/batchDel',
					dataType: 'json',
					data : {'ids': id+","},
					success: function(data){
						if(data== true){
							layer.msg('删除成功！',{icon:1,time:1000});
							gridObj.refreshPage();
						}else{
							layer.msg('删除失败！',{icon:5,time:1000});
						}
					},
					error:function(data) {
						layer.msg('服务器错误，请联系管理员!',{icon:5,time:1000});
					},
				});
			});
		}
	</script>
</body>
</html>