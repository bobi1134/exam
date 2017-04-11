package cn.mrx.exam.controller;

import cn.mrx.exam.youtu.Youtu;
import cn.mrx.exam.youtu.pojo.detectface.DetectFace;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @ClassName: YouTuController
 * @Author: Mr.X
 * @Date: 2017/3/30 15:35
 * @Description:
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/youtu")
public class YouTuController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 腾讯优图配置参数 */
    public static final String APP_ID = "10078911";
    public static final String SECRET_ID = "AKIDJWYO3XovpbCbnptIjy2tZ4OWSBi3Jlrl";
    public static final String SECRET_KEY = "pY8XklfNtDMJ3bx9KbpunuqdFfsulPr7";
    public static final String USER_ID = "1451965355";

    /**
     * 跳转到detectface.jsp页面
     * @return
     */
    @RequestMapping(value = "/detectface", method = RequestMethod.GET)
    public String detectFace(){
        return "admin/youtu/detectface";
    }

    /**
     * URL方式人脸检测
     * @param url
     * @return
     */
    @RequestMapping(value = "/detectface", method = RequestMethod.POST)
    @ResponseBody
    public Object detectFaceUrl(String url){
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
            JSONObject jsonObject = youtu.DetectFaceUrl(url, 1);
            DetectFace detectFace = JSON.parseObject(jsonObject.toString(), DetectFace.class);
            return detectFace;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 上传图片人脸检测
     * @param photo
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detectface_upload", method = RequestMethod.POST)
    @ResponseBody
    public Object detectFace(MultipartFile photo, HttpServletRequest httpServletRequest) throws  Exception{
        if (photo != null) {
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/youtu/detectface/upload/");
            String newName = UUID.randomUUID()+photo.getOriginalFilename().substring(photo.getOriginalFilename().indexOf("."));
            File newFile = new File(realPath, newName);
            if(!newFile.exists()){
                //mkdir()只能建立一级文件夹。mkdirs()可以建立多级文件夹
                newFile.mkdirs();
            }
            photo.transferTo(newFile);
            // 人脸检测
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
            JSONObject jsonObject = youtu.DetectFace(realPath + newName, 1);
            DetectFace detectFace = JSON.parseObject(jsonObject.toString(), DetectFace.class);
            return detectFace;
        }
        return null;
    }

    /**
     * 跳转到faceshape.jsp页面
     * @return
     */
    @RequestMapping(value = "/faceshape", method = RequestMethod.GET)
    public String faceShape(){
        return "admin/youtu/faceshape";
    }

    /**
     * URL方式五官定位
     * @param url
     * @return
     */
    @RequestMapping(value = "/faceshape", method = RequestMethod.POST)
    @ResponseBody
    public Object faceShape(String url){
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
            JSONObject jsonObject = youtu.FaceShapeUrl(url, 1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传图片五官定位
     * @param photo
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/faceshape_upload", method = RequestMethod.POST)
    @ResponseBody
    public Object faceShape(MultipartFile photo, HttpServletRequest httpServletRequest) throws  Exception{
        if (photo != null) {
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/youtu/faceshape/upload/");
            String newName = UUID.randomUUID()+photo.getOriginalFilename().substring(photo.getOriginalFilename().indexOf("."));
            File newFile = new File(realPath, newName);
            if(!newFile.exists()) newFile.mkdirs();
            photo.transferTo(newFile);
            // 五官定位
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
            JSONObject jsonObject = youtu.FaceShape(realPath+newName, 1);
            return jsonObject;
        }
        return null;
    }

    /**
     * 跳转到faceshape.jsp页面
     * @return
     */
    @RequestMapping(value = "/facecompare", method = RequestMethod.GET)
    public String faceCompare(){
        return "admin/youtu/facecompare";
    }

    /**
     * URL方式人脸对比
     * @param url1
     * @param url2
     * @return
     */
    @RequestMapping(value = "/facecompare", method = RequestMethod.POST)
    @ResponseBody
    public Object faceCompare(String url1, String url2){
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
            JSONObject jsonObject = youtu.FaceCompareUrl(url1, url2);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 人脸对比-上传图片
     * @param photo
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/facecompare/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object faceCompareUpload(MultipartFile photo, HttpServletRequest httpServletRequest)throws  Exception{
        if (photo != null) {
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/youtu/facecompare/upload/");
            String newName = UUID.randomUUID()+photo.getOriginalFilename().substring(photo.getOriginalFilename().indexOf("."));
            File newFile = new File(realPath, newName);
            if(!newFile.exists()) newFile.mkdirs();
            photo.transferTo(newFile);
            String path = (realPath+newName).replace("\\", "\\\\");
            return "{result:0, path:'"+ path +"'}";
        }
        return "{result:-1}";
    }

    /**
     * 图片上传方式人脸对比
     * @param path1
     * @param path2
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/facecompare_path", method = RequestMethod.POST)
    @ResponseBody
    public Object faceCompareByPath(String path1, String path2)throws  Exception{
        Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
        JSONObject jsonObject = youtu.FaceCompare(path1, path2);
        return jsonObject;
    }

}
