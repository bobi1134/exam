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
			top:653px;
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
		<!-- 中途换人分布图 -->
		<div id="changePeopleDistribution" style="min-width:700px;height:400px;margin-top:50px;"></div>
		<div class="btn btn-success radius" id="refreshBtn1" title="重绘图表"><i class="Hui-iconfont">&#xe68f;</i></div>
		<div style="margin-top: 30px">
			<span style="font-weight: bold;">提示：</span><br/>
			<span>~ 相似度大于70则认为是同一人，否则为不是。</span><br/>
		</div>

		<!-- 中途换人走势图 -->
		<div id="changePeopleTrend" style="min-width:700px;height:400px;margin-top:50px;"></div>
		<div class="btn btn-success radius" id="refreshBtn2" title="重绘图表"><i class="Hui-iconfont">&#xe68f;</i></div>
		<div style="margin-top: 30px">
			<span style="font-weight: bold;">提示：</span><br/>
			<span>~ 中途换人分析指的是以考生第一张识别成功的照片为基础，后面的一次进行比较，分析是否为同一个人！</span><br/>
			<span>~ 相似度区间为(0-100)。</span><br/>
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
			changePeopleDistribution();
			/**
			 * 中途换人分布图
			 */
			function changePeopleDistribution() {
				var count = ${count};//照片总数
				var selfNum = ${selfNum};//属于自己的数量
				$('#changePeopleDistribution').highcharts({
					chart: {
						type: 'pie',
						options3d: {
							enabled: true,
							alpha: 45,
							beta: 0
						}
					},
					title: {
						text: '中途换人分析'
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
							{
								name: '安全，未换人',
								y: selfNum/count * 100,
								sliced: true,
								selected: true
							},
							['危险，不是一个人', ((count-selfNum)/count) * 100]
						]
					}]
				});
			}

			/**
			 * 点击刷新
			 */
			$("#refreshBtn1").click(function () {
				changePeopleDistribution()
			});

			changePeopleTrend();
			/**
			 * 中途换人走势图
			 */
			function changePeopleTrend() {
				var similaritys = ${similaritys};//相似度集合
				Highcharts.chart('changePeopleTrend', {
					title: {
						text: '人脸对比走势图',
						x: -20 //center
					},
					subtitle: {
						text: 'Source: http://www.xlbweb.com',
						x: -20
					},
					yAxis: {
						title: {
							text: 'Face contrast'
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
						name: '相似度：',
						data: similaritys
					}]
				});
			}

			/**
			 * 点击刷新
			 */
			$("#refreshBtn2").click(function () {
				changePeopleTrend()
			});
		});
	</script>
</body>
</html>