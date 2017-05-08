package cn.mrx.exam.controller;

import cn.mrx.exam.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-17
 */
public class BaseController{

    protected static Logger baseLog = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected ISystemWebService iSystemWebService;

    @Autowired
    protected IUserService iUserService;

    @Autowired
    protected IRoleService iRoleService;

    @Autowired
    protected IPermissionService iPermissionService;

    @Autowired
    protected IPhotoService iPhotoService;

    @Autowired
    protected IPhotoConfigService iPhotoConfigService;
}
