package cn.mrx.exam.service.impl;

import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.mapper.PermissionMapper;
import cn.mrx.exam.service.IPermissionService;
import cn.mrx.exam.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-25
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements IPermissionService {
	
}
