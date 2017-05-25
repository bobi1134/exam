package cn.mrx.exam.controller;

import cn.mrx.exam.interceptor.PermissionCheck;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.mrx.exam.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
}
