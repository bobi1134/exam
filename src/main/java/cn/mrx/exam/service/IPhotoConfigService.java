package cn.mrx.exam.service;

import cn.mrx.exam.pojo.PhotoConfig;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-06
 */
public interface IPhotoConfigService extends IService<PhotoConfig> {

    /**
     * 自定义带条件多表分页查询
     * @param page
     * @param wrapper
     * @return
     */
    public Page<PhotoConfig> selectPhotoConfigPage(Page<PhotoConfig> page, Wrapper<PhotoConfig> wrapper);
}
