package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.SystemWeb;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.mrx.exam.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/admin/systemWeb")
public class SystemWebController extends BaseController {

    /**
     * 打开systemWeb-list页面
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
	public String systemWeb(Model model){
        List<SystemWeb> systemWebList = iSystemWebService.selectList(null);
        model.addAttribute("systemWebList", systemWebList);
        return "admin/systemWeb/systemWeb-list";
    }

    /**
     * 打开systemWeb-edit页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id,
                       Model model){
        EntityWrapper<SystemWeb> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("category", id);
        SystemWeb systemWeb = iSystemWebService.selectOne(entityWrapper);
        model.addAttribute("systemWeb", systemWeb);
        return "admin/systemWeb/systemWeb-edit";
    }

    /**
     * 异步修改网站设置
     * @param id
     * @param keywords
     * @param description
     * @param title
     * @param version
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(String id,
                       String keywords,
                       String description,
                       String title,
                       String version){
        SystemWeb systemWeb = new SystemWeb();
        systemWeb.setId(Integer.valueOf(id));
        systemWeb.setKeywords(keywords);
        systemWeb.setDescription(description);
        systemWeb.setTitle(title);
        systemWeb.setVersion(version);
        return iSystemWebService.updateById(systemWeb);
    }
}
