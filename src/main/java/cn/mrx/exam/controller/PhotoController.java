package cn.mrx.exam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-03
 */
@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拍摄检测
     * @return
     */
    @RequestMapping(value = "/detectface", method = RequestMethod.GET)
    public String collect(){
        return "admin/photo/detectface";
    }

    /**
     * 上传图片
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/resources/admin/upload/photo/");
        String newName = UUID.randomUUID() + ".png";
        //默认传入的参数带类型等参数：data:image/png;base64,
        String imgStr = httpServletRequest.getParameter("image");
        if (null != imgStr) {
            imgStr = imgStr.substring(imgStr.indexOf(",") + 1);
        }
        Boolean flag = generateImage(imgStr, realPath, newName);
        String result = "";
        if (flag) {
            result = realPath + newName;
        }

        JSONObject jsonObject = JSON.parseObject("{'flag':'"+flag+"', 'fileName':'"+newName+"'}");
        return jsonObject;
    }

    /**
     * 功能描述：base64字符串转换成图片
     * @param imgStr
     * @param filePath
     * @param fileName
     * @return
     */
    public boolean generateImage(String imgStr, String filePath, String fileName) {
        try {
            if (imgStr == null) {
                return false;
            }
            BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            //如果目录不存在，则创建
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //生成图片
            OutputStream out = new FileOutputStream(filePath + fileName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            logger.error("生成图片异常：{}", e.getMessage());
            return false;
        }
    }
}
