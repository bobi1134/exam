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
				background: url('${ctx}/resources/admin/youtu/imgs/test.jpg') center no-repeat;
				background-size: auto 100%;
				position: relative;
				margin-left: 20px;
			}
			.main .left .image .face{
				width: 177px;
				height: 199px;
				position: absolute;
				top: 132px;
				left: 149px;
			}
			.main .left .image .face-line-1{
				position: absolute;
				opacity: 0.9;
				width: 25%;
				height:25%;
				left: 0;
				top: 0;
				border-left: #61E6BD 3px solid;
				border-top: #61E6BD 3px solid;
			}
			.main .left .image .face-line-2{
				position: absolute;
				opacity: 0.9;
				width: 25%;
				height:25%;
				right: 0;
				top: 0;
				border-right: #61E6BD 3px solid;
				border-top: #61E6BD 3px solid;
			}
			.main .left .image .face-line-3{
				position: absolute;
				opacity: 0.9;
				width: 25%;
				height:25%;
				left: 0;
				bottom: 0;
				border-left: #61E6BD 3px solid;
				border-bottom: #61E6BD 3px solid;
			}
			.main .left .image .face-line-4{
				position: absolute;
				opacity: 0.9;
				width: 25%;
				height:25%;
				right: 0;
				bottom: 0;
				border-right: #61E6BD 3px solid;
				border-bottom: #61E6BD 3px solid;
			}
			.main .left .image .face-label-box{
				position: absolute;
				right: 21px;
				top:133px;
			}
			.main .left .image .face-label-box{
				width: 102px;
				height: 116px;
			}
			.main .left .image .face-label-box div{
				height: 24px;
				margin-left: 4px;
				margin-bottom: 5px;
				background: url("${ctx}/resources/admin/youtu/imgs/info_bg2.png");
				position: relative;
			}
			.main .left .image .face-label-box div span{
				position: absolute;
				left: -6px;
				margin-left: 10px;
			}
			.main .left .image .face-label-box div:first-child{
				margin-left: 0px;
				background: url("${ctx}/resources/admin/youtu/imgs/info_bg.png");
			}
			.main .left .image .face-label-box div:first-child span{
				left: -1px;
				margin-left: 10px;
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 人脸识别技术 <span class="c-gray en">&gt;</span> 人脸检测 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<div class="main">
			<div class="left">
				<div class="image">
					<div class="face">
						<div class="face-line-1"></div>
						<div class="face-line-2"></div>
						<div class="face-line-3"></div>
						<div class="face-line-4"></div>
					</div>
					<div class="face-label-box">
						<div id="sex"><span>性别:女</span></div>
						<div id="age"><span>年龄:20</span></div>
						<div id="expression"><span>表情:笑逐颜开</span></div>
						<div id="beauty"><span>魅力:90</span></div>
					</div>
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
					beforeSend:function(){
						//隐藏原有信息
						$(".main .left .image .face").hide();
						$(".main .left .image .face-label-box").hide();
						$(".main .left .image").css("background-image","url("+$("#url").val()+")");
						//打开阴影层
						$(".main .left .image .yy").show();
					},
					url : "${ctx}/admin/youtu/detectface",
					type : "post",
					dataType :"json",
					data : {"url":$("#url").val()},
					success : function (json) {
						if(json.errorcode == 0){
							//打开并更改原有信息
							$(".main .left .image .face").show();
							$(".main .left .image .face-label-box").show();
							//关闭阴影层
							$(".main .left .image .yy").hide();

							/*******************************控制face框框的位置 start******************************/
							//获取face的高宽、坐标
							var x = json.face[0].x;
							var y = json.face[0].y;
							var height = json.face[0].height;
							var width = json.face[0].width;
							var image_width = json.image_width;
							var image_height = json.image_height;
							var max_len = Math.max(image_width, image_height);
							var canvasWidth = 480.0;
							var ratio = canvasWidth / max_len;
							var canvasXOffset = (canvasWidth - image_width * ratio) / 2.0;
							var canvasYOffset = (canvasWidth - image_height * ratio) / 2.0;

							var imgX = x * ratio;
							var imgY = y * ratio;
							var imgCanvasHeight = height * ratio;
							var imgCanvasWidth = width * ratio;
							var canvasX = canvasXOffset + imgX;
							var canvasY = canvasYOffset + imgY;

							//修改face的高宽、坐标
							$(".main .left .image .face").css({"left":canvasX,"top":canvasY,"height":imgCanvasHeight,"width":imgCanvasWidth});
							/*******************************控制face框框的位置 end******************************/

							//获取face的属性
							var expressionArray = ["黯然伤神", "半嗔半喜", "似笑非笑","笑逐颜开", "莞尔一笑", "喜上眉梢","眉开眼笑", "笑尽妖娆", "心花怒放", "一笑倾城"];
							var section = parseInt(json.face[0].expression / 10);
							var expression = section < expressionArray.length ? expressionArray[section] : expressionArray[expressionArray.length - 1];
							//修改face的属性
							$("#sex span").text("性别:"+(json.face[0].gender < 50 ? "女" : "男"));
							$("#age span").text("年龄:"+json.face[0].age);
							$("#expression span").text("表情:"+expression);
							$("#beauty span").text("魅力:"+json.face[0].beauty);
							//修改face属性位置
							$(".main .left .image .face-label-box").css({"left":canvasX+imgCanvasWidth+20,"top":canvasY});

						}else{
							$(".main .left .image .yy span").text(json.errormsg);
						}
						//在右边显示返回数据
						$(".main .right").JSONView(JSON.stringify(json));
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
					url: "${ctx}/admin/youtu/detectface_upload",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					beforeSend:function(){
						//隐藏face框框
						$(".main .left .image .face").hide();
						$(".main .left .image .face-label-box").hide();
						//打开阴影层
						$(".main .left .image .yy").show();
						//更换背景
						var fils = $("#fileUpload").get(0).files[0];
						var srcc = window.URL.createObjectURL(fils);
						$(".main .left .image").css("background-image","url("+srcc+")");
					},
				success: function(data) {
						if(data.errorcode == 0){
							//打开face框框
							$(".main .left .image .face").show();
							$(".main .left .image .face-label-box").show();
							//关闭阴影层
							$(".main .left .image .yy").hide();

							//获取face的高宽、坐标
							var x = data.face[0].x;
							var y = data.face[0].y;
							var height = data.face[0].height;
							var width = data.face[0].width;
							var image_width = data.image_width;
							var image_height = data.image_height;
							var max_len = Math.max(image_width, image_height);
							var canvasWidth = 480.0;
							var ratio = canvasWidth / max_len;
							var canvasXOffset = (canvasWidth - image_width * ratio) / 2.0;
							var canvasYOffset = (canvasWidth - image_height * ratio) / 2.0;
							var imgX = x * ratio;
							var imgY = y * ratio;
							var imgCanvasHeight = height * ratio;
							var imgCanvasWidth = width * ratio;
							var canvasX = canvasXOffset + imgX;
							var canvasY = canvasYOffset + imgY;
							//修改face的高宽、坐标
							$(".main .left .image .face").css({"left":canvasX,"top":canvasY,"height":imgCanvasHeight,"width":imgCanvasWidth});

							//获取face的属性
							var expressionArray = ["黯然伤神", "半嗔半喜", "似笑非笑","笑逐颜开", "莞尔一笑", "喜上眉梢","眉开眼笑", "笑尽妖娆", "心花怒放", "一笑倾城"];
							var section = parseInt(data.face[0].expression / 10);
							var expression = section < expressionArray.length ? expressionArray[section] : expressionArray[expressionArray.length - 1];
							//修改face的属性
							$("#sex span").text("性别:"+(data.face[0].gender < 50 ? "女" : "男"));
							$("#age span").text("年龄:"+data.face[0].age);
							$("#expression span").text("表情:"+expression);
							$("#beauty span").text("魅力:"+data.face[0].beauty);
							//修改face属性位置
							$(".main .left .image .face-label-box").css({"left":canvasX+imgCanvasWidth+20,"top":canvasY});
						}else{
							$(".main .left .image .yy span").text(data.errormsg);
						}
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