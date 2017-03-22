package cn.mrx.exam.controller.support;

import cn.mrx.exam.service.ISystemWebService;
import cn.mrx.exam.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-17
 */
public class BaseController {

    @Autowired
    protected ISystemWebService iSystemWebService;

    @Autowired
    protected IUserService iUserService;

}
