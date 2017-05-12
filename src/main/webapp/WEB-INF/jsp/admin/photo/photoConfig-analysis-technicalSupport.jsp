<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
	</head>
<body>
	<!-- 菜单 -->
	<div class="cl pd-5 bg-1 bk-gray">
		<span class="l">
			<a href="${ctx}/admin/photoConfig/analysis-technicalSupport/${id}" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe621;</i> 技术支持</a>
			<a href="${ctx}/admin/photoConfig/analysis-processAnalysis/${id}" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe710;</i> 过程分析</a>
			<a href="javascript:;" onclick="xxx()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 折线图</a>
		</span>
		<span class="r">
			<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
		</span>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<div id="container" style="min-width:700px;height:400px"></div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {
			$('#container').highcharts({
				chart: {
					plotBackgroundColor: null,
					plotBorderWidth: null,
					plotShadow: false
				},
				title: {
					text: '技术支持分布图'
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
						['人脸检测',   45.0],
						['人脸分析',       26.8],
						{
							name: '人脸对比',
							y: 12.8,
							sliced: true,
							selected: true
						},
						['人脸搜索',    8.5],

					]
				}]
			});
		});
	</script>
</body>
</html>