package cn.mrx.exam.controller;

import cn.mrx.exam.common.WebConstant;
import cn.mrx.exam.controller.support.BaseController;
import cn.mrx.exam.controller.validation.UserLogin;
import cn.mrx.exam.pojo.SystemWeb;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.CaptchaUtil;
import cn.mrx.exam.utils.SystemMessUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Mr.X
 * Date: 2017/3/18
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    /**
     * 打开index.jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String admin(Model model){
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
    public  Map<String, Object> login(@Validated(UserLogin.class) User user, BindingResult bindingResult, HttpSession httpSession) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("------------>"+httpSession.getAttribute(WebConstant.SESSION_CAPTCHA));
        if (bindingResult.hasErrors()) {
            map.put("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
        }else if (!user.getCaptcha().equalsIgnoreCase((String) httpSession.getAttribute(WebConstant.SESSION_CAPTCHA))){
            map.put("error", "验证码不正确！");
        }else {
            EntityWrapper entityWrapper = new EntityWrapper();
            entityWrapper.eq("username", user.getUsername());
            entityWrapper.eq("pwd", user.getPwd());
            User t_user = iUserService.selectOne(entityWrapper);
            if (t_user == null){
                map.put("error", "用户名或密码错误！");
            }else {
                httpSession.setAttribute(WebConstant.SESSION_USER, t_user);
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
}
