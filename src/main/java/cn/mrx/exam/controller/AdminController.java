package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.SystemServer;
import cn.mrx.exam.pojo.SystemWeb;
import cn.mrx.exam.utils.CaptchaUtil;
import cn.mrx.exam.utils.SystemMessUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Mr.X
 * Date: 2017/3/18
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

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
     * 用户登录逻辑操作
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(Model model, HttpServletRequest request) {
        if ("GET".equals(request.getMethod())) {
            model.addAttribute("systemWeb", iSystemWebService.selectOne(new EntityWrapper<SystemWeb>().eq("category", 2)));
            return "admin/login";
        } else {
            System.out.println("----------登录操作...------------");
            return null;
        }
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
