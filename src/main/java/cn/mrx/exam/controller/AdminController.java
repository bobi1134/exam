package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.Role;
import cn.mrx.exam.utils.*;
import cn.mrx.exam.controller.validation.UserLogin;
import cn.mrx.exam.pojo.SystemWeb;
import cn.mrx.exam.pojo.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Author: Mr.X
 * Date: 2017/3/18
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 打开index.jsp
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String admin(Model model, HttpServletRequest httpServletRequest){
        //权限查询
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(WebConstant.SESSION_USER);
        Role role = iRoleService.selectById(user.getRoleId());
        String[] permissionIds = role.getPermissionIds().split(",");
        List<Permission> permissions = new ArrayList<>();
        List<Integer> parentIds = new ArrayList<>();
        for (String permissionId : permissionIds){
            Permission permission = iPermissionService.selectById(permissionId);
            if (permission != null){
                parentIds.add(permission.getParentId());
                permissions.add(permission);
            }
        }
        model.addAttribute("permissions", permissions);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("systemWeb", iSystemWebService.selectOne(new EntityWrapper<SystemWeb>().eq("category" ,2)));
        return "admin/index";
    }

    /**
     * 打开welcome.jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(Model model){
        model.addAttribute("systemWeb", iSystemWebService.selectOne(new EntityWrapper<SystemWeb>().eq("category" ,2)));
        model.addAttribute("systemMess", SystemMessUtil.getSystemServer());
        return "admin/welcome";
    }

    /**
     * 用户登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("systemWeb", iSystemWebService.selectOne(new EntityWrapper<SystemWeb>().eq("category", 2)));
        return "admin/login";
    }

    /**
     * 用户登录逻辑操作
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, Object> login(@Validated(UserLogin.class) User user, BindingResult bindingResult, HttpSession httpSession, HttpServletRequest httpServletRequest, String online, HttpServletResponse httpServletResponse) {
        Map<String, Object> map = new HashMap<>();
        String sessionCaptcha = (String) httpSession.getAttribute(WebConstant.SESSION_CAPTCHA);
        if (bindingResult.hasErrors()) {
            map.put("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else if (!user.getCaptcha().equalsIgnoreCase(sessionCaptcha)){
            map.put("error", "验证码不正确！");
        }else {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("username", user.getUsername());
            entityWrapper.eq("pwd", user.getPwd());
            User t_user = iUserService.selectOne(entityWrapper);
            if (t_user == null){
                map.put("error", "用户名或密码错误！");
            }else {
                //加入session，这样就保证了在welcome.jsp取出的是上次登录ip和时间
                httpSession.setAttribute(WebConstant.SESSION_USER, t_user);
                //更新数据库
                User u_user = new User();
                u_user.setId(t_user.getId());
                u_user.setTime(t_user.getTime()+1);
                u_user.setLastLoginTime(new Date());
                u_user.setLastLoginIp(IPUtil.getV4IP());
                u_user.setCaptcha(sessionCaptcha);
                iUserService.updateById(u_user);
                //记住我
                if (online!=null && online.equals("true")){
                    String cookieValue = EncryptAndDecryptUtil.base64Encrypt(t_user.getUsername()+":"+t_user.getPwd());
                    CookieUtil.addCookie(httpServletRequest, httpServletResponse, WebConstant.User_LOGIN_COOKIE, cookieValue, WebConstant.COOKIE_MAX_AGE);
                }
                map.put("success", true);
            }
        }
        return map;
    }

    /**
     * 验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        CaptchaUtil.outputCaptcha(request, response);
    }

    /**
     * 用户退出
     * @param httpServletRequest
     * @param httpServletResponse
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession){
        httpSession.invalidate();
        CookieUtil.removeCookie(httpServletRequest, httpServletResponse, WebConstant.User_LOGIN_COOKIE);
        return "redirect:/admin/login";
    }
}
