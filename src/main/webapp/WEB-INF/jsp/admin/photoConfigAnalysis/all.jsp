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
		<a class="btn btn-secondary radius l" href="${ctx}/admin/photoConfig/studentInfo/${photoConfig.id}">返回上一级</a>
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px;margin-right: 14px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</div>
	<!-- 主体 -->
	<div class="page-container">
		<!-- 面部表情分布图 -->
		<div id="expressionDistribution" style="min-width:700px;height:400px;margin-top:50px;"></div>
		<!-- 文字分析 -->
		<span class="t"><i class="Hui-iconfont">&#xe68e;</i>  该堂考试的考生的面部变化</span></br>
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黯然伤神${expressionCountArray[0]}次，半嗔半喜 ${expressionCountArray[1]}次，似笑非笑 ${expressionCountArray[2]}次，笑逐颜开 ${expressionCountArray[3]}次，莞尔一笑 ${expressionCountArray[4]}次，喜上眉梢${expressionCountArray[5]}次，眉开眼笑 ${expressionCountArray[6]}次，笑尽妖娆 ${expressionCountArray[7]}次，心花怒放 ${expressionCountArray[8]}次，一笑倾城 ${expressionCountArray[9]}次！</span></br>
		<c:set var="orderOne" value="${expressionCountArray[0]}"/>
		<c:set var="orderTwo" value="${expressionCountArray[1]+expressionCountArray[2]}"/>
		<c:set var="orderThree" value="${expressionCountArray[3]+expressionCountArray[4]}"/>
		<c:set var="orderFour" value="${expressionCountArray[5]+expressionCountArray[6]}"/>
		<c:set var="orderFive" value="${expressionCountArray[7]+expressionCountArray[8]+expressionCountArray[9]}"/>
		<c:choose>
			<c:when test="${orderOne>orderTwo and orderOne>orderThree and orderOne>orderFour and orderOne>orderFive}">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该堂考试的考生大多数处于黯然神伤的表情，看来题有点难哟！</span></br>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;老师你可以适当的减轻一点实体难易程度哟！</span>
			</c:when>
			<c:when test="${orderTwo>orderOne and orderTwo>orderThree and orderTwo>orderFour and orderTwo>orderFive}">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该堂考试的考生大多数处于半嗔半喜和似笑非笑的表情，看来本次试题对他来说还算将就，可以慢慢思考解决，再拿个好成绩！</span></br>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看来老师您这个出的试题出的很好哟，让学生动脑袋了！</span>
			</c:when>
			<c:when test="${orderThree>orderOne and orderThree>orderTwo and orderThree>orderFour and orderThree>orderFive}">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该堂考试的考生大多数处于笑逐颜开和莞尔一笑的表情，看来本次试题对于他来说是很有把握的啦，自信的人总是最美的！</span></br>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看来试题出的比较轻松，学生学的也比较好，赞，老师！</span>
			</c:when>
			<c:when test="${orderFour>orderOne and orderFour>orderTwo and orderFour>orderThree and orderFour>orderFive}">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该堂考试的考生大多数处于喜上眉梢和眉开眼笑的表情，看是复习的非常好了，如果最后成绩很差，请带他谈一次心！</span></br>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;老师，大部分学生都很开心也，试题对于他们来说很轻松哟！</span>
			</c:when>
			<c:when test="${orderFive>orderOne and orderFive>orderTwo and orderFive>orderThree and orderFive>orderFour}">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该堂考试的考生大多数处于笑的不得了的状态，肯定是遇到很多原题+复习了+加会做题，学霸一枚，鉴定完毕！</span></br>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不说了，老师，你出的题太简单了，90%以上的学生都会做，666！</span>
			</c:when>
		</c:choose>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts-3d.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {
			//修改名称
			parent.document.getElementsByClassName('layui-layer-title')[0].innerText="汇总分析 - ${photoConfig.description}";

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
		});

	</script>
</body>
</html>