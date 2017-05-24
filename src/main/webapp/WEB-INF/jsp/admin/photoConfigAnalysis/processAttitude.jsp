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
		<%@ include file="analysis-header-menu.jspf" %>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<%@ include file="process-menu.jspf" %>
		<div id="container" style="min-width:700px;height:400px;margin-top:50px;"></div>
		<div style="margin-top: 30px">
			<span style="font-weight: bold;">提示：</span><br/>
			<span>~ 态度分析指的是考生在考试过程中的面部变化信息，如果高兴、难过等。</span><br/>
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts-3d.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {
			var count = ${count};
			var expressions1 = ${expressions1};
			var expressions2 = ${expressions2};
			var expressions3 = ${expressions3};
			var expressions4 = ${expressions4};
			var expressions5 = ${expressions5};
			var expressions6 = ${expressions6};
			var expressions7 = ${expressions7};
			var expressions8 = ${expressions8};
			var expressions9 = ${expressions9};
			var expressions10 = ${expressions10};


			$('#container').highcharts({
				chart: {
					type: 'pie',
					options3d: {
						enabled: true,
						alpha: 45,
						beta: 0
					}
				},
				title: {
					text: '态度分析'
				},
				tooltip: {
					pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions: {
					pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						depth: 35,
						dataLabels: {
							enabled: true,
							format: '{point.name}'
						}
					}
				},
				series: [{
					type: 'pie',
					name: '分布',
					data: [
						['黯然伤神', expressions1/count*100],
						['半嗔半喜', expressions2/count*100],
						{
							name: '似笑非笑',
							y: expressions3/count*100,
							sliced: true,
							selected: true
						},
						['笑逐颜开', expressions4/count*100],
						['莞尔一笑', expressions5/count*100],
						['喜上眉梢', expressions6/count*100],
						['眉开眼笑', expressions7/count*100],
						['笑尽妖娆', expressions8/count*100],
						['心花怒放', expressions9/count*100],
						['一笑倾城', expressions10/count*100],
					]
				}]
			});
		});
	</script>
</body>
</html>