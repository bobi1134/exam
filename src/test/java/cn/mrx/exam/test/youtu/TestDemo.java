package cn.mrx.exam.test.youtu;

import cn.mrx.exam.youtu.Youtu;
import cn.mrx.exam.youtu.pojo.detectface.DetectFace;
import cn.mrx.exam.youtu.pojo.detectface.Face;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: TestDemo
 * @Author: Mr.X
 * @Date: 2017/3/30 9:12
 * @Description:
 * @Version 1.0
 */
public class TestDemo {
    // appid, secretid secretkey请到http://open.youtu.qq.com/获取
    // 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
    public static final String APP_ID = "10078911";
    public static final String SECRET_ID = "AKIDJWYO3XovpbCbnptIjy2tZ4OWSBi3Jlrl";
    public static final String SECRET_KEY = "pY8XklfNtDMJ3bx9KbpunuqdFfsulPr7";
    public static final String USER_ID = "1451965355";


    //人脸检测：DetectFace
    @Test
    public void test01() throws Exception{
        Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
        JSONObject jsonObject = youtu.DetectFace("C:\\Users\\Mr.X\\Desktop\\20140086.jpg", 1);
//        JSONObject jsonObject = youtu.DetectFaceUrl("http://jiaowu.sicau.edu.cn/photo/20140086.jpg",1);
        System.out.println(jsonObject);
        DetectFace detectFace = JSON.parseObject(jsonObject.toString(), DetectFace.class);
        System.out.println(detectFace);
        System.out.println(detectFace.getFace().get(0).getExpression());
    }

    //五官分析：FaceShape
    @Test
    public void test02() throws Exception{
        Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
        JSONObject jsonObject = youtu.FaceShape("C:\\Users\\Mr.X\\Desktop\\20140064.jpg", 1);
//        JSONObject jsonObject = youtu.FaceShapeUrl("http://jiaowu.sicau.edu.cn/photo/20140086.jpg",1);
        System.out.println(jsonObject);
    }

    //人脸对比：FaceShape
    @Test
    public void test03() throws Exception{
        Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
        JSONObject jsonObject = youtu.FaceCompareUrl("http://jiaowu.sicau.edu.cn/photo/20140151.jpg", "http://jiaowu.sicau.edu.cn/photo/20140151.jpg");
        System.out.println(jsonObject);
    }

    @Test
    public void test04(){
        try {
            Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
            JSONObject jsonObject = youtu.FaceCompare("C:\\Users\\Mr.X\\Desktop\\876da2f7-a301-4275-845b-32997e7e43f9.png", "C:\\Users\\Mr.X\\Desktop\\876da2f7-a301-4275-845b-32997e7e43f9.png");
            System.out.println(jsonObject);
            System.out.println(jsonObject.get("errorcode"));
            System.out.println(jsonObject.get("errorcode")==null);
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.toString().substring(0, e.toString().indexOf(":")));
        }
    }
}
