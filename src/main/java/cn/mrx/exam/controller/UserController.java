package cn.mrx.exam.controller;

import cn.mrx.exam.interceptor.PermissionCheck;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import com.baomidou.mybatisplus.plugins.Page;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.mrx.exam.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    /**
     * member-list.jsp页面
     * @return
     */
    @PermissionCheck
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String user(){
        return "admin/user/member-list";
    }

    /**
     * 针对BSGrid表格的封装操作
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @PermissionCheck
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(BSGridPage<User> bsGridPage, HttpServletRequest httpServletRequest){
        Page<User> userPage = iUserService.selectPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        return bsGridPage.parsePage(userPage);
    }

    /**
     * 添加用户页面
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "admin/user/member-add";
    }

    /**
     * 异步添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(User user){
        return iUserService.insert(user);
    }

    /**
     * 添加修改页面
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id,
                       Model model){
        User user = iUserService.selectById(id);
        model.addAttribute("user", user);
        return "admin/user/member-edit";
    }

    /**
     * 异步修改用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(User user){
        return iUserService.updateById(user);
    }

    /**
     * 异步批量删除用户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Object batchDel(String ids){
        return iUserService.deleteBatchIds(Arrays.asList(ids.split(",")));
    }
}

