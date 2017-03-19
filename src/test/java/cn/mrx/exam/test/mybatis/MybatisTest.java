package cn.mrx.exam.test.mybatis;

import cn.mrx.exam.pojo.SystemWeb;
import cn.mrx.exam.service.ISystemWebService;
import cn.mrx.exam.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author: Mr.X
 * Date: 2017/3/17
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-service.xml", "classpath:spring/spring-dao.xml"})
public class MybatisTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISystemWebService iSystemWebService;

    @Test
    public void load(){
        EntityWrapper entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("category", 2);
//        System.out.println(iSystemWebService.selectOne(entityWrapper));
        System.out.println("======================>"+iSystemWebService.selectOne(new EntityWrapper<SystemWeb>().eq("category" ,2)));
    }

}
