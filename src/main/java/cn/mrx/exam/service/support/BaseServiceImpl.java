package cn.mrx.exam.service.support;

import cn.mrx.exam.mapper.PermissionMapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @ClassName: BaseServiceImpl
 * @Author: Mr.X
 * @Date: 2017/3/22 13:12
 * @Description:
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
