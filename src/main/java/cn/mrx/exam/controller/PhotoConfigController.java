package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import cn.mrx.exam.utils.WebConstant;
import cn.mrx.exam.utils.YoutuUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 图片配置页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String collectConfig(){
        return "admin/photoConfig/photoConfig-list";
    }

    /**
     * 异步查询所有图片配置
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(BSGridPage<PhotoConfig> bsGridPage, HttpServletRequest httpServletRequest){
        //Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        //自定义带条件多表分页查询
        Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPhotoConfigPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        return bsGridPage.parsePage(photoConfigPage);
    }

    /**
     * 图片配置 - 添加采集规则页面
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model){
        //准备考生信息
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("role_id", 3);
        List<User> users = iUserService.selectList(userEntityWrapper);
        model.addAttribute("users", users);
        return "admin/photoConfig/photoConfig-add";
    }

    /**
     * 图片配置 - 异步添加采集规则
     * @param startTime
     * @param endTime
     * @param description
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String startTime, String endTime, String description, String studentIds, HttpServletRequest httpServletRequest) throws Exception{
        PhotoConfig photoConfig = new PhotoConfig();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _startTime = sdf.parse(startTime);
        Date _endTime = sdf.parse(endTime);

        //应考人
        if(studentIds!=null && !studentIds.trim().equals("")){
            photoConfig.setUserIds(studentIds);
        }
        photoConfig.setStartTime(_startTime);//开始时间
        photoConfig.setEndTime(_endTime);//结束时间
        photoConfig.setDescription(description);//描述
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(WebConstant.SESSION_USER);
        photoConfig.setPublishId(user.getId());//发布者
        return iPhotoConfigService.insert(photoConfig);
    }

    /**
     * 图片配置 - 更新采集规则页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);

        //所有学生
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("role_id", 3);
        List<User> users = iUserService.selectList(userEntityWrapper);
        for (User user : users){
            //PhotoConfig userIds已选择的学生
            if(photoConfig.getUserIds()!=null && !photoConfig.getUserIds().trim().equals("")){
                String str = photoConfig.getUserIds();
                for (String s : str.split(",")){
                    if (Integer.valueOf(s) == user.getId()){
                        user.setFlag(true);
                        break;
                    }else{
                        user.setFlag(false);
                    }
                }
            }
        }

        model.addAttribute("photoConfig", photoConfig);
        model.addAttribute("users", users);
        return "admin/photoConfig/photoConfig-edit";
    }

    /**
     * 图片配置 - 异步更新采集规则
     * @param id
     * @param startTime
     * @param endTime
     * @param description
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(String id, String startTime, String endTime, String description, String studentIds)throws Exception{
        PhotoConfig photoConfig = new PhotoConfig();
        //将字符串转换为Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _startTime = sdf.parse(startTime);
        Date _endTime = sdf.parse(endTime);

        photoConfig.setId(Integer.valueOf(id));//主键id
        photoConfig.setStartTime(_startTime);//开始时间
        photoConfig.setEndTime(_endTime);//结束时间
        photoConfig.setDescription(description);//描述
        String xxx = studentIds.equals("") ? " " : studentIds;
        photoConfig.setUserIds(xxx);//应考人
        return iPhotoConfigService.updateById(photoConfig);
    }

    /**
     * 图片配置 - 异步批量删除采集规则
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
        return iPhotoConfigService.deleteBatchIds(lists);
    }

    /**
     * 图片配置 - 图片库页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/photo-gallery/{id}", method = RequestMethod.GET)
    public String photoGallery(@PathVariable("id") String id, Model model){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //加入条件
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time", startTime);
        photoEntityWrapper.lt("create_time", endTime);
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        model.addAttribute("photos", photos);
        return "admin/photoConfig/photoConfig-photoGallery";
    }
}
