package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @since 2017-05-06
 */
@Controller
@RequestMapping("/admin/photoConfig")
public class PhotoConfigController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 采集配置页面
     * @return
     */
    @RequestMapping(value = "/collect-config", method = RequestMethod.GET)
    public String collectConfig(){
        return "admin/photo/collect-config";
    }

    /**
     * 针对BSGrid表格的封装操作
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(BSGridPage<PhotoConfig> bsGridPage, HttpServletRequest httpServletRequest){
        Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        return bsGridPage.parsePage(photoConfigPage);
    }
}
