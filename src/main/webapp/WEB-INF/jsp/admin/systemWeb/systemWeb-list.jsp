<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统设置 <span class="c-gray en">&gt;</span> 网站设置 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="7">网站设置</th>
			</tr>
			<tr class="text-c">
				<th>ID</th>
				<th>关键词</th>
				<th>描述</th>
				<th>后台管理标题</th>
				<th>版本</th>
				<th>类别</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${systemWebList}" var="c">
				<tr class="text-c">
					<td>${c.id}</td>
					<td>${c.keywords}</td>
					<td>${c.description}</td>
					<td>${c.title}</td>
					<td>${c.version}</td>
					<td>
						<c:if test="${c.category==1}">前台网页设置</c:if>
						<c:if test="${c.category==2}">后台网页设置</c:if>
					</td>
					<td class="f-14">
						<a title="编辑" href="javascript:;" onclick="systemWeb_edit('网站设置编辑','${ctx}/admin/systemWeb/edit/${c.id}')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript">
		/**
		 * 网站设置-编辑
		 * @param title
         * @param url
         */
		function systemWeb_edit(title,url){
			layer_show(title,url);
		}
	</script>
</body>
</html>