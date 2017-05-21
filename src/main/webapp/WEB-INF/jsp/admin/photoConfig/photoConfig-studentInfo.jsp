<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
	</head>
<body>
	<%--主体--%>
	<div class="page-container">
		<table class="table table-border table-bordered table-hover table-bg">
			<thead>
			<tr>
				<th scope="col" colspan="6">
					<span class="l">学生信息</span>
					<a class="btn btn-success radius r" style="line-height:1.6em;" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
				</th>
			</tr>
			<tr class="text-c">
				<th width="40">ID</th>
				<th width="200">用户名</th>
				<th width="200">邮箱</th>
				<th width="200">真实姓名</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${users}" var="c">
				<tr class="text-c">
					<td>${c.id}</td>
					<td>${c.username}</td>
					<td>${c.email}</td>
					<td>${c.reallyName}</td>
					<td class="f-14">
						<a title="分析" href="${ctx}/admin/photoConfigAnalysis/technicalSupport/${photoConfigId}/${c.id}" style="text-decoration:none">
							<i class="Hui-iconfont">&#xe61c;</i>
						</a>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>

	<!-- 自定义js -->
	<script type="text/javascript">
		//页面加载完修改标题
		parent.document.getElementsByClassName('layui-layer-title')[0].innerText="应考人";
	</script>
</body>
</html>