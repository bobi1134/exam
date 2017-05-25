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
		<input type="hidden" value="${systemWeb.id}" name="id"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>关键词：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemWeb.keywords}" placeholder="" id="keywords" name="keywords">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemWeb.description}" placeholder="" id="description" name="description">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">TAB标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemWeb.title}" placeholder="" id="title" name="title">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">版本：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemWeb.version}" placeholder="" id="version" name="version">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">类别：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${systemWeb.category}" placeholder="" id="category" name="category" disabled/>
			</div>
		</div>

		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius"><i class="icon-ok"></i> 确定</button>
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
				keywords:{
					required:true,
				},
				description:{
					required:true,
				},
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				//执行修改操作
				$(form).ajaxSubmit({
					url:"${ctx}/admin/systemWeb/edit",
					type:"post",
					dataType:"json",
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