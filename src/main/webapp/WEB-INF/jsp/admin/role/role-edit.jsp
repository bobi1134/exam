<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-admin-role-add">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${role.roleName}" placeholder="" id="roleName" name="roleName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${role.description}" placeholder="" id="" name="">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">网站角色：</label>
			<div class="formControls col-xs-8 col-sm-9">

				<c:set var="parentIds" value="${parentIds}"/>
				<c:if test="${fn:contains(parentIds, '1')}">
					<%--会员管理--%>
					<dl class="permission-list">
						<dt>
							<label>会员管理</label>
						</dt>
						<dd>
							<dl class="cl permission-list2">
								<dd>
									<c:forEach items="${allPermissions}" var="c">
										<c:if test="${c.parentId==1}">
											<c:choose>
												<c:when test="${c.flag==true}">
													<label class=""><input type="checkbox" value="" name="user-Character-0-0-0" id="user-Character-0-0-0" checked="checked">${c.permissionName}</label>
												</c:when>
												<c:otherwise>
													<label class=""><input type="checkbox" value="" name="user-Character-0-0-0" id="user-Character-0-0-0">${c.permissionName}</label>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>
								</dd>
							</dl>
						</dd>
					</dl>
				</c:if>



				<%--角色权限管理--%>
				<c:if test="${fn:contains(parentIds, '2')}">
					<dl class="permission-list">
						<dt>
							<label>角色权限管理</label>
						</dt>
						<dd>
							<dl class="cl permission-list2">
								<dd>
									<c:forEach items="${allPermissions}" var="c">
										<c:if test="${c.parentId==2}">
											<c:choose>
												<c:when test="${c.flag==true}">
													<label class=""><input type="checkbox" value="" name="user-Character-0-0-0" id="user-Character-0-0-0" checked="checked">${c.permissionName}</label>
												</c:when>
												<c:otherwise>
													<label class=""><input type="checkbox" value="" name="user-Character-0-0-0" id="user-Character-0-0-0">${c.permissionName}</label>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>
								</dd>
							</dl>
						</dd>
					</dl>
				</c:if>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius" id="admin-role-save" name="admin-role-save"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<%@ include file="../jspf/footer.jspf" %>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$(".permission-list2 dd input:checkbox").click(function(){
		var l =$(this).parent().parent().find("input:checked").length;
		var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).closest("dl").find("dt input:checkbox").prop("checked",true);
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}
		else{
			if(l==0){
				$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
			}
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
	});
	
	$("#form-admin-role-add").validate({
		rules:{
			roleName:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit();
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>