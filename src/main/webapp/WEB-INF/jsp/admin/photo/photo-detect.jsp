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

			.main .photo{
				width: 740px;
				height: 320px;
				margin:100px auto;
				position: relative;
			}

			#webcam{
				float: left;
				border: 20px solid #333;
				position: relative;
				background: #eee;
				border-radius: 20px;
			}

			#webcam > img{
				z-index: 1;
				position: absolute;
				border: 0px none;
				padding: 0px;
				bottom: -40px;
				left: 89px;
			}

			#webcam > object{
				display: block;
				position: relative;
				z-index: 2;
			}

			#webcam > span{
				position: absolute;
				color: #eee;
				font-size: 10px;
				bottom: -20px;
				left: 50%;
				margin-left: -20px;
			}

			#take-photo{
				width: 110px;
				height: 30px;
				position: absolute;
				left: 50%;
				margin-left: -55px;
				bottom: -50px;
				text-align: center;
				line-height: 30px;
				background-color: #333333;
				border-radius: 8px;
				color: #fff;
				cursor: pointer;
			}

			.main .photo .result{
				width: 320px;
				height: 240px;
				float: right;
				border: 20px solid #333;
				border-radius: 20px;
				position: relative;
			}

			.main .photo .result > img{
				position: absolute;
				border: 0px none;
				padding: 0px;
				bottom: -40px;
				right: 89px;
			}

			.main .photo .result > span{
				position: absolute;
				color: #eee;
				font-size: 10px;
				bottom: -20px;
				left: 50%;
				margin-left: -25px;
			}

			.main .photo .result .bg{
				width: 100%;
				height: 100%;
			}

			.main .photo .result .item{
				position: absolute;
				width: 3px;
				height: 3px;
				border-radius: 50%;
				background: #5fffcc;
				opacity: 0.9;
			}

			.main .photo .result .yy{
				position: absolute;
				width: 100%;
				height: 100%;
				background-color: #000;
				opacity: .2;
				color: #fff;
				text-align: center;
				line-height: 240px;
				top:0;
			}
		</style>
	</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 过程分析 <span class="c-gray en">&gt;</span> 拍摄检测 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<div class="main">
			<div class="photo">
				<div id="webcam">
					<img src="${ctx}/resources/admin/static/photo/antenna.png"/>
					<span>Take Pictures</span>
				</div>
				<div id="take-photo">拍照</div>
				<div class="result">
					<img src="${ctx}/resources/admin/static/photo/antenna.png"/>
					<span>Result</span>
					<div class="bg"></div>
					<!-- items定位 -->

					<div class="yy"></div>
				</div>
			</div>
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
							afterDo(data);
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
							afterDo(data);
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
				$(".main .photo .result .bg").hide();
				webcam.capture();
			});

			/**
			 * 之后操作
			 */
			function afterDo(data) {
				if(data.flag){
					$(".main .photo .result .bg").show().addClass("hui-bouncein").css("background", "url(${ctx}/resources/admin/upload/photo/"+data.fileName+")");
				}

				//异步五官定位(根据人脸检测中的face返回的点来进行定位)
				$.ajax({
					url : "${ctx}/admin/photo/detectface",
					type : "post",
					dataType :"json",
					data : {fileName: data.fileName},
					beforeSend:function(){
						//删除item节点
						$(".main .photo .result .item").remove();
						$(".main .photo .result .yy").text("请稍等...").show();
					},
					success : function(data){
						if(data.errorcode == 0){
							$(".main .photo .result .yy").text("").hide();
							var max_len = Math.max(data.image_width, data.image_height);
							var canvasWidth = 320.0;
							var scale = canvasWidth / max_len;
							var canvasXOffset = (canvasWidth - data.image_width*scale) / 2.0;
							var canvasYOffset = (canvasWidth - data.image_height*scale) / 2.0;
							var landmark = new Array();
							var shape = data.face[0].face_shape;
							landmark = landmark.concat(shape.face_profile, shape.left_eye, shape.left_eyebrow, shape.mouth, shape.nose, shape.right_eye, shape.right_eyebrow);
							for (var i = 0; i < landmark.length; ++i) {
								var x = parseInt(scale*landmark[i].x);
								var y = parseInt(scale*landmark[i].y);
								x += canvasXOffset;
								y += canvasYOffset-40;
								//追加item内容
								$(".main .photo .result").prepend("<div class='item hui-wobble' style='top:"+y+"px;left:"+x+"px'></div>");
							}
						}else{
							console.log(data.errormsg);
							$(".main .photo .result .yy").text(data.errormsg);
						}
					},
					error : function(){
						layer.msg('服务器错误，请联系管理员！',{icon:5,time:1000});
					}
				});
			}
		});
	</script>
</body>
</html>