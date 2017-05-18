<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
		<link rel="stylesheet" href="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.css"/>
		<style type="text/css">
			table.bsgrid{
				width:100%;
			}
			.page-container{
			}

			.page-container a{
				margin-bottom: 5px;
			}
		</style>
	</head>
	<body>
	<!-- 菜单 -->
	<div class="cl pd-5 bg-1 bk-gray">
		<span class="l">
			<%@ include file="menu.jspf" %>
		</span>
		<span class="r">
			<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px;margin-right: 14px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
		</span>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<a href="${ctx}/admin/photoConfigAnalysis/successRate/${id}" class="btn btn-primary radius r">返回</a>
		<table id="bsGrid" class="bsgrid">
			<tr>
				<th w_index="id">ID</th>
				<th w_index="resultDetectface" w_sort="resultDetectface">人脸分析</th>
				<th w_index="resultFaceshape" w_sort="resultFaceshape">五官检测</th>
				<th w_index="resultFacecompare" w_sort="resultFacecompare">人脸对比</th>
				<%--<th w_index="name">照片名</th>--%>
				<th w_index="createTime" w_render="createTimeFn" w_sort="createTime">创建时间</th>
				<th w_index="userId">userId</th>
			</tr>
		</table>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/js/lang/grid.zh-CN.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/bsgrid/builds/merged/bsgrid.all.min.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		var gridObj;
		$(function(){
			/** 初始化表格 */
			gridObj = $.fn.bsgrid.init('bsGrid', {
				url: '/admin/photoConfigAnalysis/successRateDetails/'+${id},
				pageSizeSelect: true,
				pageSize: 20
			});
		});

		/**
		 * 格式化显示照片创建时间
		 */
		function createTimeFn(row) {
			return (new Date(row.createTime)).Format("yyyy-MM-dd hh:mm:ss");
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
	</script>
</body>
</html>