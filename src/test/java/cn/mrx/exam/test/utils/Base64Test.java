package cn.mrx.exam.test.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @ClassName: Base64Test
 * @Author: Mr.X
 * @Date: 2017/5/21 15:48
 * @Description:
 * @Version 1.0
 */


public class Base64Test {

    //图片转化成base64字符串
    public static String GetImageStr() {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "C:\\Users\\Mr.X\\Desktop\\aaa.jpg";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    //base64字符串转化成图片
    public static boolean GenerateImage(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "C:\\Users\\Mr.X\\Desktop\\bbb.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 对字节数组字符串进行Base64解码并生成图片
    public static boolean GenerateImage(String imgStr, OutputStream out) {
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
//        String strImg = GetImageStr();
//        System.out.println(strImg);
        //GenerateImage(strImg);
        GenerateImage("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");
    }
}
