<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
	</head>
	<style type="text/css">
		.page-container{
			min-height: 800px;
		}
		.charts{
			overflow: auto;
			margin-top: 50px;
		}
		.charts #chart1, #chart2, #chart3{
			width: 33.3%;
			height:300px;
			float: left;
		}
		.advice {
			margin-top: 20px;
			overflow: auto;
		}
		.advice .all{
			display: none;
			overflow: hidden;
		}
		.advice .all .title{
			font-size: 18px;
			font-weight: bold;
		}
		.advice .all .text .t{
			font-weight: bold;
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
		<!-- 图表信息 -->
		<div class="charts">
			<div id="chart1"></div>
			<div id="chart2"></div>
			<div id="chart3"></div>
		</div>

		<!-- 意见分析 -->
		<div class="advice">
			<div class="btn btn-primary radius" style="display: block;" id="resultBtn">点我开始得出文字结论</div>
			<div class="all">
				<div class="title">小编通过大数据分析得出以下结论：</div>
				<div class="text">
					<!-- A -->
					<span class="t"><i class="Hui-iconfont">&#xe68e;</i>  是否佩戴眼镜</span></br>
					<c:choose>
						<c:when test="${glass==true}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生戴了眼镜，是个近视眼儿！</span>
						</c:when>
						<c:when test="${glass==false}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生没戴眼镜，视力正常，666！</span>
						</c:when>
					</c:choose>

					</br>

					<!-- B -->
					<span class="t"><i class="Hui-iconfont">&#xe68e;</i>  该生的面部变化</span></br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黯然伤神${expressions[0]}次，半嗔半喜 ${expressions[1]}次，似笑非笑 ${expressions[2]}次，笑逐颜开 ${expressions[3]}次，莞尔一笑 ${expressions[4]}次，喜上眉梢${expressions[5]}次，眉开眼笑 ${expressions[6]}次，笑尽妖娆 ${expressions[7]}次，心花怒放 ${expressions[8]}次，一笑倾城 ${expressions[9]}次！</span></br>
					<c:set var="orderOne" value="${expressions[0]}"/>
					<c:set var="orderTwo" value="${expressions[1]+expressions[2]}"/>
					<c:set var="orderThree" value="${expressions[3]+expressions[4]}"/>
					<c:set var="orderFour" value="${expressions[5]+expressions[6]}"/>
					<c:set var="orderFive" value="${expressions[7]+expressions[8]+expressions[9]}"/>
					<c:choose>
						<c:when test="${orderOne>orderTwo and orderOne>orderThree and orderOne>orderFour and orderOne>orderFive}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生大多数处于黯然神伤的表情，看来题有点难哟！</span>
						</c:when>
						<c:when test="${orderTwo>orderOne and orderTwo>orderThree and orderTwo>orderFour and orderTwo>orderFive}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生大多数处于半嗔半喜和似笑非笑的表情，看来本次试题对他来说还算将就，可以慢慢思考解决，再拿个好成绩！</span>
						</c:when>
						<c:when test="${orderThree>orderOne and orderThree>orderTwo and orderThree>orderFour and orderThree>orderFive}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生大多数处于笑逐颜开和莞尔一笑的表情，看来本次试题对于他来说是很有把握的啦，自信的人总是最美的！</span>
						</c:when>
						<c:when test="${orderFour>orderOne and orderFour>orderTwo and orderFour>orderThree and orderFour>orderFive}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生大多数处于喜上眉梢和眉开眼笑的表情，看是复习的非常好了，如果最后成绩很差，请带他谈一次心！</span>
						</c:when>
						<c:when test="${orderFive>orderOne and orderFive>orderTwo and orderFive>orderThree and orderFive>orderFour}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生大多数处于笑的不得了的状态，肯定是遇到很多原题+复习了+加会做题，学霸一枚，鉴定完毕！</span>
						</c:when>
					</c:choose>

					</br>

					<!-- C -->
					<span class="t"><i class="Hui-iconfont">&#xe68e;</i>  该生的转向情况变化</span></br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位于左边${turnOrder[0]}次，位于中间 ${turnOrder[1]}次，位于右边${turnOrder[2]}次！</span></br>
					<c:choose>
						<c:when test="${turnOrder[0]>(turnOrder[1]+turnOrder[2])}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该考生有点不正常，头部位置在考试期间大都数位于左边！</span>
						</c:when>
						<c:when test="${turnOrder[1]>(turnOrder[0]+turnOrder[2])}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该考生挺老实的，大多数时间位于屏幕中央！</span>
						</c:when>
						<c:when test="${turnOrder[0]>(turnOrder[1]+turnOrder[2])}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该考生有点不正常，头部位置在考试期间大都数位于右边！</span>
						</c:when>
					</c:choose>

					</br>

					<!-- D -->
					<span class="t"><i class="Hui-iconfont">&#xe68e;</i>  该生的中途换人情况变化</span></br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该生的相似度比较大于85的情况下有${changePeopleOrder[0]}张，相似度位于60-85之间有${changePeopleOrder[1]}张，小于60有${changePeopleOrder[2]}张！</span></br>
					<c:choose>
						<c:when test="${changePeopleOrder[0]>changePeopleOrder[1] and changePeopleOrder[2]==0}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该考生高度相似，完全是同一个人，不用担心！</span></br>
						</c:when>
						<c:when test="${changePeopleOrder[1]>changePeopleOrder[0] and changePeopleOrder[2]==0}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该考生应该是同一个人，不用担心，嗯哈！</span></br>
						</c:when>
						<c:when test="${changePeopleOrder[2]>0}">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;警告，该生有换人嫌疑！</span></br>
						</c:when>
					</c:choose>

					<!-- E -->
					<span class="t"><i class="Hui-iconfont">&#xe68e;</i>  该生的其他情况</span></br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：${faceArr[0]}</span></br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年龄：${faceArr[1]}</span></br>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;魅力：${faceArr[2]}</span></br>
				</div>
			</div>
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function () {
			/**
			 * 面部表情走势图
			 */
			chart1();
			function chart1() {
				var faceStr = "${photoConfigAnalysis.face}";
				var strArr = faceStr.split(",");
				var expressionArr = new Array();
				for(var i=0; i<strArr.length; i++){
					expressionArr[i] = parseInt(strArr[i]);
				}
				Highcharts.chart('chart1', {
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
						data: expressionArr
					}]
				});
			}

			/**
			 * 头部转向走势图
			 */
			chart2();
			function chart2() {
				var turnAroundStr = "${photoConfigAnalysis.turnAround}";
				var strArr = turnAroundStr.split(",");
				var turnAroundArr = new Array();
				for(var i=0; i<strArr.length; i++){
					turnAroundArr[i] = parseInt(strArr[i]);
				}
				Highcharts.chart('chart2', {
					title: {
						text: '头部转向走势图',
						x: -20 //center
					},
					subtitle: {
						text: 'Source: http://www.xlbweb.com',
						x: -20
					},
					yAxis: {
						title: {
							text: 'Turn around'
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
						name: '转向角度：',
						data: turnAroundArr
					}]
				});
			}

			/**
			 * 人脸对比走势图
			 */
			chart3();
			function chart3() {
				var changePeopleStr = "${photoConfigAnalysis.changePeople}";
				var strArr = changePeopleStr.split(",");
				var changePeopleArr = new Array();
				for(var i=0; i<strArr.length; i++){
					changePeopleArr[i] = parseInt(strArr[i]);
				}
				Highcharts.chart('chart3', {
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
							text: 'Turn around'
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
						data: changePeopleArr
					}]
				});
			}

			var flag = false;
			$("#resultBtn").click(function(){
				if(!flag){
					$(".advice .all").removeClass("hui-fadeout").addClass("hui-fadeinT").show();
					flag = true;
				}else{
					$(".advice .all").removeClass("hui-fadeinT").addClass("hui-fadeout");
					flag = false;
				}
			});
		});
	</script>
</body>
</html>