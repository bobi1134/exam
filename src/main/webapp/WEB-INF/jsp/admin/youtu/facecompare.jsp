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
				background: url('${ctx}/resources/admin/static/youtu/test.jpg') center no-repeat;
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
				position: relative;
			}
			.main .result p{
				margin-top: 25px;
				color: #17EDA9;
				font-size: 16px;
			}
			.main .result .similarity{
				width: 100%;
				text-align: center;
				margin-top: 10px;
				color: #17EDA9;
				font-size: 36px;
			}
			.main .result .yy{
				position: absolute;
				width: 100%;
				height: 100%;
				top: 0;
				line-height: 140px;
				color: #fff;
				opacity: 0.1;
				background: #000;
				display: none;
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
				<div class="similarity">12%</div>
				<div class="yy">请稍后...</div>
			</div>
			<div class="request">
				<div class="req-title">REQUEST:</div>
				<div class="req-result">
				</div>
			</div>
			<div class="response">
				<div class="res-title">RESPONSE:</div>
				<div class="res-result">
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

			//初始化获取
			var url1 = $("#url1").val();
			var url2 = $("#url2").val();

			var path1 = "";
			var path2 = "";

			// 修改背景
			$("#urlBtn1").click(function () {
				url1 = $("#url1").val();
				$(".main .left .image").css("background-image","url("+url1+")");
				//清除fileUpload1
				path1 = "";
			});

			// 修改背景
			$("#urlBtn2").click(function () {
				url2 = $("#url2").val();
				$(".main .right .image").css("background-image","url("+url2+")");
				//清除fileUpload2
				$("#fileUpload2").val("");
				path2 = "";
			});

			//打开file文件选择框
			$(".main .left .upload .fileUploadBtn").click(function () {
				$("#fileUpload1").click();
			});

			// 上传图片文件1
			$("#fileUpload1").on("change", function () {
				$("#fileUploadForm1").ajaxSubmit({
					type:'post',
					url:"${ctx}/admin/youtu/facecompare/upload",
					contentType:"application/x-www-form-urlencoded; charset=utf-8",
					beforeSend:function () {
						var file = $("#fileUpload1").get(0).files[0];
						var fileSrc = window.URL.createObjectURL(file);
						$(".main .left .image").css("background-image","url("+fileSrc+")");
						//清除url框
						$("#url1").val("");
					},
					success:function (data) {
						//返回文件路径
						var jsonData = eval("("+data+")");
						if(jsonData.result == 0){
							path1 = jsonData.path;
						}else{

						}
					},
					error:function () {
						layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
					}
				});
			});

			//打开file文件选择框
			$(".main .right .upload .fileUploadBtn").click(function () {
				$("#fileUpload2").click();
			});

			// 上传图片文件2
			$("#fileUpload2").on("change", function () {
				$("#fileUploadForm2").ajaxSubmit({
					type:'post',
					url:"${ctx}/admin/youtu/facecompare/upload",
					contentType:"application/x-www-form-urlencoded; charset=utf-8",
					beforeSend:function () {
						var file = $("#fileUpload2").get(0).files[0];
						var fileSrc = window.URL.createObjectURL(file);
						$(".main .right .image").css("background-image","url("+fileSrc+")");
						//清除url框
						$("#url2").val("");
					},
					success:function (data) {
						//返回文件路径
						var jsonData = eval("("+data+")");
						if(jsonData.result == 0){
							path2 = jsonData.path;
						}else{

						}
					},
					error:function () {
						layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
					}
				});
			});

			//人脸对比
			$(".main .operate").click(function () {
				url1 = $("#url1").val();
				url2 = $("#url2").val();
				if(url1 != "" && url2 != ""){
					$.ajax({
						url : "${ctx}/admin/youtu/facecompare",
						type : "post",
						dataType :"json",
						data : {"url1":url1, "url2":url2},
						beforeSend : function () {
							$(".main .result .similarity").hide();
							$(".main .result .yy").show();
						},
						success : function (data) {
							if(data.errorcode == 0){
								//关闭阴影，打开相似度数字
								$(".main .result .similarity").show();
								$(".main .result .yy").hide();

								//修改相似度数字
								$(".main .result .similarity").text(data.similarity + "%");
							}else{
								$(".main .result .yy").text(data.errormsg);
							}
							//显示返回数据
							var str = "{url1:'"+url1+"', url2:'"+url2+"'}";
							var resquestJson = eval("("+str+")");
							$(".main .request .req-result").JSONView(JSON.stringify(resquestJson));
							$(".main .response .res-result").JSONView(JSON.stringify(data));
						},
						error : function () {
							layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
						}
					});
				}else if(path1 != "" && path2 != ""){
					$.ajax({
						url : "${ctx}/admin/youtu/facecompare_path",
						type : "post",
						dataType :"json",
						data : {"path1":path1, "path2":path2},
						beforeSend : function () {
							$(".main .result .similarity").hide();
							$(".main .result .yy").show();
						},
						success : function (data) {
							if(data.errorcode == 0){
								//关闭阴影，打开相似度数字
								$(".main .result .similarity").show();
								$(".main .result .yy").hide();

								//修改相似度数字
								$(".main .result .similarity").text(data.similarity + "%");
							}else{
								$(".main .result .yy").text(data.errormsg);
							}
							//显示返回数据
							var str = "{path1:'upload image 1...', path2:'upload image 2...'}";
							var resquestJson = eval("("+str+")");
							$(".main .request .req-result").JSONView(JSON.stringify(resquestJson));
							$(".main .response .res-result").JSONView(JSON.stringify(data));
						},
						error : function () {
							layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
						}
					});
				}else{
					layer.msg('请遵守对比规则！',{icon:5,time:1000});
				}
			});
		});
	</script>
</body>
</html>