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
		000000000000000000000000
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/hcharts/Highcharts/5.0.6/js/modules/exporting.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		parent.document.getElementsByClassName('layui-layer-title')[0].innerText="汇总分析 - ${photoConfig.description}";
	</script>
</body>
</html>