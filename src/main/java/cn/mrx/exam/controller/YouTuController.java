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

/**
 * @ClassName: YouTuController
 * @Author: Mr.X
 * @Date: 2017/3/30 15:35
 * @Description:
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/yt")
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
        return "admin/yt/detectface";
    }

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

}
