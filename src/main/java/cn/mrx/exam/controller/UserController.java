package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.SystemWeb;
import cn.mrx.exam.utils.CaptchaUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-19
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

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

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
//    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        CaptchaUtils.outputCaptcha(request, response);
    }
}
