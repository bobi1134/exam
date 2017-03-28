package cn.mrx.exam.controller;

import cn.mrx.exam.interceptor.PermissionCheck;
import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.Role;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.mrx.exam.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-26
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * role-list.jsp页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String rolePage(Model model){
        return "admin/permission/permission-list";
    }

    /**
     * 针对BSGrid表格的封装操作
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(BSGridPage<Permission> bsGridPage, HttpServletRequest httpServletRequest){
        Page<Permission> permissionPage = iPermissionService.selectPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        return bsGridPage.parsePage(permissionPage);
    }

    /**
     * 打开permission-edit.jsp
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model){
        Permission permission = iPermissionService.selectById(id);
        model.addAttribute("permission", permission);
        return "admin/permission/permission-edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(Permission permission){
        Map<String,Object> map = new HashMap<>();
        boolean result = iPermissionService.updateById(permission);
        if (result == true)
            map.put("status", true);
        else
            map.put("status", false);
        return map;
    }

}
