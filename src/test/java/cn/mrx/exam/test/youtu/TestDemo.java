package cn.mrx.exam.test.youtu;

import cn.mrx.exam.youtu.Youtu;
import cn.mrx.exam.youtu.pojo.detectface.DetectFace;
import cn.mrx.exam.youtu.pojo.detectface.Face;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

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
//        JSONObject jsonObject = youtu.DetectFace("C:\\Users\\Mr.X\\Desktop\\test.jpg",1);
        JSONObject jsonObject = youtu.DetectFaceUrl("http://jiaowu.sicau.edu.cn/photo/20140086.jpg",1);
        System.out.println(jsonObject);
        DetectFace detectFace = JSON.parseObject(jsonObject.toString(), DetectFace.class);
        System.out.println(detectFace);
        System.out.println(detectFace.getFace().get(0).getExpression());
    }
}
