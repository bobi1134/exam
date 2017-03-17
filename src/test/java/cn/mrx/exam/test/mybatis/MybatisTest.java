package cn.mrx.exam.test.mybatis;

import cn.mrx.exam.pojo.User;
import cn.mrx.exam.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private IUserService userService;

    @Test
    public void load(){
        Page<User> page = userService.selectPage(new Page<User>(2, 10), new EntityWrapper<User>());
        List<User> users = page.getRecords();
        for (User user : users){
            System.out.println(user);
        }
    }

}
