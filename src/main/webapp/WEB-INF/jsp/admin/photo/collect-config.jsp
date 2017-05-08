﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
				<th w_index="startTime" w_render="fmtStartTime" w_sort="startTime">开始时间</th>
				<th w_index="endTime" w_render="fmtEndTime" w_sort="endTime">结束时间</th>
				<th w_index="description">描述</th>
				<th w_index="userId">发布者</th>
				<th w_index="status" w_render="statusFn">最新发布</th>
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
		 * 操作
		 */
		function toolbar(row) {
			var id = row.id;
			var status = statusFn();
			console.log("status:"+status);
			var html = "";
			html += '<a title="编辑" href="javascript:;" onclick="edit(\''+id+'\')" ><i class="Hui-iconfont">&#xe6df;</i>编辑</a>'
			html +=  '<a title="删除" href="javascript:;" onclick="del(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe6e2;</i>删除</a>';
			if(status == "已结束"){
				html +=  '<a title="分析" href="javascript:;" onclick="analysis(\''+id+'\')" class="ml-15"><i class="Hui-iconfont">&#xe61c;</i>分析</a>';
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
	</script>
</body>
</html>