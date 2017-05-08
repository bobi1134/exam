package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import cn.mrx.exam.utils.WebConstant;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String collectConfig(){
        return "admin/photo/photoConfig";
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

    /**
     * 添加采集规则页面
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(){
        return "admin/photo/photoConfig-add";
    }

    /**
     * 添加采集规则
     * @param startTime
     * @param endTime
     * @param description
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String startTime, String endTime, String description, HttpServletRequest httpServletRequest) throws Exception{

        PhotoConfig photoConfig = new PhotoConfig();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _startTime = sdf.parse(startTime);
        Date _endTime = sdf.parse(endTime);
        photoConfig.setStartTime(_startTime);//开始时间
        photoConfig.setEndTime(_endTime);//结束时间
        photoConfig.setDescription(description);//描述
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(WebConstant.SESSION_USER);
        photoConfig.setUserId(user.getId());//发布者
        return iPhotoConfigService.insert(photoConfig);
    }
}
