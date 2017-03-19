package cn.mrx.exam.service.impl;

import cn.mrx.exam.pojo.User;
import cn.mrx.exam.mapper.UserMapper;
import cn.mrx.exam.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
}
