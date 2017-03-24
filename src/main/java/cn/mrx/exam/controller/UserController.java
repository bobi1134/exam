package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.mrx.exam.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-23
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    /**
     * member-list.jsp页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String user(){
        return "admin/user/member-list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer curPage, Integer pageSize,
                                    String sortName, String sortOrder,
                                    String startTime, String endTime, String username){
        Map<String, Object> map = new HashMap<>();
        //搜索条件
        EntityWrapper<User> entityWrapper = new EntityWrapper();
        if (sortName != null && !sortName.equals("")){
            entityWrapper.orderBy(sortName, sortOrder.equalsIgnoreCase("asc"));
        }

        System.out.println("----------------------");
        System.out.println(startTime);
        System.out.println("----------------------");
        if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")){
            entityWrapper.between("last_login_time", startTime+" 00:00:00", endTime+" 23:59:59");
        }else if (startTime != null && !startTime.equals("")){
            entityWrapper.gt("last_login_time", startTime+" 00:00:00");
        }else if (endTime != null && !endTime.equals("")){
            entityWrapper.gt("last_login_time", endTime+" 23:59:59");
        }

        if (username != null && !username.equals("")){
            entityWrapper.like("username", username);
        }

        Page<User> userPage = iUserService.selectPage(new Page<User>(curPage, pageSize), entityWrapper);
        List<User> users = userPage.getRecords();
        map.put("curPage", curPage);
        map.put("pageSize", pageSize);
        map.put("totalRows", userPage.getTotal());
        map.put("data", users);
        map.put("success", true);
        return map;
    }

}
