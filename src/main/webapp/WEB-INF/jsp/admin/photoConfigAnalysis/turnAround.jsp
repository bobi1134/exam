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
		<div id="container" style="min-width:700px;height:400px"></div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {
			var result = ${result};

			$('#container').highcharts({
				chart: {
					type: 'area'
				},
				title: {
					text: '学生转向问题'
				},
				subtitle: {
					text: 'Source: http://www.xlbweb.com'
				},
				xAxis: {
					labels: {
						formatter: function() {
							return this.value; // clean, unformatted number for year
						}
					}
				},
				yAxis: {
					title: {
						text: 'Turn around (0~100)'
					}
				},
				tooltip: {
					pointFormat: '{series.name} 转向角度 <b>{point.y}</b>' +
					'<br/>左(0~33)、中（34~66）、右（67~100）'
				},
				plotOptions: {
					area: {
						pointStart: 0,
						marker: {
							enabled: false,
							symbol: 'circle',
							radius: 2,
							states: {
								hover: {
									enabled: true
								}
							}
						}
					}
				},
				series: [{
					name: '${student.reallyName}',
					data: result
				}]
			});
		});
	</script>
</body>
</html>