package cn.mrx.exam.test.mybatis;

import cn.mrx.exam.interceptor.PermissionCheck;
import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Author: Mr.X
 * Date: 2017/3/17
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-service.xml", "classpath:spring/spring-dao.xml"})
public class MybatisTest {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ISystemWebService iSystemWebService;
    @Autowired
    private IPermissionService iPermissionService;
    @Autowired
    private IPhotoService iPhotoService;


    @Test
    public void testInsert() {
        Photo photo = new Photo();
        photo.setResultJson("abcdefghijk");
        photo.setName("aaa.png");
        photo.setCreateTime(new Date());
        photo.setUserid(1);
        boolean result = iPhotoService.insert(photo);
        System.out.println(result);
    }

    @Test
    public void testUpdate() {
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.eq("name", "aaa.png");

        Photo photo = new Photo();
        photo.setResultJson("xxx");


        iPhotoService.update(photo, photoEntityWrapper);
    }

    @Test
    public void testSelect() {
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.gt("create_time", "2017-05-02 15:56:22");
        photoEntityWrapper.lt("create_time", "2017-05-08 16:56:22");
        List<Photo> photos = iPhotoService.selectList(photoEntityWrapper);
        System.out.println("-----------------------");
        System.out.println(photos.toString());
        System.out.println("-----------------------");
    }

}
