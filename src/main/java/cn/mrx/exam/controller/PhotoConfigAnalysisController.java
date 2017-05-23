package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.pojo.User;
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
     * @param studentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/technicalSupport/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String technicalSupport(@PathVariable("photoConfigId") String photoConfigId,
                                   @PathVariable("studentId") String studentId,
                                   Model model){
        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/technicalSupport";
    }

    /**
     * 通通查询封装：PhotoConfig表 - 查询在该采集规则时间段内的图片，返回photos
     * @param photoConfigId
     * @param studentId
     * @return
     */
    public List<Photo> selectPhotos(String photoConfigId, String studentId){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(photoConfigId);
        //将Date转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(photoConfig.getStartTime());
        String endTime = sdf.format(photoConfig.getEndTime());
        //该时间段内的图片库
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time" , startTime);
        photoEntityWrapper.lt("create_time" , endTime);
        photoEntityWrapper.eq("user_id", studentId);
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        return photos;
    }

    /**
     * 采集成功率分析
     * @param photoConfigId
     * @param studentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/successRate/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String successRate(@PathVariable("photoConfigId") String photoConfigId,
                              @PathVariable("studentId") String studentId,
                              Model model){
        List<Photo> photos = selectPhotos(photoConfigId, studentId);

        //查数据库，分析数据，默认查看采集成功率（人脸分析、五官定位的采集成功率）
        int count = photos.size();
        int exception_Detectface = 0,  exception_Faceshape = 0, exception_FaceCompare = 0;//后台程序出错
        int errorcode_x_Detectface = 0, errorcode_x_Faceshape = 0, errorcode_x_FaceCompare = 0;//已解析，但是未检测成功
        int errorcode_0_Detectface = 0, errorcode_0_Faceshape = 0, errorcode_0_FaceCompare = 0;//已解析，并且检测成功
        for (Photo photo : photos){
            //人脸分析情况
            String resultDetectface = photo.getResultDetectface();
            if(resultDetectface!=null && !resultDetectface.trim().equals("")){
                JSONObject jsonObject1 = JSON.parseObject(resultDetectface);
                if (jsonObject1.get("face")==null){
                    exception_Detectface++;//程序异常，如网络不通，文件没找到等
                }else if((int) jsonObject1.get("errorcode")!=0) {
                    errorcode_x_Detectface++;//优图未识别成功
                }else if((int) jsonObject1.get("errorcode")==0){
                    errorcode_0_Detectface++;//优图识别成功
                }
            }

            //五官定位情况
            String resultFaceshape = photo.getResultFaceshape();
            if(resultFaceshape!=null && !resultFaceshape.trim().equals("")){
                JSONObject jsonObject2 = JSON.parseObject(resultFaceshape);
                if (jsonObject2.get("face_shape") == null){
                    exception_Faceshape++;//程序异常，如网络不通，文件没找到等
                }else if((int) jsonObject2.get("errorcode")!=0){
                    errorcode_x_Faceshape++;//优图未识别成功
                }else if((int) jsonObject2.get("errorcode")==0){
                    errorcode_0_Faceshape++;//优图识别成功
                }
            }

            //人脸对比情况
            String resultFacecompare = photo.getResultFacecompare();
            if(resultFacecompare!=null && !resultFacecompare.trim().equals("")){
                JSONObject jsonObject3 = JSON.parseObject(resultFacecompare);
                if(jsonObject3.get("errorcode") == null){
                    exception_FaceCompare++;//程序异常，如网络不通，文件没找到等
                }else if((int) jsonObject3.get("errorcode")!=0) {
                    errorcode_x_FaceCompare++;//优图未识别成功
                }else if((int) jsonObject3.get("errorcode")==0){
                    errorcode_0_FaceCompare++;////优图识别成功
                }
            }
        }

        //返回数据
        model.addAttribute("count", count);
        model.addAttribute("exception_Detectface", exception_Detectface);
        model.addAttribute("errorcode_x_Detectface", errorcode_x_Detectface);
        model.addAttribute("errorcode_0_Detectface", errorcode_0_Detectface);

        model.addAttribute("exception_Faceshape", exception_Faceshape);
        model.addAttribute("errorcode_x_Faceshape", errorcode_x_Faceshape);
        model.addAttribute("errorcode_0_Faceshape", errorcode_0_Faceshape);

        model.addAttribute("exception_FaceCompare", exception_FaceCompare);
        model.addAttribute("errorcode_x_FaceCompare", errorcode_x_FaceCompare);
        model.addAttribute("errorcode_0_FaceCompare", errorcode_0_FaceCompare);

        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/collectSuccessRate";
    }

    /**
     * 采集成功率详情页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/successRateDetails/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String successRateDetails(@PathVariable("photoConfigId") String photoConfigId,
                                     @PathVariable("studentId") String studentId,
                                     Model model){
        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/collectSuccessRateDetails";
    }

    /**
     * 采集成功率详情分页异步查询
     * @param bsGridPage
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/successRateDetails/{photoConfigId}/{studentId}", method = RequestMethod.POST)
    @ResponseBody
    public Object successRateDetails(@PathVariable("photoConfigId") String photoConfigId,
                                     @PathVariable("studentId") String studentId,
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
        photoEntityWrapper.eq("user_id", studentId);

        Page<Photo> photoPage = iPhotoService.selectPage(bsGridPage.getPage(), photoEntityWrapper);
        return bsGridPage.parsePage(photoPage);
    }

    /**
     * 态度分析（默认打开分析页面）
     * @param photoConfigId
     * @param studentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/attitude/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String attitude(@PathVariable("photoConfigId") String photoConfigId,
                           @PathVariable("studentId") String studentId,
                           Model model){
        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        int expressions1=0,expressions2=0,expressions3=0,expressions4=0,expressions5=0,
                expressions6=0,expressions7=0,expressions8=0,expressions9=0,expressions10=0;
        for (Photo photo : photos){
            String resultDetectface = photo.getResultDetectface();
            JSONObject jsonObject1 = JSON.parseObject(resultDetectface);
            if (jsonObject1.get("errorcode")!=null && (int)jsonObject1.get("errorcode")==0){
                JSONObject jsonObject2 = JSON.parseObject(JSON.parseArray(jsonObject1.get("face").toString()).get(0).toString());
                int expression =  (int)jsonObject2.get("expression");
                if(expression>=0 && expression<=10){
                    expressions1++;
                }else if(expression>10 && expression<=20){
                    expressions2++;
                }else if(expression>20 && expression<=30){
                    expressions3++;
                }else if(expression>30 && expression<=40){
                    expressions4++;
                }else if(expression>40 && expression<=50){
                    expressions5++;
                }else if(expression>50 && expression<=60){
                    expressions6++;
                }else if(expression>60 && expression<=70){
                    expressions7++;
                }else if(expression>70 && expression<=80){
                    expressions8++;
                }else if(expression>80 && expression<=90){
                    expressions9++;
                }else if(expression>90 && expression<=100){
                    expressions10++;
                }
            }
        }

        model.addAttribute("count", photos.size());
        model.addAttribute("expressions1", expressions1);
        model.addAttribute("expressions2", expressions2);
        model.addAttribute("expressions3", expressions3);
        model.addAttribute("expressions4", expressions4);
        model.addAttribute("expressions5", expressions5);
        model.addAttribute("expressions6", expressions6);
        model.addAttribute("expressions7", expressions7);
        model.addAttribute("expressions8", expressions8);
        model.addAttribute("expressions9", expressions9);
        model.addAttribute("expressions10", expressions10);

        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/attitude";
    }

    /**
     * 面部表情分析
     * @param model
     * @return
     */
    @RequestMapping(value = "/processFace/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String processFace(@PathVariable("photoConfigId") String photoConfigId,
                              @PathVariable("studentId") String studentId,
                              Model model){
        List<Integer> expression = new ArrayList<>();
        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        for (Photo photo : photos){
            JSONObject jsonObject = JSON.parseObject(photo.getResultDetectface());
            if(jsonObject.get("face")!=null && (int)jsonObject.get("errorcode")==0){
                JSONArray jsonArray = JSON.parseArray(jsonObject.get("face").toString());
                JSONObject jsonObject2 = (JSONObject)jsonArray.get(0);
                expression.add((int)jsonObject2.get("expression"));
            }
        }
        model.addAttribute("expression", expression);

        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/processFace";
    }

    /**
     * 学生转向问题分析
     * @param photoConfigId
     * @param studentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/turnAround/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String turnAround(@PathVariable("photoConfigId") String photoConfigId,
                             @PathVariable("studentId") String studentId,
                             Model model){
        //准备数据...
        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        List<Integer> result = new ArrayList<>();
        for (Photo photo : photos){
            String resultFaceshape = photo.getResultFaceshape();
            JSONObject jsonObject1 = JSON.parseObject(resultFaceshape);
            //解析成功的情况
            if (jsonObject1.get("errorcode")!=null && (int)jsonObject1.get("errorcode")==0){
                JSONObject jsonObject2 = JSON.parseObject(JSON.parseArray(jsonObject1.get("face_shape").toString()).get(0).toString());
                JSONObject jsonObject3 = JSON.parseObject(JSON.parseArray(jsonObject2.get("nose").toString()).get(0).toString());
                Integer x = (Integer) jsonObject3.get("x");
                Integer y = (Integer) jsonObject3.get("y");
                System.out.println("x:"+x+",y:"+y);
                //右下左中情况
                //左(0~33)、中（34~66）、右（67~100）
                //左
                if(x>0 && x < 120){
                    result.add(0+33*(x/120));
                }

                //中
                if(x>120 && x<200){
                    result.add(34+33*(x/120));
                }

                //右
                if(x>200 && x < 320){
                    result.add(67+33*(x/120));
                }
            }else{
                result.add(0);
            }
        }
        model.addAttribute("result", result);

        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/turnAround";
    }

    /**
     * 分析是否换人
     * @param photoConfigId
     * @param studentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/changePeople/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String changePeople(@PathVariable("photoConfigId") String photoConfigId,
                             @PathVariable("studentId") String studentId,
                             Model model){

        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        int selfNum = 0, count = 0;
        List<Double> similaritys = new ArrayList<>();
        for (Photo photo : photos){
            JSONObject jsonObject = JSON.parseObject(photo.getResultFacecompare());
            //保证解析正确
            if(jsonObject.get("errorcode")!=null && (int)jsonObject.get("errorcode")==0){
                Double similarity = Double.parseDouble(jsonObject.get("similarity").toString());
                if(similarity>70){
                    selfNum++;
                }
                similaritys.add(similarity);
                count++;
            }
        }

        model.addAttribute("similaritys", similaritys);
        model.addAttribute("selfNum", selfNum);
        model.addAttribute("count", count);

        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/changePeople";
    }
}
