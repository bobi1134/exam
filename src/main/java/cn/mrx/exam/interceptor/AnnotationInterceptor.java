package cn.mrx.exam.interceptor;

import cn.mrx.exam.pojo.Permission;
import cn.mrx.exam.pojo.Role;
import cn.mrx.exam.pojo.User;
import cn.mrx.exam.service.IPermissionService;
import cn.mrx.exam.service.IRoleService;
import cn.mrx.exam.service.IUserService;
import cn.mrx.exam.utils.WebConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: AnnotationInterceptor
 * @Author: Mr.X
 * @Date: 2017/3/25 20:32
 * @Description:
 * @Version 1.0
 */
public class AnnotationInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IPermissionService iPermissionService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HandlerMethod methodHandler = (HandlerMethod) handler;
        String uri = httpServletRequest.getRequestURI();
        logger.info(this.getClass().getName()+" 权限控制拦截，[url="+uri+",method="+methodHandler.getMethod().getName()+"]\n");
        //判断请求方法上面是否含有Permission注解
        if (methodHandler.getMethodAnnotation(PermissionCheck.class) != null){
            logger.info("[uri="+uri+",method="+methodHandler.getMethod().getName()+"]有Permission注解！\n");
            HttpSession httpSession = httpServletRequest.getSession();
            User user = (User) httpSession.getAttribute(WebConstant.SESSION_USER);
            Role role = iRoleService.selectOne(new EntityWrapper<Role>().eq("id", user.getRoleId()));
            String[] permissionIds = role.getPermissionIds().split(",");
            for (String permissionId : permissionIds){
                Permission permission = iPermissionService.selectById(permissionId);
                if (permission != null){
                    if (uri.equals(permission.getUri())){
                        logger.info("[用户访问uri="+uri+"数据库uri="+permission.getUri()+"]\n");
                        return true;
                    }
                }
            }
        }else{
            return true;
        }
        logger.info("[uri="+uri+",method="+methodHandler.getMethod().getName()+"]无权限访问！\n");
        httpServletResponse.sendRedirect("/admin");
        return false;
    }
}
