<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
		<style type="text/css">
			.page-container{
				padding-top: 20px;
				padding-right: 5px;
			}
			.page-container .pie{
				margin-top: 20px;
			}
			.page-container .pie div{
				float: left;
				min-width:33%;
				height:350px;
				margin-top: 10px;
			}
		</style>
	</head>
	<body>
	<!-- 菜单 -->
	<div class="cl pd-5 bg-1 bk-gray">
		<span class="l">
			<%@ include file="photoConfig-analysis-menu.jspf" %>
		</span>
		<span class="r">
			<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
		</span>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<span class="r">
			<a href="${ctx}/admin/photoConfig/analysis-successRate-details/${id}" class="btn btn-primary radius">查看详情</a>
		</span>
		<div class="pie">
			<div id="detectface"></div>
			<div id="faceshape"></div>
			<div id="faceCompare"></div>
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {

			//人脸分析采集成功率
			var count = ${count};
			var exception_Detectface = ${exception_Detectface};
			var errorcode_Detectface = ${errorcode_Detectface};
			$('#detectface').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
				},
				title: {
					text: '人脸分析采集成功率'
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							color: '#000000',
							connectorColor: '#000000',
							format: '<b>{point.name}</b>: {point.percentage:.1f} %'
						}
					}
				},
				series: [{
					type: 'pie',
					name: '占有比例',
					data: [
						['程序异常', exception_Detectface/count],
						['检测失败', errorcode_Detectface/count],
						{
							name: '检测成功',
							y: 100,
							sliced: true,
							selected: true
						},

					]
				}]
			});

			//五官检测采集成功率
			var exception_Faceshape = ${exception_Faceshape};
			var errorcode_Faceshape = ${errorcode_Faceshape};
			$('#faceshape').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
				},
				title: {
					text: '五官检测采集成功率'
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							color: '#000000',
							connectorColor: '#000000',
							format: '<b>{point.name}</b>: {point.percentage:.1f} %'
						}
					}
				},
				series: [{
					type: 'pie',
					name: '占有比例',
					data: [
						['程序异常', exception_Faceshape/count],
						['检测失败', errorcode_Faceshape/count],
						{
							name: '检测成功',
							y: 100,
							sliced: true,
							selected: true
						},

					]
				}]
			});

			//人脸对比采集成功率
			var exception_FaceCompare = ${exception_FaceCompare};
			var errorcode_FaceCompare = ${errorcode_FaceCompare};
			$('#faceCompare').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
				},
				title: {
					text: '人脸对比采集成功率'
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
							enabled: true,
							color: '#000000',
							connectorColor: '#000000',
							format: '<b>{point.name}</b>: {point.percentage:.1f} %'
						}
					}
				},
				series: [{
					type: 'pie',
					name: '占有比例',
					data: [
						['程序异常', exception_FaceCompare/count],
						['检测失败', errorcode_FaceCompare/count],
						{
							name: '检测成功',
							y: 100,
							sliced: true,
							selected: true
						},

					]
				}]
			});
		});
	</script>
</body>
</html>