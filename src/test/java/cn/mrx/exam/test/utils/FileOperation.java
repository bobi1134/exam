package cn.mrx.exam.test.utils;

import org.junit.Test;

import java.io.File;

/**
 * @ClassName: FileOperation
 * @Author: Mr.X
 * @Date: 2017/5/22 22:00
 * @Description:
 * @Version 1.0
 */
public class FileOperation {

    @Test
    public void del(){
        File file = new File("C:\\Users\\Mr.X\\Desktop\\aaa.jpg");
        if(file.exists()){
            file.delete();
            System.out.println("已删除文件。。。");
        }
    }
}
