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
			/** main left & right  */
			.main .left, .main .right{
				width: 45%;
				height: 600px;
				float: left;
			}
			.main .left{
				margin-left: 2.5%;
			}
			.main .right{
				margin-left: 5%;
			}
			.main .left .image, .main .right .image{
				height: 500px;
				border: #d2d2de 1px solid;
				background: url('${ctx}/resources/admin/youtu/detectface/css_imgs/test.jpg') center no-repeat;
				background-size: auto 100%;
			}
			.main .left .upload .fileUploadBtn, .main .right .upload .fileUploadBtn{
				width: 100%;
				height: 40px;
				background: #3688ff;
				font-size: 16px;
				color: #fff;
				text-align: center;
				line-height: 40px;
				margin-top:9px;
				cursor: pointer;
			}
			.main .left .url, .main .right .url{
				width: 100%;
				height: 40px;
				margin-top:9px;
			}
			.main .left .url input, .main .right .url input{
				height: 38px;
				width: 80%;
				float: left;
				border: 1px solid #b3b3b3;
				border-right: 0px;
			}
			.main .left .url .urlBtn, .main .right .url .urlBtn{
				height: 40px;
				width: 19.8%;
				background: #3688ff;
				cursor: pointer;
				color: #fff;
				float: left;
				text-align: center;
				line-height: 40px;
				font-size: 16px;
			}
			/** main operate */
			.main .operate{
				height: 40px;
				width: 95%;
				background: #65efc4;
				margin-top: 9px;
				margin-left: 2.5%;
				line-height: 40px;
				text-align: center;
				font-size: 20px;
				color: #fff;
				cursor: pointer;
				float: left;
			}
			/** main result */
			.main .result{
				height: 140px;
				width: 95%;
				margin-top: 9px;
				margin-left: 2.5%;
				text-align: center;
				border: #d2d2d2 1px solid;
				float: left;
			}
			.main .result p{
				margin-top: 25px;
				color: #17EDA9;
				font-size: 16px;
			}
			.main .result div{
				width: 100%;
				text-align: center;
				margin-top: 10px;
				color: #17EDA9;
				font-size: 36px;
			}
			/** main request & response */
			.main .request, .main .response{
				height: 140px;
				width: 95%;
				margin-top: 9px;
				margin-left: 2.5%;
				border: #d2d2d2 1px solid;
				overflow: auto;
				float: left;
			}
			.main .request .req-title, .main .response .res-title{
				font-size: 16px;
				color: #207BEC;
				margin-top: 22px;
				margin-left: 18px;
			}
		</style>
	</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 人脸识别技术 <span class="c-gray en">&gt;</span> 人脸对比 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<div class="main">
			<div class="left">
				<!-- image区域 -->
				<div class="image">

				</div>
				<!-- 上传图片按钮区域 -->
				<div class="upload">
					<form role="form" id="fileUploadForm1" enctype="multipart/form-data">
						<input type="file" name="photo" id="fileUpload1" style="display:none"/>
						<div class="fileUploadBtn">上传本地图片</div>
					</form>
				</div>
				<!-- 自定义url按钮区域 -->
				<div class="url">
					<input type="text" id="url1" value="http://open.youtu.qq.com/content/img/product/face/face_14.jpg"/>
					<div class="urlBtn" id="urlBtn1">分析</div>
				</div>
			</div>
			<div class="right">
				<!-- image区域 -->
				<div class="image">

				</div>
				<!-- 上传图片按钮区域 -->
				<div class="upload">
					<form role="form" id="fileUploadForm2" enctype="multipart/form-data">
						<input type="file" name="photo" id="fileUpload2" style="display:none"/>
						<div class="fileUploadBtn">上传本地图片</div>
					</form>
				</div>
				<!-- 自定义url按钮区域 -->
				<div class="url">
					<input type="text" id="url2" value="http://open.youtu.qq.com/content/img/product/face/face_14.jpg"/>
					<div class="urlBtn" id="urlBtn2">分析</div>
				</div>
			</div>
			<div class="operate">人脸对比</div>
			<div class="result">
				<p align="center">The similarity between the two faces is</p>
				<div>12%</div>
			</div>
			<div class="request">
				<div class="req-title">REQUEST:</div>
				<div>
					{<br/>
					"urlA": "http://jiaowu.sicau.edu.cn/photo/20140151.jpg",<br/>
					"urlB": "http://jiaowu.sicau.edu.cn/photo/20140152.jpg",<br/>
					"app_id": "10009633"<br/>
					}
				</div>
			</div>
			<div class="response">
				<div class="res-title">RESPONSE:</div>
				<div>
					{<br/>
					"urlA": "http://jiaowu.sicau.edu.cn/photo/20140151.jpg",<br/>
					"urlB": "http://jiaowu.sicau.edu.cn/photo/20140152.jpg",<br/>
					"app_id": "10009633"<br/>
					}
				</div>
			</div>
		</div>
	</div>

	<!-- 模板js -->
	<%@ include file="../jspf/footer.jspf" %>
	<!-- 自定义js -->
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/jsonview/jquery.jsonview.js"></script>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/jquery.form.min.js"></script>
	<script type="text/javascript">
		$(function () {

		});
	</script>
</body>
</html>