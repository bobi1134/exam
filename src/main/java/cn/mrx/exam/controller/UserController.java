package cn.mrx.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-18
 */
@Controller
@RequestMapping("/admin")
public class UserController extends BaseController{

    /**
     *
     * @return
     */
    @RequestMapping("")
    public String admin(){
        return "admin/index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "admin/welcome";
    }

    @RequestMapping("/ee")
    public String aa(){
        return "admin/welcome";
    }
}
