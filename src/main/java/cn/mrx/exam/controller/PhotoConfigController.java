package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.QueryFilter;
import cn.mrx.exam.utils.WebConstant;
import cn.mrx.exam.utils.YoutuUtil;
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
     * 采集配置页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String collectConfig(){
        return "admin/photo/photoConfig-list";
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
        //Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        //自定义带条件多表分页查询
        Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPhotoConfigPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
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

    /**
     * 更新采集规则页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);
        model.addAttribute("photoConfig", photoConfig);
        return "admin/photo/photoConfig-edit";
    }

    /**
     * 更新采集规则
     * @param id
     * @param startTime
     * @param endTime
     * @param description
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(String id, String startTime, String endTime, String description)throws Exception{
        PhotoConfig photoConfig = new PhotoConfig();
        //将字符串转换为Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _startTime = sdf.parse(startTime);
        Date _endTime = sdf.parse(endTime);
        photoConfig.setId(Integer.valueOf(id));//主键id
        photoConfig.setStartTime(_startTime);//开始时间
        photoConfig.setEndTime(_endTime);//结束时间
        photoConfig.setDescription(description);//描述
        return iPhotoConfigService.updateById(photoConfig);
    }

    /**
     * 批量删除
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
     * 图片库页面
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
        return "admin/photo/photoConfig-photoGallery";
    }

    /**
     * 结果分析-技术支持页面（默认打开页面）
     * @param id
     * @return
     */
    @RequestMapping(value = "/analysis-technicalSupport/{id}", method = RequestMethod.GET)
    public String analysisPage(@PathVariable("id") String id, Model model){
        model.addAttribute("id", id);
        return "admin/photo/photoConfig-analysis-technicalSupport";
    }

    /**
     * 结果分析-过程分析页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/analysis-processAnalysis/{id}", method = RequestMethod.GET)
    public String analysis(@PathVariable("id") String id, Model model, HttpServletRequest httpServletRequest){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);
        //默认先获取该考试时间段采集成功率
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //该时间段内的图片库
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time" , startTime);
        photoEntityWrapper.lt("create_time" , endTime);
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        //先判断该考试时间段内的图片是否已经解析
        String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/upload/photo/");
        for (Photo photo : photos){
            Photo p = new Photo();
            boolean flag = false;
            //人脸检测
            if(photo.getResultDetectface()==null || photo.getResultDetectface().equals("")){
                JSONObject detectFaceResult = YoutuUtil.detectFace(realPath + photo.getName());
                //将人脸检测分析结果装入数据库
                p.setResultDetectface(detectFaceResult.toString());
                flag = true;
            }
            //五官定位
            if(photo.getResultFaceshape()==null || photo.getResultFaceshape().equals("")){
                JSONObject detectFaceResult = YoutuUtil.faceShape(realPath + photo.getName());
                //将五官定位分析结果装入数据库
                p.setResultFaceshape(detectFaceResult.toString());
                flag = true;
            }
            //修改数据库
            System.out.println(flag);
            if (flag){
                System.out.println(p);
                EntityWrapper<Photo> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("name", photo.getName());
                boolean result = iPhotoService.update(p, entityWrapper);
            }
        }

        //查数据库，分析数据。。。

        model.addAttribute("id", id);
        return "admin/photo/photoConfig-analysis-processAnalysis";
    }
}
