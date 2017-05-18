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
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>

	<!-- 自定义js -->
	<script type="text/javascript">
	</script>
</body>
</html>