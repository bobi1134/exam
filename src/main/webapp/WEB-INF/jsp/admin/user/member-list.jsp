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
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span> <span class="r">共有数据：<strong>88</strong> 条</span> </div>
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
				<th w_index="lastLoginTime" w_render="fmtLastLoginTime">上次登录时间</th>
				<th w_index="roleId" w_render="roleIdFn">角色</th>
			</tr>
		</table>
	</div>
</div>

<!--_footer 作为公共模版分离出去-->
<%@ include file="../jspf/footer.jspf" %>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<%--<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>--%>
<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$(function(){
	/** 初始化表格 */
	$.fn.bsgrid.init('bsGrid', {
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




/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*用户-查看*/
function member_show(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_start(this,id)" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已停用</span>');
				$(obj).remove();
				layer.msg('已停用!',{icon: 5,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

/*用户-启用*/
function member_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_stop(this,id)" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
				$(obj).remove();
				layer.msg('已启用!',{icon: 6,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}
/*用户-编辑*/
function member_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*密码-修改*/
function change_password(title,url,id,w,h){
	layer_show(title,url,w,h);	
}
/*用户-删除*/
function member_del(obj,id){
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