<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/admin/plug-in/jsonview/jquery.jsonview.css"/>
		<style type="text/css">
			.main{
				overflow: auto;
			}
			/*main left*/
			.main .left{
				width: 50%;
				float: left;
			}
			/*main right*/
			.main .right{
				width: 45%;
				float: right;
				border: #d2d2de 1px solid;
			}
			/*main left image*/
			.main .left .image{
				height: 485px;
				width: 485px;
				border: #d2d2de 1px solid;
				background: url('${ctx}/resources/admin/youtu/detectface/css_imgs/test.jpg') center no-repeat;
				background-size: auto 100%;
				position: relative;
				margin-left: 20px;
			}

			/*main left image item*/
			.main .left .image .item{
				position: absolute;
				width: 3px;
				height: 3px;
				border-radius: 50%;
				background: #5fffcc;
				opacity: 0.9;
			}
			/*main left btn*/
			.main .left .upload .fileUploadBtn{
				width: 486px;
				height: 40px;
				background: #3688ff;
				font-size: 16px;
				color: #fff;
				text-align: center;
				line-height: 40px;
				margin-top:10px;
				margin-left: 21px;
				cursor: pointer;
			}
			.main .left .url{
				width: 486px;
				height: 40px;
				margin-top:10px;
				margin-left: 21px;
			}
			.main .left .url input{
				height: 38px;
				width: 346px;
				float: left;
				border: 1px solid #b3b3b3;
			}
			.main .left .url .urlBtn{
				height: 40px;
				width: 138px;
				background: #3688ff;
				cursor: pointer;
				color: #fff;
				float: left;
				text-align: center;
				line-height: 40px;
				font-size: 16px;
			}
			.main .left .image .yy{
				width: 100%;
				height: 100%;
				background: #000;
				opacity: 0.3;
				display: none;
				text-align: center;
				line-height: 485px;
			}
			.main .left .image .yy span{
				color: #fff;
			}
		</style>
	</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 人脸识别技术 <span class="c-gray en">&gt;</span> 五官定位 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<div class="main">
			<div class="left">
				<div class="image">
					<div class="item"></div>
					<div class="yy">
						<span>请稍后...</span>
					</div>
				</div>
				<div class="upload">
					<form role="form" id="fileUploadForm" enctype="multipart/form-data">
						<input type="file" name="photo" id="fileUpload" style="display:none"/>
						<div class="fileUploadBtn">上传本地图片</div>
					</form>
				</div>
				<div class="url">
					<input type="text" id="url" value="http://open.youtu.qq.com/content/img/product/face/face_14.jpg"/>
					<div class="urlBtn" id="urlBtn">分析</div>
				</div>
			</div>
			<div class="right">Result JSON</div>
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/jsonview/jquery.jsonview.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/jquery.form.min.js"></script>
	<script type="text/javascript">
		$(function () {
			//url解析
			$("#urlBtn").click(function () {
				$.ajax({
					url : "${ctx}/admin/youtu/faceshape",
					type : "post",
					dataType :"json",
					data : {"url":$("#url").val()},
					beforeSend:function(){
						//删除item节点
						$(".main .left .image .item").remove();
						//更改url
						$(".main .left .image").css("background-image","url("+$("#url").val()+")");
						//打开阴影层
						$(".main .left .image .yy").show();
					},
					success : function (data) {
						if(data.errorcode == 0){
							//关闭阴影层
							$(".main .left .image .yy").hide();

							//获取无关定位位置
							var max_len = Math.max(data.image_width, data.image_height);
							var canvasWidth = 485.0;
							var scale = canvasWidth / max_len;
							var canvasXOffset = (canvasWidth - data.image_width*scale) / 2.0;
							var canvasYOffset = (canvasWidth - data.image_height*scale) / 2.0;

							var landmark = new Array();
							var shape = data.face_shape[0];
							landmark = landmark.concat(shape.face_profile, shape.left_eye, shape.left_eyebrow, shape.mouth, shape.nose, shape.right_eye, shape.right_eyebrow);

							for (var i = 0; i < landmark.length; ++i) {
								var x = parseInt(scale*landmark[i].x);
								var y = parseInt(scale*landmark[i].y);
								x += canvasXOffset;
								y += canvasYOffset;
								//追加item内容
								$(".main .left .image").prepend("<div class='item' style='top:"+y+"px;left:"+x+"px'></div>");
							}
						}else{
							$(".main .left .image .yy span").text(data.errormsg);
						}
						//在右边显示返回数据
						$(".main .right").JSONView(JSON.stringify(data));
					},
					error : function () {
						layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
					}
				});
			});

			//上传图片解析
			$(".main .left .upload .fileUploadBtn").click(function () {
				$("#fileUpload").click();
			});

			$("#fileUpload").on("change", function () {
				//开始上传
				$("#fileUploadForm").ajaxSubmit({
					type: 'post',
					url: "${ctx}/admin/youtu/faceshape_upload",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					beforeSend:function(){
						//删除item节点
						$(".main .left .image .item").remove();
						//打开阴影层
						$(".main .left .image .yy").show();
						//更换背景
						var fils = $("#fileUpload").get(0).files[0];
						var srcc = window.URL.createObjectURL(fils);
						$(".main .left .image").css("background-image","url("+srcc+")");
					},
					success: function(data) {
						if(data.errorcode == 0){
							//关闭阴影层
							$(".main .left .image .yy").hide();

							//获取无关定位位置
							var max_len = Math.max(data.image_width, data.image_height);
							var canvasWidth = 485.0;
							var scale = canvasWidth / max_len;
							var canvasXOffset = (canvasWidth - data.image_width*scale) / 2.0;
							var canvasYOffset = (canvasWidth - data.image_height*scale) / 2.0;

							var landmark = new Array();
							var shape = data.face_shape[0];
							landmark = landmark.concat(shape.face_profile, shape.left_eye, shape.left_eyebrow, shape.mouth, shape.nose, shape.right_eye, shape.right_eyebrow);

							for (var i = 0; i < landmark.length; ++i) {
								var x = parseInt(scale*landmark[i].x);
								var y = parseInt(scale*landmark[i].y);
								x += canvasXOffset;
								y += canvasYOffset;
								//追加item内容
								$(".main .left .image").prepend("<div class='item' style='top:"+y+"px;left:"+x+"px'></div>");
							}
						}else{
							$(".main .left .image .yy span").text(data.errormsg);
						}
						//在右边显示返回数据
						$(".main .right").JSONView(JSON.stringify(data));
					},
					error:function () {
						layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
					}
				});
			})
		});
	</script>
</body>
</html>