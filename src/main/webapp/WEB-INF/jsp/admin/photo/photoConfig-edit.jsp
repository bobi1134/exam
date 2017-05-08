<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../jspf/head.jspf" %>
</head>
<body>
<article class="page-container">
	<form method="post" class="form form-horizontal" id="form-photo-config-add">
		<input type="hidden" name="id" value="${photoConfig.id}"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>开始时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'commentdatemax\')||\'%y-%M-%d\'}' })" id="commentdatemin" name="startTime" class="input-text Wdate" value="<fmt:formatDate value="${photoConfig.startTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/>">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>结束时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" onfocus="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'commentdatemin\')}' })" id="commentdatemax" name="endTime" class="input-text Wdate" value="<fmt:formatDate value="${photoConfig.endTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/>">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="添加描述信息" id="description" name="description" value="${photoConfig.description}">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<%@ include file="../jspf/footer.jspf" %>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/resources/admin/h-ui/lib/My97DatePicker/4.8/WdatePicker.js"></script>
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

	$("#form-photo-config-add").validate({
		rules:{
			startTime:{
				required:true,
			},
			endTime:{
				required:true,
			},
			description:{
				required:true,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				url:"${ctx}/admin/photoConfig/edit",
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