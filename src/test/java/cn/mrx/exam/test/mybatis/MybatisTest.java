package cn.mrx.exam.test.mybatis;

import cn.mrx.exam.interceptor.PermissionCheck;
import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.service.*;
import cn.mrx.exam.utils.YoutuUtil;
import cn.mrx.exam.youtu.Youtu;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
    @Autowired
    private IPhotoConfigService iPhotoConfigService;


    @Test
    public void testInsert() {
        Photo photo = new Photo();
        photo.setResultFaceshape("abcdefghijk");
        photo.setName("aaa.png");
        photo.setCreateTime(new Date());
        photo.setUserId(1);
        boolean result = iPhotoService.insert(photo);
        System.out.println(result);
    }

    @Test
    public void testUpdate() {
        EntityWrapper<Photo> photoEntityWrapper = new EntityWrapper<>();
        photoEntityWrapper.eq("name", "aaa.png");

        Photo photo = new Photo();
        photo.setResultFaceshape("xxx");


        iPhotoService.update(photo, photoEntityWrapper);
    }

    @Test
    public void testSelect() {
        EntityWrapper<PhotoConfig> photoConfigEntityWrapper = new EntityWrapper<>();
        photoConfigEntityWrapper.eq("user_id", 2);

        Page<PhotoConfig> photoConfigPage = iPhotoConfigService.selectPhotoConfigPage(new Page<>(0, 5), new EntityWrapper<>());
        System.out.println("------------------------------");
        System.out.println(photoConfigPage.getRecords());
        System.out.println("------------------------------");
    }

    @Test
    public void testYoutuUtil() {
        JSONObject jsonObject = JSON.parseObject("{\"face\":[{\"glass\":false,\"expression\":0,\"face_shape\":{\"right_eye\":[{\"x\":196,\"y\":144},{\"x\":192,\"y\":146},{\"x\":187,\"y\":147},{\"x\":182,\"y\":146},{\"x\":178,\"y\":145},{\"x\":182,\"y\":141},{\"x\":187,\"y\":140},{\"x\":192,\"y\":141}],\"nose\":[{\"x\":161,\"y\":173},{\"x\":162,\"y\":145},{\"x\":158,\"y\":153},{\"x\":154,\"y\":160},{\"x\":150,\"y\":168},{\"x\":145,\"y\":176},{\"x\":153,\"y\":181},{\"x\":160,\"y\":183},{\"x\":168,\"y\":182},{\"x\":176,\"y\":178},{\"x\":172,\"y\":169},{\"x\":169,\"y\":161},{\"x\":165,\"y\":153}],\"face_profile\":[{\"x\":108,\"y\":139},{\"x\":108,\"y\":150},{\"x\":108,\"y\":162},{\"x\":109,\"y\":173},{\"x\":111,\"y\":185},{\"x\":115,\"y\":195},{\"x\":121,\"y\":205},{\"x\":128,\"y\":214},{\"x\":136,\"y\":222},{\"x\":146,\"y\":227},{\"x\":157,\"y\":230},{\"x\":168,\"y\":228},{\"x\":178,\"y\":222},{\"x\":187,\"y\":216},{\"x\":195,\"y\":208},{\"x\":201,\"y\":199},{\"x\":206,\"y\":188},{\"x\":210,\"y\":178},{\"x\":213,\"y\":167},{\"x\":215,\"y\":155},{\"x\":215,\"y\":145}],\"mouth\":[{\"x\":141,\"y\":199},{\"x\":146,\"y\":203},{\"x\":152,\"y\":206},{\"x\":159,\"y\":207},{\"x\":165,\"y\":207},{\"x\":172,\"y\":205},{\"x\":177,\"y\":201},{\"x\":171,\"y\":198},{\"x\":165,\"y\":195},{\"x\":160,\"y\":196},{\"x\":154,\"y\":194},{\"x\":147,\"y\":196},{\"x\":147,\"y\":200},{\"x\":153,\"y\":201},{\"x\":159,\"y\":202},{\"x\":165,\"y\":201},{\"x\":171,\"y\":201},{\"x\":171,\"y\":200},{\"x\":165,\"y\":200},{\"x\":160,\"y\":200},{\"x\":153,\"y\":198},{\"x\":147,\"y\":198}],\"left_eye\":[{\"x\":129,\"y\":140},{\"x\":133,\"y\":143},{\"x\":138,\"y\":145},{\"x\":143,\"y\":144},{\"x\":148,\"y\":143},{\"x\":144,\"y\":139},{\"x\":139,\"y\":137},{\"x\":133,\"y\":138}],\"right_eyebrow\":[{\"x\":209,\"y\":131},{\"x\":201,\"y\":130},{\"x\":193,\"y\":130},{\"x\":184,\"y\":130},{\"x\":176,\"y\":130},{\"x\":184,\"y\":124},{\"x\":194,\"y\":123},{\"x\":203,\"y\":124}],\"left_eyebrow\":[{\"x\":118,\"y\":127},{\"x\":126,\"y\":125},{\"x\":134,\"y\":125},{\"x\":142,\"y\":126},{\"x\":150,\"y\":127},{\"x\":142,\"y\":121},{\"x\":133,\"y\":119},{\"x\":124,\"y\":120}]},\"gender\":94,\"beauty\":69,\"roll\":-3,\"yaw\":2,\"x\":101,\"width\":124.0,\"face_id\":\"2052550148693736053\",\"y\":104,\"pitch\":7,\"age\":35,\"height\":124.0}],\"image_height\":240,\"session_id\":\"\",\"image_width\":320,\"errorcode\":0,\"errormsg\":\"OK\"}");
        JSONArray jsonArray = JSON.parseArray(jsonObject.get("face").toString());
        JSONObject jsonObject2 = (JSONObject)jsonArray.get(0);
        System.out.println("-----------------");
        System.out.println(jsonObject2.get("glass"));
        System.out.println("-----------------");
    }

    @Test
    public void testJSON() {
        String hello = "hello";
        String json =  "{\"error\":\""+hello+"\"}";
        System.out.println(JSON.parseObject(json));
    }

}
