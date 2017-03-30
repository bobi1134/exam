package cn.mrx.exam.controller;

import cn.mrx.exam.interceptor.PermissionCheck;
import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.Role;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.service.IRoleService;
import cn.mrx.exam.utils.WebConstant;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * role-list.jsp页面
     * @return
     */
    @PermissionCheck
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String rolePage(Model model){
        Page<Role> rolePage = iRoleService.selectPage(new Page<>());
        model.addAttribute("roles", rolePage.getRecords());
        return "admin/role/role-list";
    }

    /**
     * 打开role-edit.jsp
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editRole(Model model, @PathVariable("id") String id, HttpServletRequest httpServletRequest){
        //所有角色
        Page<Permission> permissionPage = iPermissionService.selectPage(new Page<>());
        List<Permission> permissions = permissionPage.getRecords();
        //父亲菜单id：1:会员管理,2:角色权限管理,3:文章管理
        List<Integer> parentIds = new ArrayList<>();
        for (Permission permission : permissions) {
            //将该角色(id)拥有的权限加上flag
            Role role = iRoleService.selectById(id);
            if(role.getPermissionIds() != null && !role.getPermissionIds().trim().equals("")){
                String[] permissionIds = role.getPermissionIds().split(",");
                for (String permissionId : permissionIds) {
                    if (Integer.valueOf(permissionId)==permission.getId()){
                        parentIds.add(permission.getParentId());
                        permission.setFlag(true);
                        continue;
                    }
                }
            }else{
                parentIds.add(permission.getParentId());
            }
        }
        model.addAttribute("allPermissions", permissions);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("role", iRoleService.selectById(id));
        return "admin/role/role-edit";
    }

    /**
     * 修改角色拥有的权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(String roleId, String permissionIds){
        String ids = (permissionIds.equals("")) ? " " : permissionIds;
        Role role = new Role();
        role.setId(Integer.valueOf(roleId));
        role.setPermissionIds(ids);
        return iRoleService.updateById(role);
    }
}
