﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="jspf/taglib.jspf" %>
<!DOCTYPE HTML>
<html>
<head>
    <link href="${ctx}/resources/admin/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <%@ include file="jspf/head.jspf" %>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value=""/>
<div class="header"><span>${systemWeb.title} ${systemWeb.version}</span></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <form class="form form-horizontal" action="index.html" method="post">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="" name="" type="text" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="" name="" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input class="input-text size-L" type="text" placeholder="验证码"
                           onblur="if(this.value==''){this.value='验证码:'}"
                           onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
                    <img src="${ctx}/admin/captcha" id="vimg"/> <a id="kanbuq" href="javascript:;">看不清，换一张</a></div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <label for="online">
                        <input type="checkbox" name="online" id="online" value="">
                        使我保持登录状态</label>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input name="" type="submit" class="btn btn-success radius size-L"
                           value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                    <input name="" type="reset" class="btn btn-default radius size-L"
                           value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="footer">${systemWeb.title} ${systemWeb.version}</div>
<%@ include file="jspf/footer.jspf" %>
<script type="text/javascript">
    $(function () {
//        验证码，看不清，换一张
        $("#kanbuq").click(function () {
            $("#vimg").attr("src", "${ctx}/admin/captcha?random=" + Math.random());
        });
    });
</script>
</body>
</html>