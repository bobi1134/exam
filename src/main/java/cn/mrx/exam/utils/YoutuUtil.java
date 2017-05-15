package cn.mrx.exam.utils;

import cn.mrx.exam.youtu.Youtu;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: YoutuUtil
 * @Author: Mr.X
 * @Date: 2017/5/11 13:44
 * @Description:
 * @Version 1.0
 */
public class YoutuUtil {


    /** 腾讯优图配置参数 */
    public static final String APP_ID = "10078911";
    public static final String SECRET_ID = "AKIDJWYO3XovpbCbnptIjy2tZ4OWSBi3Jlrl";
    public static final String SECRET_KEY = "pY8XklfNtDMJ3bx9KbpunuqdFfsulPr7";
    public static final String USER_ID = "1451965355";

    /**
     * 人脸检测
     * @param fileDir
     * @return
     * @throws Exception
     */
    public static JSONObject detectFace(String fileDir){
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
            JSONObject jsonObject = youtu.DetectFace(fileDir, 1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            String exceptionStr = e.toString().substring(0, e.toString().indexOf(":"));
            String json =  "{\"exception\":\""+exceptionStr+"\"}";
            return JSON.parseObject(json);
        }
    }

    /**
     * 五官定位
     * @param fileDir
     * @return
     * @throws Exception
     */
    public static JSONObject faceShape(String fileDir){
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
            JSONObject jsonObject = youtu.FaceShape(fileDir, 1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            String exceptionStr = e.toString().substring(0, e.toString().indexOf(":"));
            String json =  "{\"exception\":\""+exceptionStr+"\"}";
            return JSON.parseObject(json);
        }
    }

    /**
     * 人脸对比
     * @param fileDir1
     * @param fileDir2
     * @return
     * @throws Exception
     */
    public static JSONObject faceCompare(String fileDir1, String fileDir2) {
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
            JSONObject jsonObject = youtu.FaceCompare(fileDir1, fileDir2);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            String exceptionStr = e.toString().substring(0, e.toString().indexOf(":"));
            String json =  "{\"exception\":\""+exceptionStr+"\"}";
            return JSON.parseObject(json);
        }
    }
}
