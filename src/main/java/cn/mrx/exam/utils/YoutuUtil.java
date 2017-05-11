package cn.mrx.exam.utils;

import cn.mrx.exam.youtu.Youtu;
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

    public static Youtu youtu;

    /**
     * 构造器创建实例
     */
    private YoutuUtil(){
        youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT, USER_ID);
    }

    /**
     * 人脸检测
     * @param fileDir
     * @return
     * @throws Exception
     */
    public static JSONObject detectFace(String fileDir){
        try {
            JSONObject jsonObject = youtu.DetectFace(fileDir, 1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            JSONObject jsonObject = youtu.FaceShape(fileDir, 1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 人脸对比
     * @param fileDir1
     * @param fileDir2
     * @return
     * @throws Exception
     */
    public static JSONObject faceShape(String fileDir1, String fileDir2) {
        try {
            JSONObject jsonObject = youtu.FaceCompare(fileDir1, fileDir2);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
