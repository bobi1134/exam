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
		<input type="hidden" value="${role.id}" id="roleId"/>
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

				<%--会员管理--%>
				<dl class="permission-list" id="hygl">
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
												<label class=""><input type="checkbox" value="${c.id}" checked>${c.permissionName}</label>
											</c:when>
											<c:otherwise>
												<label class=""><input type="checkbox" value="${c.id}">${c.permissionName}</label>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</dd>
						</dl>
					</dd>
				</dl>

				<%--角色权限管理--%>
				<dl class="permission-list" id="jsqxgl">
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
												<label class=""><input type="checkbox" value="${c.id}" checked>${c.permissionName}</label>
											</c:when>
											<c:otherwise>
												<label class=""><input type="checkbox" value="${c.id}">${c.permissionName}</label>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</dd>
						</dl>
					</dd>
				</dl>

				<%--人脸识别技术--%>
				<dl class="permission-list" id="rlsbjs">
					<dt>
						<label>人脸识别技术</label>
					</dt>
					<dd>
						<dl class="cl permission-list2">
							<dd>
								<c:forEach items="${allPermissions}" var="c">
									<c:if test="${c.parentId==3}">
										<c:choose>
											<c:when test="${c.flag==true}">
												<label class=""><input type="checkbox" value="${c.id}" checked>${c.permissionName}</label>
											</c:when>
											<c:otherwise>
												<label class=""><input type="checkbox" value="${c.id}">${c.permissionName}</label>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</dd>
						</dl>
					</dd>
				</dl>
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
				var permissionIds = "";
				//会员管理
				$("#hygl dd .cl dd label").each(function () {
					var input = $(this).find("input");
					if (input.is(":checked")) {
						permissionIds = permissionIds + input.val() + ",";
					}
				});
				//角色权限管理
				$("#jsqxgl dd .cl dd label").each(function () {
					var input = $(this).find("input");
					if (input.is(":checked")) {
						permissionIds = permissionIds + input.val() + ",";
					}
				});
				//角色权限管理
				$("#rlsbjs dd .cl dd label").each(function () {
					var input = $(this).find("input");
					if (input.is(":checked")) {
						permissionIds = permissionIds + input.val() + ",";
					}
				});
				console.log("permissionIds:"+permissionIds);
				//执行修改操作
				$(form).ajaxSubmit({
					url:"${ctx}/admin/role/edit",
					type:"post",
					dataType:"json",
					data : {"permissionIds":permissionIds, "roleId":$("#roleId").val()},
					success:function (json) {
						if(json == true){
							layer.msg('修改成功!',{icon:1,time:1000}, function () {
								window.parent.location.reload(); //刷新父页面
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);  // 关闭layer
							});
						}else{
							layer.msg('修改失败！',{icon:5,time:1000});
						}

					},
					error:function () {
						layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
					}
				});
			}
		});
	});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>