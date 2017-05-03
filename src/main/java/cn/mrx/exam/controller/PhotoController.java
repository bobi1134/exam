package cn.mrx.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.mrx.exam.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-03
 */
@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController {

    @RequestMapping(value = "/collect", method = RequestMethod.GET)
    public String collect(){
        return "admin/photo/collect";
    }
}
