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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 编辑权限
     * @param permission
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(Permission permission){
        return iPermissionService.updateById(permission);
    }

    /**
     * 批量删除权限
     * @param ids
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Object del(String ids){
        String[]  strs = ids.split(",");
        List<String> lists = new ArrayList<>();
        for (String str : strs) {
            if(!str.equals("")){
                lists.add(str);
            }
        }
        if (lists.size() < 1){
            return "null";
        }else{
            return iPermissionService.deleteBatchIds(lists);
        }
    }

    /**
     * 打开permission-add.jsp
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "admin/permission/permission-add";
    }

    /**
     * 添加权限
     * @param permission
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(Permission permission){
        return iPermissionService.insert(permission);
    }
}
