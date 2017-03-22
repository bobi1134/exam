<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="jspf/taglib.jspf" %>
<!DOCTYPE HTML>
<html>
<head>
    <link href="${ctx}/resources/admin/h-ui/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/resources/admin/plug-in/sweetalert/sweetalert.css" rel="stylesheet" type="text/css" />
    <%@ include file="jspf/head.jspf" %>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value=""/>
<div class="header"><span>${systemWeb.title} ${systemWeb.version}</span></div>
<div class="loginWraper">
    <div class="loginBox">
        <form class="form form-horizontal" method="post" onsubmit="return loginForm()" id="loginForm">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input name="username" type="text" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input name="pwd" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input name="captcha" class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
                    <img src="${ctx}/admin/captcha" id="vimg"/> <a id="kanbuq" href="javascript:kanbuq();">看不清，换一张</a></div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <label for="online"><input type="checkbox" name="online" id="online" value="">使我保持登录状态</label>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" id="loginBtn"/>
                    <input type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="footer">${systemWeb.title} ${systemWeb.version}</div>
<%@ include file="jspf/footer.jspf" %>
<script type="text/javascript" src="${ctx}/resources/admin/plug-in/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript">
    /**
     * 看不清，换一张
     */
    function kanbuq() {
        $("#vimg").attr("src", "${ctx}/admin/captcha?random=" + Math.random());
    }
    
    /**
     * 用户登录函数
     * @returns {boolean}
     */
    function loginForm() {
        $.ajax({
            type : "post",
            url : "${ctx}/admin/login",
            dataType : "json",
            data : $("#loginForm").serialize(),
            success : function(json){
                if(json.error != null){
                    swal("错误信息", json.error, "error");
                }else if(json.success){
                    window.location = "${ctx}/admin";
                }
            },
            error : function(){
                swal("错误信息", "服务器错误，请联系管理员！", "error");
            }
        });
        return false;
    }
</script>
</body>
</html>