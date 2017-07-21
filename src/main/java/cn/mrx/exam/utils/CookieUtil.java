package cn.mrx.exam.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie操作
 */
public final class CookieUtil {
    /**
     * 根据Cookie的名字获取一个Cookie
     *
     * @param httpServletRequest
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String cookieName) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 根据Cookie的名字删除一个Cookie
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param cookieName
     */
    public static void removeCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String cookieName) {
        Cookie cookie = getCookie(httpServletRequest, cookieName);
        if (cookie != null) {
            cookie.setMaxAge(0); // 立即失效
            cookie.setPath("/"); // localhost:8080/
            httpServletResponse.addCookie(cookie);
        }
    }

    /**
     * 添加一个Cookie
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     */
    public static void addCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String cookieName, String cookieValue, int maxAge) {
        Cookie cookie = getCookie(httpServletRequest, cookieName);
        if (cookie == null) {
            cookie = new Cookie(cookieName, cookieValue);
        }
        cookie.setMaxAge(maxAge); // Cookie有效的时间
        cookie.setPath("/"); // 设置cookie的有效路径， 根路径下
        // cookie.setDomain("t.qq.com"); // 设置可以跨子域访问
        httpServletResponse.addCookie(cookie);
    }
}