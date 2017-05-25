package cn.mrx.exam.controller;

import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.pojo.PhotoConfigAnalysis;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.utils.BSGridPage;
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
        return "admin/photoConfigAnalysis/analysisTechnicalSupport";
    }

    /**
     * 通用查询封装：PhotoConfig表 - 查询在该采集规则时间段内的图片，返回photos
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
        model.addAttribute("count", photos.size());
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
        return "admin/photoConfigAnalysis/analysisCollectSuccessRate";
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
        return "admin/photoConfigAnalysis/analysisCollectSuccessRateDetails";
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
     * 通用插入或修改采集配置分析结果
     * @param photoConfigId
     * @param studentId
     * @param columnName
     * @param res
     * @return
     */
    public boolean insertOrUpdatePhotoConfigAnalysis(int photoConfigId, int studentId, String columnName, String res){
        EntityWrapper<PhotoConfigAnalysis> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("photoConfig_id", photoConfigId);
        entityWrapper.eq("student_id", studentId);
        PhotoConfigAnalysis selectOne = iPhotoConfigAnalysisService.selectOne(entityWrapper);

        //写入数据库
        PhotoConfigAnalysis photoConfigAnalysis = new PhotoConfigAnalysis();
        if(selectOne!=null) photoConfigAnalysis.setId(selectOne.getId());//主键id
        switch(columnName){
            case "face":
                photoConfigAnalysis.setFace(res);
                break;
            case "turn_around":
                photoConfigAnalysis.setTurnAround(res);
                break;
            case "change_people":
                photoConfigAnalysis.setChangePeople(res);
                break;
        }
        photoConfigAnalysis.setPhotoconfigId(photoConfigId);
        photoConfigAnalysis.setStudentId(studentId);
        return iPhotoConfigAnalysisService.insertOrUpdate(photoConfigAnalysis);
    }

    /**
     * 面部表情分析（默认打开分析页面）
     * @param model
     * @return
     */
    @RequestMapping(value = "/faceExpression/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String faceExpression(@PathVariable("photoConfigId") String photoConfigId,
                              @PathVariable("studentId") String studentId,
                              Model model){
        //图1、面部表情分布图：所有图片的汇总信息：0~10,10~20...按10分类区分
        int[] expressionCountArray = selectExpressionCountArray(photoConfigId, studentId);

        //图2、面部表情走势图：所有图片的表情
        List<Integer> expressions = new ArrayList<>();
        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        int count = 0;//定义成功解析的照片总数
        for (Photo photo : photos){
            JSONObject jsonObject = JSON.parseObject(photo.getResultDetectface());
            if(jsonObject.get("face")!=null && (int)jsonObject.get("errorcode")==0){
                JSONArray jsonArray = JSON.parseArray(jsonObject.get("face").toString());
                JSONObject jsonObject2 = (JSONObject)jsonArray.get(0);
                expressions.add((int)jsonObject2.get("expression"));
                count++;
            }
        }

        //@ 将面部表情走势图数据保存到数据库
        String res = "";
        for (int i : expressions)  res = res + String.valueOf(i) + ",";
        boolean xxx = insertOrUpdatePhotoConfigAnalysis(Integer.valueOf(photoConfigId), Integer.valueOf(studentId), "face", res);

        model.addAttribute("expressions", expressions);
        model.addAttribute("count", count);
        model.addAttribute("expressionCountArray", expressionCountArray);
        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/processFaceExpression";
    }

    /**
     * 面部表情分布图：查询所有图片的汇总信息：0~10,10~20...按10分类区分
     * @param photoConfigId
     * @param studentId
     * @return
     */
    public int[] selectExpressionCountArray(String photoConfigId, String studentId){
        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        int[] expressionCountArray = new int[10];
        for (Photo photo : photos){
            String resultDetectface = photo.getResultDetectface();
            JSONObject jsonObject1 = JSON.parseObject(resultDetectface);
            if (jsonObject1.get("errorcode")!=null && (int)jsonObject1.get("errorcode")==0){
                JSONObject jsonObject2 = JSON.parseObject(JSON.parseArray(jsonObject1.get("face").toString()).get(0).toString());
                int expression =  (int)jsonObject2.get("expression");
                if(expression>=0 && expression<=10){
                    expressionCountArray[0]++;
                }else if(expression>10 && expression<=20){
                    expressionCountArray[1]++;
                }else if(expression>20 && expression<=30){
                    expressionCountArray[2]++;
                }else if(expression>30 && expression<=40){
                    expressionCountArray[3]++;
                }else if(expression>40 && expression<=50){
                    expressionCountArray[4]++;
                }else if(expression>50 && expression<=60){
                    expressionCountArray[5]++;
                }else if(expression>60 && expression<=70){
                    expressionCountArray[6]++;
                }else if(expression>70 && expression<=80){
                    expressionCountArray[7]++;
                }else if(expression>80 && expression<=90){
                    expressionCountArray[8]++;
                }else if(expression>90 && expression<=100){
                    expressionCountArray[9]++;
                }
            }
        }
        return expressionCountArray;
    }

    /**
     * 学生转向问题分析（根据五官定位的坐标来定位）
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
            if (jsonObject1.get("errorcode")!=null && (int)jsonObject1.get("errorcode")==0){//解析成功的情况
                JSONObject jsonObject2 = JSON.parseObject(JSON.parseArray(jsonObject1.get("face_shape").toString()).get(0).toString());
                JSONObject jsonObject3 = JSON.parseObject(JSON.parseArray(jsonObject2.get("nose").toString()).get(0).toString());
                Integer x = (Integer) jsonObject3.get("x");
                //左(0~33)、中（34~66）、右（67~100）
                //左
                if(x>0 && x < 120){
                    result.add(0 + (int)(Math.random()*33+1));
                }

                //中
                if(x>120 && x<200){
                    result.add(34 + (int)(Math.random()*32+1));
                }

                //右
                if(x>200 && x < 320){
                    result.add(67 + (int)(Math.random()*33+1));
                }
            }
        }

        //@ 更新进数据库
        String res = "";
        for (int i : result)  res = res + String.valueOf(i) + ",";
        boolean xxx = insertOrUpdatePhotoConfigAnalysis(Integer.valueOf(photoConfigId), Integer.valueOf(studentId), "turn_around", res);

        model.addAttribute("result", result);
        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/processTurnAround";
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
                if(similarity>=60){
                    selfNum++;
                }
                similaritys.add(similarity);
                count++;
            }
        }

        //@ 更新进数据库
        String res = "";
        for (Double i : similaritys)  res = res + String.valueOf(i) + ",";
        boolean xxx = insertOrUpdatePhotoConfigAnalysis(Integer.valueOf(photoConfigId), Integer.valueOf(studentId), "change_people", res);

        model.addAttribute("similaritys", similaritys);
        model.addAttribute("selfNum", selfNum);
        model.addAttribute("count", count);
        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/processChangePeople";
    }

    /**
     * 意见分析
     * @param photoConfigId
     * @param studentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/advice/{photoConfigId}/{studentId}", method = RequestMethod.GET)
    public String advice(@PathVariable("photoConfigId") String photoConfigId,
                               @PathVariable("studentId") String studentId,
                               Model model){
        //图表信息
        EntityWrapper<PhotoConfigAnalysis> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("photoConfig_id", photoConfigId);
        entityWrapper.eq("student_id", studentId);
        PhotoConfigAnalysis photoConfigAnalysis = iPhotoConfigAnalysisService.selectOne(entityWrapper);
        model.addAttribute("photoConfigAnalysis", photoConfigAnalysis);

        //文字信息
        //@、是否戴眼镜
        List<Photo> photos = selectPhotos(photoConfigId, studentId);
        JSONObject jsonObject1 = null;
        for (Photo photo : photos){
            String resultDetectface = photo.getResultDetectface();//人脸分析
            jsonObject1 = JSON.parseObject(resultDetectface);
            if (jsonObject1.get("errorcode")!=null && (int)jsonObject1.get("errorcode")==0) break;
        }
        JSONObject jsonObject2 = JSON.parseObject(JSON.parseArray(jsonObject1.get("face").toString()).get(0).toString());
        model.addAttribute("glass", jsonObject2.get("glass"));

        //@、表情分布情况
        int[] expressions = selectExpressionCountArray(photoConfigId, studentId);
        model.addAttribute("expressions", expressions);

        //@、转向分布情况
        String turnAroundStr = photoConfigAnalysis.getTurnAround();
        int[] turnOrder = new int[3];
        if(turnAroundStr!=null && !turnAroundStr.trim().equals("")){
            for(String str : turnAroundStr.split(",")){
                int x = Integer.valueOf(str);
                if(x>=0 && x < 33) turnOrder[0]++;
                if(x>33 && x<66) turnOrder[1]++;
                if(x>66 && x <= 100) turnOrder[2]++;
            }
        }
        model.addAttribute("turnOrder", turnOrder);

        //@、中途换人情况
        String changePeopleStr = photoConfigAnalysis.getChangePeople();
        int[] changePeopleOrder = new int[3];
        if(changePeopleStr!=null && !changePeopleStr.trim().equals("")){
            String[] array = changePeopleStr.split(",");
            for(int i=0; i<array.length; i++){
                double similarity = Double.parseDouble(array[i]);
                if(similarity>=85) changePeopleOrder[0]++;
                if(similarity>=60 && similarity<85) changePeopleOrder[1]++;
                if(similarity<60) changePeopleOrder[2]++;
            }
        }
        model.addAttribute("changePeopleOrder", changePeopleOrder);

        //@、其他情况
        String[] faceArr = new String[3];
        for (Photo photo : photos){
            String resultDetectface = photo.getResultDetectface();//人脸分析
            jsonObject1 = JSON.parseObject(resultDetectface);
            if (jsonObject1.get("errorcode")!=null && (int)jsonObject1.get("errorcode")==0){
                JSONObject xxx = JSON.parseObject(JSON.parseArray(jsonObject1.get("face").toString()).get(0).toString());
                faceArr[0] = String.valueOf(xxx.get("gender"));
                faceArr[1] = String.valueOf(xxx.get("age"));
                faceArr[2] = String.valueOf(xxx.get("beauty"));
                break;
            }
        }
        model.addAttribute("faceArr", faceArr);

        //返回photoConfigId和根据studentId查询出来的User
        model.addAttribute("photoConfigId", photoConfigId);
        User student = iUserService.selectById(studentId);
        model.addAttribute("student", student);
        return "admin/photoConfigAnalysis/processAdvice";
    }

    @RequestMapping(value = "/all/{photoConfigId}", method = RequestMethod.GET)
    public String all(@PathVariable("photoConfigId") String photoConfigId,
                      Model model){
        PhotoConfig photoConfig = iPhotoConfigService.selectById(photoConfigId);
        String userIds = photoConfig.getUserIds();//应考人
        if(userIds!=null && !userIds.trim().equals("")){
            //表情统计
//            selectExpressionCountArray()
            int[] expressionCountArray = new int[10];
            for(String userId : userIds.split(",")){


            }
        }

        model.addAttribute("photoConfig", photoConfig);
        return "admin/photoConfigAnalysis/all";
    }
}
