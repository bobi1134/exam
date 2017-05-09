<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/admin/h-ui/lib/lightbox2/2.8.1/css/lightbox.css"/>
	</head>
<body>
	<div class="page-container">
		<div class="portfolio-content">
			<ul class="cl portfolio-area">
				<c:forEach items="${photos}" var="c">
					<li class="item">
						<div class="portfoliobox">
							<input class="checkbox" name="" type="checkbox" value="">
							<div class="picbox">
								<a href="${ctx}/resources/admin/upload/photo/${c.name}" data-lightbox="gallery" data-title="<fmt:formatDate value="${c.createTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/>">
									<!-- 缩略图 -->
									<img src="${ctx}/resources/admin/upload/photo/${c.name}">
								</a>
							</div>
							<div class="textbox"><fmt:formatDate value="${c.createTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/></div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/lightbox2/2.8.1/js/lightbox.min.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript">
		$(function(){
			$.Huihover(".portfolio-area li");
		});
	</script>
</body>
</html>