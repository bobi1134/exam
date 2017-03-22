package cn.mrx.exam.service.impl;

import cn.mrx.exam.pojo.User;
import cn.mrx.exam.mapper.UserMapper;
import cn.mrx.exam.service.IUserService;
import cn.mrx.exam.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-22
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	
}
