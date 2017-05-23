<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
		<style type="text/css">
			.page-container .pie{
				margin-top: 31px;
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
		<%@ include file="analysis-header-menu.jspf" %>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<a href="${ctx}/admin/photoConfigAnalysis/successRateDetails/${photoConfigId}/${student.id}" class="btn btn-primary radius r">查看详情</a>
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
			var errorcode_x_Detectface = ${errorcode_x_Detectface};
			var errorcode_0_Detectface = ${errorcode_0_Detectface};
			//程序异常、检测失败、检测成功
			var detectfaceA = exception_Detectface/count * 100;
			var detectfaceB = errorcode_x_Detectface/count * 100;
			var detectfaceC = errorcode_0_Detectface/count * 100;
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
//						['程序异常', detectfaceA],
						{
							name: '程序异常',
							y: detectfaceA,
							color:"#f00"
						},
						['检测失败', detectfaceB],
						{
							name: '检测成功',
							y: detectfaceC,
							sliced: true,
							selected: true
						},

					]
				}]
			});

			//五官检测采集成功率
			var exception_Faceshape = ${exception_Faceshape};
			var errorcode_x_Faceshape = ${errorcode_x_Faceshape};
			var errorcode_0_Faceshape = ${errorcode_0_Faceshape};
			//程序异常、检测失败、检测成功
			var faceshapeA = exception_Faceshape/count * 100;
			var faceshapeB = errorcode_x_Faceshape/count * 100;
			var faceshapeC = errorcode_0_Faceshape/count * 100;
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
//						['程序异常', faceshapeA],
						{
							name: '程序异常',
							y: faceshapeA,
							color:"#f00"
						},
						['检测失败', faceshapeB],
						{
							name: '检测成功',
							y: faceshapeC,
							sliced: true,
							selected: true
						},

					]
				}]
			});

			//人脸对比采集成功率
			var exception_FaceCompare = ${exception_FaceCompare};
			var errorcode_x_FaceCompare = ${errorcode_x_FaceCompare};
			var errorcode_0_FaceCompare = ${errorcode_0_FaceCompare};
			//程序异常、检测失败、检测成功
			var faceCompareA = exception_FaceCompare/count * 100;
			var faceCompareB = errorcode_x_FaceCompare/count * 100;
			var faceCompareC = errorcode_0_FaceCompare/count * 100;
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
//						['程序异常', faceCompareA],
						{
							name: '程序异常',
							y: faceCompareA,
							color:"#f00"
						},
						['检测失败', faceCompareB],
						{
							name: '检测成功',
							y: faceCompareC,
							sliced: true,
							selected: true,
						},

					]
				}]
			});
		});
	</script>
</body>
</html>