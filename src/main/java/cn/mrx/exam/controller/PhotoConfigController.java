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
import java.io.File;
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
     * 跳转到photoConfig-list页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String collectConfig(){
        return "admin/photoConfig/photoConfig-list";
    }

    /**
     * 异步查询所有的photoConfig
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(BSGridPage<PhotoConfig> bsGridPage,
                       HttpServletRequest httpServletRequest){
        //Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        //自定义带条件多表分页查询
        Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPhotoConfigPage(bsGridPage.getPage(), QueryFilter.getInstance(httpServletRequest).buildEntityWrapper());
        return bsGridPage.parsePage(photoConfigPage);
    }

    /**
     * 跳转到photoConfig-add页面
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
     * 异步添加photoConfig
     * @param startTime
     * @param endTime
     * @param description
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String startTime,
                      String endTime,
                      String description,
                      String studentIds,
                      String collectRate,
                      HttpServletRequest httpServletRequest) throws Exception{
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
        photoConfig.setCollectRate(collectRate);//采集频率
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(WebConstant.SESSION_USER);
        photoConfig.setPublishId(user.getId());//发布者
        return iPhotoConfigService.insert(photoConfig);
    }

    /**
     * 跳转到photoConfig-edit页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id,
                       Model model){
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
     * 异步更新photoConfig
     * @param id
     * @param startTime
     * @param endTime
     * @param description
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(String id,
                       String startTime,
                       String endTime,
                       String description,
                       String collectRate,
                       String studentIds)throws Exception{
        PhotoConfig photoConfig = new PhotoConfig();
        //将字符串转换为Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date _startTime = sdf.parse(startTime);
        Date _endTime = sdf.parse(endTime);

        photoConfig.setId(Integer.valueOf(id));//主键id
        photoConfig.setStartTime(_startTime);//开始时间
        photoConfig.setEndTime(_endTime);//结束时间
        photoConfig.setDescription(description);//描述
        photoConfig.setCollectRate(collectRate);//采集频率
        String xxx = studentIds.equals("") ? " " : studentIds;
        photoConfig.setUserIds(xxx);//应考人
        return iPhotoConfigService.updateById(photoConfig);
    }

    /**
     * 异步批量删除photoConfig
     * @param ids
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Object del(String ids,
                      HttpServletRequest httpServletRequest){
        String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/upload/photo/");

        String[] strs = ids.split(",");
        List<String> lists = new ArrayList<>();
        for (String str : strs) {
            if(!str.equals("")){
                //@1、要删除的ids
                lists.add(str);

                //@2、删除在该采集配置条件下的所有图片
                PhotoConfig photoConfig = iPhotoConfigService.selectById(str);
                //将Date转换为字符串
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime = sdf.format(photoConfig.getStartTime());
                String endTime = sdf.format(photoConfig.getEndTime());
                //该时间段内的图片库
                EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
                photoEntityWrapper.gt("create_time" , startTime);
                photoEntityWrapper.lt("create_time" , endTime);
                List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
                for (Photo photo : photos){
                    File file = new File(realPath+photo.getName());
                    if (file.exists()){
                        file.delete();
                    }
                }
            }
        }
        return iPhotoConfigService.deleteBatchIds(lists);
    }

    /**
     * 跳转到图片库页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/photo-gallery/{id}", method = RequestMethod.GET)
    public String photoGallery(@PathVariable("id") String id,
                               Model model){
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

    /**
     * 跳转到查看该堂考试下的所有学生信息页面
     * @param photoConfigId
     * @param model
     * @return
     */
    @RequestMapping(value = "/studentInfo/{photoConfigId}", method = RequestMethod.GET)
    public String studentInfo(@PathVariable("photoConfigId") String photoConfigId,
                              Model model){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(photoConfigId);
        String str = photoConfig.getUserIds();
        List<User> users = new ArrayList<>();
        if (str!=null && !str.equals("")){
            for (String s : str.split(",")){
                users.add(iUserService.selectById(s));
            }
        }
        model.addAttribute("photoConfigId", photoConfigId);
        model.addAttribute("users", users);
        return "/admin/photoConfig/photoConfig-studentInfo";
    }

    /**
     * PhotoConfig-list页面：信息采集入库
     * @param id
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/informationCollect/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object informationCollect(@PathVariable("id") String id,
                                     HttpServletRequest httpServletRequest){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);
        //先判断该考试时间段内的图片（人脸分析、五官定位）是否已经解析，为解析则调用接口解析
        //照片存放目录
        String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/upload/photo/");
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //该时间段内的图片库
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time" , startTime);
        photoEntityWrapper.lt("create_time" , endTime);
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        //人脸对比第一张图片
        String photo1_name = "";
        boolean photo1_flag = true;
        //分会结果
        String josnStr = "";

        if(photos.size()>0){
            int successNum = 0, errorNum = 0;
            for (Photo photo : photos){
                Photo p = new Photo();
                boolean flag = false;

                //人脸分析
                if(photo.getResultDetectface()==null || photo.getResultDetectface().equals("")){
                    JSONObject detectFaceResult = YoutuUtil.detectFace(realPath + photo.getName());
                    p.setResultDetectface(detectFaceResult.toString());
                    flag = true;

                    //将第一个正确检测的数据保存起来
                    if(photo1_flag && detectFaceResult.get("face")!=null && (int)detectFaceResult.get("errorcode")==0){
                        photo1_name = photo.getName();
                        photo1_flag = false;
                    }
                }
                //五官定位
                if(photo.getResultFaceshape()==null || photo.getResultFaceshape().equals("")){
                    JSONObject faceShapeResult = YoutuUtil.faceShape(realPath + photo.getName());
                    p.setResultFaceshape(faceShapeResult.toString());
                    flag = true;
                }

                //人脸对比
                if(photo.getResultFacecompare()==null || photo.getResultFacecompare().trim().equals("")){
                    JSONObject faceCompareResult = YoutuUtil.faceCompare(realPath+photo1_name, realPath+photo.getName());
                    p.setResultFacecompare(faceCompareResult.toString());
                    flag = true;
                }

                //若有空则修改数据库
                if (flag){
                    System.out.println(p);
                    EntityWrapper<Photo> entityWrapper = new EntityWrapper<>();
                    entityWrapper.eq("name", photo.getName());
                    boolean result = iPhotoService.update(p, entityWrapper);
                    if(result) successNum++;
                    else errorNum++;
                }
            }

            if(successNum>0 || errorNum>0){
                josnStr = "{\"status\":\"gt0\", \"isCollect\":\"false\", \"successNum\":\""+successNum+"\", \"errorNum\":\""+errorNum+"\"}";
            }else{
                josnStr = "{\"status\":\"gt0\", \"isCollect\":\"true\"}";
            }
            return JSON.parseObject(josnStr);
        }else{
            josnStr = "{\"status\":\"lt0\"}";
            return JSON.parseObject(josnStr);
        }
    }
}
