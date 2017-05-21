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
     * @param photoConfigId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/technicalSupport/{photoConfigId}/{userId}", method = RequestMethod.GET)
    public String analysisPage(@PathVariable("photoConfigId") String photoConfigId,
                               @PathVariable("userId") String userId,
                               Model model){
        model.addAttribute("photoConfigId", photoConfigId);
        model.addAttribute("userId", userId);
        return "admin/photoConfigAnalysis/technicalSupport";
    }

    /**
     * 通通查询封装：PhotoConfig表 - 查询在该采集规则时间段内的图片，返回photos
     * @param photoConfigId
     * @param userId
     * @return
     */
    public List<Photo> selectPhotos(String photoConfigId, String userId){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(photoConfigId);
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //该时间段内的图片库
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time" , startTime);
        photoEntityWrapper.lt("create_time" , endTime);
        photoEntityWrapper.eq("user_id", userId);
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        return photos;
    }

    /**
     * 采集成功率分析
     * @param photoConfigId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/successRate/{photoConfigId}/{userId}", method = RequestMethod.GET)
    public String analysisSuccessRate(@PathVariable("photoConfigId") String photoConfigId,
                                      @PathVariable("userId") String userId,
                                      Model model){
        List<Photo> photos = selectPhotos(photoConfigId, userId);

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

        model.addAttribute("photoConfigId", photoConfigId);
        model.addAttribute("userId", userId);
        return "admin/photoConfigAnalysis/collectSuccessRate";
    }

    /**
     * 采集成功率详情页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/successRateDetails/{photoConfigId}/{userId}", method = RequestMethod.GET)
    public String analysisSuccessRateDetails(@PathVariable("photoConfigId") String photoConfigId,
                                             @PathVariable("userId") String userId,
                                             Model model){
        model.addAttribute("photoConfigId", photoConfigId);
        model.addAttribute("userId", userId);
        return "admin/photoConfigAnalysis/collectSuccessRateDetails";
    }

    /**
     * 采集成功率详情分页异步查询
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/successRateDetails/{photoConfigId}/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public Object analysisSuccessRateDetails(@PathVariable("photoConfigId") String photoConfigId,
                                             @PathVariable("userId") String userId,
                                             BSGridPage<Photo> bsGridPage,
                                             HttpServletRequest httpServletRequest){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(photoConfigId);
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //查询在该区间段的所有照片
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time", startTime);
        photoEntityWrapper.lt("create_time", endTime);
        photoEntityWrapper.eq("user_id", userId);

        Page<Photo> photoPage = iPhotoService.selectPage(bsGridPage.getPage(), photoEntityWrapper);
        return bsGridPage.parsePage(photoPage);
    }

    /**
     * 过程分析页面（默认打开面部表情分析页面）
     * @param model
     * @return
     */
    @RequestMapping(value = "/processFace/{photoConfigId}/{userId}", method = RequestMethod.GET)
    public String analysisProcess(@PathVariable("photoConfigId") String photoConfigId,
                                  @PathVariable("userId") String userId,
                                  Model model){
        List<Integer> expression = new ArrayList<>();
        List<Photo> photos = selectPhotos(photoConfigId, userId);
        for (Photo photo : photos){
            JSONObject jsonObject = JSON.parseObject(photo.getResultDetectface());
            if(jsonObject.get("face")!=null){
                JSONArray jsonArray = JSON.parseArray(jsonObject.get("face").toString());
                JSONObject jsonObject2 = (JSONObject)jsonArray.get(0);
                expression.add((int)jsonObject2.get("expression"));
            }
        }
        model.addAttribute("expression", expression);
        model.addAttribute("photoConfigId", photoConfigId);
        model.addAttribute("userId", userId);
        return "admin/photoConfigAnalysis/processFace";
    }
}
