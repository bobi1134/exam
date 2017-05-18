package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.utils.BSGridPage;
import cn.mrx.exam.utils.YoutuUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PhotoConfigAnalysisController
 * @Author: Mr.X
 * @Date: 2017/5/18 13:22
 * @Description: 过程分析Controller
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/photoConfigAnalysis")
public class PhotoConfigAnalysisController extends BaseController{

    /**
     * 技术支持页面（默认打开页面）
     * @param id
     * @return
     */
    @RequestMapping(value = "/technicalSupport/{id}", method = RequestMethod.GET)
    public String analysisPage(@PathVariable("id") String id, Model model){
        model.addAttribute("id", id);
        return "admin/photoConfigAnalysis/technicalSupport";
    }

    /**
     * 信息采集入库
     * @param id
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/informationCollect/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object analysis(@PathVariable("id") String id, HttpServletRequest httpServletRequest){
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

    /**
     * PhotoConfig - 查询在该采集规则时间段内的图片
     * @param id
     * @return
     */
    public List<Photo> selectPhotos(String id){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //该时间段内的图片库
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time" , startTime);
        photoEntityWrapper.lt("create_time" , endTime);
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        return photos;
    }

    /**
     * 采集成功率分析
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/successRate/{id}", method = RequestMethod.GET)
    public String analysisSuccessRate(@PathVariable("id") String id, Model model){
        List<Photo> photos = selectPhotos(id);

        //查数据库，分析数据，默认查看采集成功率（人脸分析、五官定位的采集成功率）
        int count = photos.size();
        int exception_Detectface = 0,  exception_Faceshape = 0, exception_FaceCompare = 0;//后台程序出错数量
        int errorcode_Detectface = 0, errorcode_Faceshape = 0, errorcode_FaceCompare = 0;//未检测成功数量
        for (Photo photo : photos){
            //人脸识别情况
            String resultDetectface = photo.getResultDetectface();
            JSONObject jsonObject = JSON.parseObject(resultDetectface);
            if (jsonObject.get("face") != null){
                if((int) jsonObject.get("errorcode") != 0) errorcode_Detectface++;
            }else {
                exception_Detectface++;
            }

            //五官定位情况
            String resultFaceshape = photo.getResultFaceshape();
            JSONObject jsonObject2 = JSON.parseObject(resultFaceshape);
            if (jsonObject2.get("face_shape") != null){
                if((int) jsonObject2.get("errorcode") != 0) errorcode_Faceshape++;
            }else {
                exception_Faceshape++;
            }

            //人脸对比情况
            String resultFacecompare = photo.getResultFacecompare();
            if(resultFacecompare != null){
                JSONObject jsonObject3 = JSON.parseObject(resultFacecompare);
                if(jsonObject3.get("errorcode") != null){
                    if((int)jsonObject3.get("errorcode") != 0) errorcode_FaceCompare++;
                }else {
                    exception_FaceCompare++;
                }
            }
        }

        //返回数据
        model.addAttribute("count", count);
        model.addAttribute("exception_Detectface", exception_Detectface);
        model.addAttribute("errorcode_Detectface", errorcode_Detectface);

        model.addAttribute("exception_Faceshape", exception_Faceshape);
        model.addAttribute("errorcode_Faceshape", errorcode_Faceshape);

        model.addAttribute("exception_FaceCompare", exception_FaceCompare);
        model.addAttribute("errorcode_FaceCompare", errorcode_FaceCompare);

        model.addAttribute("id", id);//必须返回
        return "admin/photoConfigAnalysis/collectSuccessRate";
    }

    /**
     * 采集成功率详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/successRateDetails/{id}", method = RequestMethod.GET)
    public String analysisSuccessRateDetails(@PathVariable("id") String id, Model model){
        model.addAttribute("id", id);
        return "admin/photoConfigAnalysis/collectSuccessRateDetails";
    }

    /**
     * 采集成功率详情分页异步查询
     * @param id
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/successRateDetails/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object analysisSuccessRateDetails(@PathVariable("id") String id, BSGridPage<Photo> bsGridPage, HttpServletRequest httpServletRequest){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(id);
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //查询在该区间段的所有照片
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time", startTime);
        photoEntityWrapper.lt("create_time", endTime);

        Page<Photo> photoPage = iPhotoService.selectPage(bsGridPage.getPage(), photoEntityWrapper);
        return bsGridPage.parsePage(photoPage);
    }

    /**
     * 过程分析页面（默认打开面部表情分析页面）
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/processFace/{id}", method = RequestMethod.GET)
    public String analysisProcess(@PathVariable("id") String id, Model model){
        List<Integer> expression = new ArrayList<>();
        List<Photo> photos = selectPhotos(id);
        for (Photo photo : photos){
            JSONObject jsonObject = JSON.parseObject(photo.getResultDetectface());
            if(jsonObject.get("face")!=null){
                JSONArray jsonArray = JSON.parseArray(jsonObject.get("face").toString());
                JSONObject jsonObject2 = (JSONObject)jsonArray.get(0);
                expression.add((int)jsonObject2.get("expression"));
            }
        }
        model.addAttribute("expression", expression);
        model.addAttribute("id", id);
        return "admin/photoConfigAnalysis/processFace";
    }
}
