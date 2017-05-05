<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../jspf/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../jspf/head.jspf" %>
		<style type="text/css">
			.main{
				border: 1px solid #efeef0;
				border-radius: 5px;
			}

		</style>
	</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 过程分析 <span class="c-gray en">&gt;</span> 图片采集 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<div class="main">
		</div>
	</div>

	<!-- js -->
	<%@ include file="../jspf/footer.jspf" %>
	<script type="text/javascript" src="${ctx}/resources/admin/plug-in/jQuery-webcam/jquery.webcam.min.js"></script>
	<!-- 自定义js -->
	<script type="text/javascript">
		$(function() {
			var width = 320, height = 240;
			var pos = 0, ctx = null, saveCB, image = [];
			var canvas = document.createElement("canvas");
			canvas.setAttribute('width', width);
			canvas.setAttribute('height', height);
			if (canvas.toDataURL) {
				ctx = canvas.getContext("2d");
				image = ctx.getImageData(0, 0, width, height);
				saveCB = function(data) {
					var col = data.split(";");
					var img = image;
					for(var i = 0; i < width; i++) {
						var tmp = parseInt(col[i]);
						img.data[pos + 0] = (tmp >> 16) & 0xff;
						img.data[pos + 1] = (tmp >> 8) & 0xff;
						img.data[pos + 2] = tmp & 0xff;
						img.data[pos + 3] = 0xff;
						pos+= 4;
					}
					if (pos >= 4 * width * height) {
						ctx.putImageData(img, 0, 0);
						$.post("${ctx}/admin/photo/upload", {type: "data", image: canvas.toDataURL("image/png")}, function (data) {
						});
						pos = 0;
					}
				};
			} else {
				saveCB = function(data) {
					image.push(data);
					pos+= 4 * width;
					if (pos >= 4 * width * height) {
						$.post("${ctx}/admin/photo/upload", {type: "pixel", image: image.join('|')}, function (data) {
						});
						pos = 0;
					}
				};
			}

			$("#webcam").webcam({
				width: width,
				height: height,
				mode: "callback",
				swffile: "${ctx}/resources/admin/plug-in/jQuery-webcam/jscam_canvas_only.swf",
				onSave: saveCB,
				onCapture: function () {
					webcam.save();
				},
				debug: function (type, string) {
					console.log(type + ": " + string);
				}
			});

			/**
			 *  点击拍照
			 */
			$("#take-photo").click(function () {
				webcam.capture();
			});
		});
	</script>
</body>
</html>