package cn.mrx.exam.utils;

/**
 * @version 1.0
 * @ClassName: WebConstant
 * @Author: Mr.X
 * @Date: 2017/3/22 20:58
 * @Description:
 */
public class WebConstant {
    /** 定义放在Session中的验证码 */
    public final static String SESSION_CAPTCHA = "session_captcha";
    /** 定义放在Session中的用户 */
    public final static String SESSION_USER = "session_user";
    /** 用户登录的Cookie名称 */
    public final static String User_LOGIN_COOKIE = "user_login_cookie";
    /** Cookie的有效期默认为30天 */
    public final static int COOKIE_MAX_AGE = 60 * 60 * 24 * 30;
}
