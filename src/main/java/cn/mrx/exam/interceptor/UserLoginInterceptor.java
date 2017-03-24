package cn.mrx.exam.interceptor;

import cn.mrx.exam.pojo.User;
import cn.mrx.exam.service.IUserService;
import cn.mrx.exam.utils.CookieUtil;
import cn.mrx.exam.utils.EncryptAndDecryptUtil;
import cn.mrx.exam.utils.WebConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: UserLoginInterceptor
 * @Author: Mr.X
 * @Date: 2017/3/24 12:45
 * @Description: 登录拦截
 * @Version 1.0
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IUserService iUserService;

    /**
     * 登录拦截
     * @param httpServletRequest
     * @param httpServletResponse
     * @param arg2
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object arg2) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(WebConstant.SESSION_USER);
        if (user != null) {
            return true;
        }else{
            Cookie cookie = CookieUtil.getCookie(httpServletRequest, WebConstant.User_LOGIN_COOKIE);
            if (cookie != null){
                String cookieValue = EncryptAndDecryptUtil.base64Decrypt(cookie.getValue());
                String[] arr = cookieValue.split(":");
                if(arr.length == 2){
                    String username = arr[0];
                    String pwd = arr[1];
                    //查询数据库
                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.eq("username", username);
                    entityWrapper.eq("pwd", pwd);
                    User t_user = iUserService.selectOne(entityWrapper);
                    if (t_user != null){
                        httpSession.setAttribute(WebConstant.SESSION_USER, t_user);
                        return true;
                    }
                }
            }
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/admin/login");
            return false;
        }
    }
}
