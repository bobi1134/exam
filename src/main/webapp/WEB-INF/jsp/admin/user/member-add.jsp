<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
	</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
		<!-- 用户名 -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="username" name="username">
			</div>
		</div>

		<!-- 密码 -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="pwd" name="pwd">
			</div>
		</div>

		<!-- 邮件 -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>邮件：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="email" name="email">
			</div>
		</div>

		<!-- 邮件 -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="" id="reallyName" name="reallyName">
			</div>
		</div>

		<!-- 角色 -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">角色：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
					<select class="select" size="1" name="roleId">
						<option value="2">web_manager</option>
						<option value="3" selected>web_user</option>
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>

	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript">
	$(function(){
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});

		$("#form-member-add").validate({
			rules:{
				username:{
					required:true,
				},
				pwd:{
					required:true,
				},
				email:{
					required:true,
				},
				reallyName:{
					required:true,
				},
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(form).ajaxSubmit({
					url:"${ctx}/admin/user/add",
					type:"post",
					dataType:"json",
					data : $("#form-member-add").serialize(),
					success:function (json) {
						if(json == true){
							layer.msg('添加成功!',{icon:1,time:1000}, function () {
								window.parent.location.reload(); //刷新父页面
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);  // 关闭layer

							});
						}else{
							layer.msg('添加失败！',{icon:5,time:1000});
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
</body>
</html>