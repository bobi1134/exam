package cn.mrx.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName: YouTuController
 * @Author: Mr.X
 * @Date: 2017/3/30 15:35
 * @Description:
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/yt")
public class YouTuController extends BaseController {


    @RequestMapping(value = "/detectface", method = RequestMethod.GET)
    public String detectFace(){
        return "admin/yt/detectface";
    }

}
