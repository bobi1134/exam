package cn.mrx.exam.youtu.pojo.detectface;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: DetectFace
 * @Author: Mr.X
 * @Date: 2017/3/30 10:48
 * @Description:
 * @Version 1.0
 */
public class DetectFace {

    private List<Face> face;
    private int image_width;
    private int image_height;
    private String errorcode;
    private String errormsg;

    public List<Face> getFace() {
        return face;
    }

    public void setFace(List<Face> face) {
        this.face = face;
    }

    public int getImage_width() {
        return image_width;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    @Override
    public String toString() {
        return "DetectFace{" +
                "face=" + face +
                ", image_width=" + image_width +
                ", image_height=" + image_height +
                ", errorcode='" + errorcode + '\'' +
                ", errormsg='" + errormsg + '\'' +
                '}';
    }
}
