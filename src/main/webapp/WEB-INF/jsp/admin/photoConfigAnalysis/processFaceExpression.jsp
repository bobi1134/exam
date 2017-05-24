<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
	</head>
	<style type="text/css">
		#refreshBtn1,#refreshBtn2{
			position: absolute;
			top: 128px;
			right: 54px;
			padding: 0px 4px;
			height: 19px;
			font-size: 12px;
			width: 20px;
		}
		#refreshBtn2{
			top: 696px;
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
		<!-- 面部表情分布图 -->
		<div id="expressionDistribution" style="min-width:700px;height:400px;margin-top:50px;"></div>
		<div class="btn btn-success radius" id="refreshBtn1" title="重绘图表"><i class="Hui-iconfont">&#xe68f;</i></div>
		<div style="margin-top: 30px">
			<span style="font-weight: bold;">提示：</span><br/>
			<span>~ 面部表情分布图指的是考生在考试过程中的面部变化信息，如果高兴、难过等。</span><br/>
			<span>~ 区间为0~100。</span><br/>
			<span>~ 以10为区别单位，分别为黯然伤神、半嗔半喜、似笑非笑、笑逐颜开、莞尔一笑、喜上眉梢、眉开眼笑、笑尽妖娆、心花怒放、一笑倾城，分词仅供参考。</span><br/>
		</div>

		<!-- 面部表情走势图 -->
		<div id="expressionTrend" style="min-width:700px;height:400px;margin-top:50px;"></div>
		<div class="btn btn-success radius" id="refreshBtn2" title="重绘图表"><i class="Hui-iconfont">&#xe68f;</i></div>
		<div style="margin-top: 30px">
			<span style="font-weight: bold;">提示：</span><br/>
			<span>~ 面部表情走势图指的是考生在考试过程中面部表情信息走向，有0~100的区间。</span><br/>
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
			expressionDistribution();
			/**
			 * 面部表情分布图
			 */
			function expressionDistribution() {
				var count = ${count};
				console.log("count:"+count);
				var expressionCountArray0 = ${expressionCountArray[0]};
				var expressionCountArray1 = ${expressionCountArray[1]};
				var expressionCountArray2 = ${expressionCountArray[2]};
				var expressionCountArray3 = ${expressionCountArray[3]};
				var expressionCountArray4 = ${expressionCountArray[4]};
				var expressionCountArray5 = ${expressionCountArray[5]};
				var expressionCountArray6 = ${expressionCountArray[6]};
				var expressionCountArray7 = ${expressionCountArray[7]};
				var expressionCountArray8 = ${expressionCountArray[8]};
				var expressionCountArray9 = ${expressionCountArray[9]};
				$('#expressionDistribution').highcharts({
					chart: {
						type: 'pie',
						options3d: {
							enabled: true,
							alpha: 45,
							beta: 0
						}
					},
					title: {
						text: '面部表情分布图'
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
							['黯然伤神', expressionCountArray0/count*100],
							['半嗔半喜', expressionCountArray1/count*100],
							{
								name: '似笑非笑',
								y: expressionCountArray2/count*100,
								sliced: true,
								selected: true
							},
							['笑逐颜开', expressionCountArray3/count*100],
							['莞尔一笑', expressionCountArray4/count*100],
							['喜上眉梢', expressionCountArray5/count*100],
							['眉开眼笑', expressionCountArray6/count*100],
							['笑尽妖娆', expressionCountArray7/count*100],
							['心花怒放', expressionCountArray8/count*100],
							['一笑倾城', expressionCountArray9/count*100],
						]
					}]
				});
			}

			/**
			 * 点击刷新
			 */
			$("#refreshBtn1").click(function () {
				expressionDistribution()
			});

			/**
			 * 面部表情走势图
			 */
			expressionTrend();
			function expressionTrend() {
				var expressions = ${expressions};
				Highcharts.chart('expressionTrend', {
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
						data: expressions
					}]
				});
			}

			/**
			 * 点击刷新
			 */
			$("#refreshBtn2").click(function () {
				expressionTrend()
			});
		});
	</script>
</body>
</html>