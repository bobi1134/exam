package cn.mrx.exam.service.impl;

import cn.mrx.exam.pojo.PhotoConfig;
import cn.mrx.exam.mapper.PhotoConfigMapper;
import cn.mrx.exam.service.IPhotoConfigService;
import cn.mrx.exam.service.support.BaseServiceImpl;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-06
 */
@Service
public class PhotoConfigServiceImpl extends BaseServiceImpl<PhotoConfigMapper, PhotoConfig> implements IPhotoConfigService {

    /**
     * 自定义带条件多表分页查询
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public Page<PhotoConfig> selectPhotoConfigPage(Page<PhotoConfig> page, Wrapper<PhotoConfig> wrapper) {
        if (null != wrapper) {
            wrapper.orderBy(page.getOrderByField(), page.isAsc());
        }
        page.setRecords(baseMapper.selectPhotoConfigPage(page, wrapper));
        return page;
    }
}
