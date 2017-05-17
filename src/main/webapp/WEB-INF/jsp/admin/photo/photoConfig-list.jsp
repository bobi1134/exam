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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 过程分析 <span class="c-gray en">&gt;</span> 采集配置 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<div class="text-c">
			<form class="Huiform" target="_self" id="searchForm">
				<input type="text" style="display:none;"/>
				<input type="text" class="input-text" style="width:250px" placeholder="描述" id="" name="QUERY-description-s-lk" onkeypress="if (event.keyCode == 13){ doSearch();}"/>
				<button type="button" class="btn btn-success" id="searchButton" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</form>
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<a href="javascript:;" onclick="batchDel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
				<a href="javascript:;" onclick="add('添加采集规则','${ctx}/admin/photoConfig/add','','400')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加采集规则</a>
			</span>
		</div>
		<table id="bsGrid" class="bsgrid">
			<tr>
				<th w_index="id" w_check="true"></th>
				<th w_index="id">ID</th>
				<th w_index="startTime" w_render="fmtStartTime" w_sort="start_time">开始时间</th>
				<th w_index="endTime" w_render="fmtEndTime" w_sort="end_time">结束时间</th>
				<th w_index="description">描述</th>
				<th w_index="user" w_render="userNameFn" w_sort="user_id">发布者</th>
				<th w_index="status" w_render="statusFn">最新状态</th>
				<th w_render="toolbar" w_align="center">操作</th>
			</tr>
		</table>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/js/lang/grid.zh-CN.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		var gridObj;
		$(function(){
			/** 初始化表格 */
			gridObj = $.fn.bsgrid.init('bsGrid', {
				url: '/admin/photoConfig/list',
				pageSizeSelect: true,
				pageSize: 20
			});

			/** 多条件模糊搜索 */
			$("#searchForm").on("click", "#searchButton", function () {
				$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
			});
		});

		/**
		 * 回车搜索
		 */
		function doSearch() {
			$.fn.bsgrid.getGridObj("bsGrid").search($('#searchForm').serializeArray());
		}

		/**
		 * 状态：未开始、考试中、已结束
		 */
		var startTime, endTime;
		function statusFn() {
			var nowTime = new Date();
			var status;
			if(nowTime < startTime){
				status = "未开始";
			}else if(nowTime > endTime){
				status = "已结束";
			}else if(nowTime > startTime && nowTime < endTime){
				status = "考试中";
			}
			return status;
		}

		/**
		 * 格式化开始时间
		 */
		function fmtStartTime(row) {
			startTime= row.startTime;
			return (new Date(startTime)).Format("yyyy-MM-dd hh:mm:ss");
		}

		/**
		 * 格式化结束时间
		 */
		function fmtEndTime(row) {
			endTime = row.endTime;
			return (new Date(endTime)).Format("yyyy-MM-dd hh:mm:ss");
		}

		/**
		 * 格式化日期函数
		 * @param fmt
		 * @returns {*}
		 * @constructor
		 */
		Date.prototype.Format = function(fmt) {
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
		 * 发布者
		 */
		function userNameFn(row) {
			var user = row.user;
			return user.username;
		}

		/**
		 * 操作
		 */
		function toolbar(row) {
			var id = row.id;
			var status = statusFn();
			var html = "";
			html += '<a title="编辑" href="javascript:;" onclick="edit(\''+id+'\')" ><i class="Hui-iconfont">&#xe6df;</i>编辑</a>'
			html +=  '<a title="删除" href="javascript:;" onclick="del(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe6e2;</i>删除</a>';

			if(status == "考试中" || status == "已结束"){
				html +=  '<a title="图片库" href="javascript:;" onclick="photo(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe613;</i> 图片库</a>';
			}else{
				html +=  '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			}

			html +=  '<a title="入库" href="javascript:;" onclick="informationCollect(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe67a;</i>入库</a>';

			if(status == "已结束"){
				html +=  '<a title="分析" href="javascript:;" onclick="analysis(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe61c;</i>分析</a>';
			}else{
				html +=  '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			}
			return html;
		}

		/**
		 * 添加采集规则页面
		 * @param title
		 * @param url
         * @param w
         * @param h
         */
		function add(title, url, w, h) {
			layer_show(title, url, w, h);
		}

		/**
		 * 更改采集规则页面
		 */
		function edit(id) {
			layer_show("更新采集规则", "${ctx}/admin/photoConfig/edit/" + id, 800, 400);
		}

		/**
		 * 批量删除
		 */
		function batchDel() {
			var ids = gridObj.getCheckedValues('id');
			//console.log("ids:"+ids);
			if(ids.length > 0){
				//是否都为空flag
				var flag = true;
				for(var i=0; i<ids.length; i++){
					if(ids[i] != "") {
						flag = false;
						break;
					}
					flag = true;
				}
				//任意一个不为空
				if(!flag){
					ajaxDel(ids);
				}else{
					layer.msg('无效数据！',{icon:5,time:2000});
				}
			}else{
				layer.msg('未勾选数据！',{icon:5,time:2000});
			}
		}

		/**
		 * 异步删除
		 */
		function ajaxDel(ids) {
			layer.confirm('确定要删除吗？', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					type: 'post',
					url: '${ctx}/admin/photoConfig/del',
					dataType: 'json',
					data : {'ids': ids.toString()},
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
			});
		}

		/**
		 * 单条删除
		 * @param id
         */
		function del(id) {
			var ids = new Array(id);
			ajaxDel(ids);
		}

		/**
		 * 图片库页面
		 */
		function photo(id) {
			var index = layer.open({
				type: 2,
				title: "图片库",
				content: "${ctx}/admin/photoConfig/photo-gallery/"+id
			});
			layer.full(index);
		}

		/**
		 * 结果分析-技术支持页面（默认打开页面）
		 * @param id
         */
		function analysis(id) {
			var index = layer.open({
				type: 2,
				title: "结果分析",
				content: "${ctx}/admin/photoConfig/analysis-technicalSupport/"+id
			});
			layer.full(index);
		}

		/**
		 * 信息分析入库
		 */
		function informationCollect(id) {
			var index;
			$.ajax({
				type: 'post',
				url: '${ctx}/admin/photoConfig/analysis-informationCollect/'+id,
				dataType: 'json',
				beforeSend:function () {
					index = layer.load(0, {shade: false});
				},
				success: function(data){
					console.log(data);
					console.log(data.status);
					console.log( data.isCollect);
					if(data.status == "gt0" && data.isCollect == "false"){
						layer.close(index);
						var result = "入库状态！成功"+data.successNum+"张，失败"+data.errorNum+"张！";
						layer.msg(result, {icon:1,time:2000});
					}
					if(data.status == "gt0" && data.isCollect == "true"){
						layer.close(index);
						layer.msg("已是最新入库状态！", {icon:1,time:2000});
					}
					if(data.status == "lt0"){
						layer.close(index);
						layer.msg("没有检查到图片库，请检查！", {icon:5,time:2000});
					}
				},
				error:function(data) {
					layer.close(index);
					layer.msg('服务器错误，请联系管理员!',{icon:5,time:1000});
				},
			});
		}
	</script>
</body>
</html>