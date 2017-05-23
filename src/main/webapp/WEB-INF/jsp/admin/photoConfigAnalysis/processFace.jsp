<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
	</head>
	<style type="text/css">
		.page-container{
			/*border: 1px solid red;*/
			/*padding: 0;*/
		}
	</style>
<body>
	<!-- 菜单 -->
	<div class="cl pd-5 bg-1 bk-gray">
		<%@ include file="analysis-header-menu.jspf" %>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<%@ include file="process-menu.jspf" %>
		<div id="container" style="min-width:700px;height:400px;margin-top:100px;"></div>
		<div style="margin-top: 30px">
			<span style="font-weight: bold;">提示：</span><br/>
			<span>~ 面部表情指的是考生在考试过程中面部表情信息，有0~100的区间，分别为:黯然伤神,半嗔半喜,似笑非笑,笑逐颜开,莞尔一笑,喜上眉梢,眉开眼笑,笑尽妖娆,心花怒放,一笑倾城，分词仅供参考！</span><br/>
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {
			var expression = ${expression};

			Highcharts.chart('container', {
				title: {
					text: '面部表情走势图',
					x: -20 //center
				},
				subtitle: {
					text: 'Source: http://www.xlbweb.com',
					x: -20
				},
				yAxis: {
					title: {
						text: 'Expression'
					},
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				tooltip: {
					valueSuffix: '°'
				},
				legend: {
					layout: 'vertical',
					align: 'right',
					verticalAlign: 'middle',
					borderWidth: 0
				},
				series: [{
					name: '表情：',
					data: expression
				}]
			});
		});
	</script>
</body>
</html>