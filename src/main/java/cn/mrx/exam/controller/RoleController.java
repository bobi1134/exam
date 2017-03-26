package cn.mrx.exam.controller;

import cn.mrx.exam.interceptor.PermissionCheck;
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
 * @since 2017-03-26
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    /**
     * role-list.jsp页面
     * @return
     */
    @PermissionCheck
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String user(){
        return "admin/role/role-list";
    }
}
