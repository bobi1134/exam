package cn.mrx.exam.mapper;

import cn.mrx.exam.pojo.PhotoConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-06
 */
public interface PhotoConfigMapper extends BaseMapper<PhotoConfig> {

    /**
     * 自定义带条件多表分页查询
     * @param rowBounds
     * @param wrapper
     * @return
     */
    List<PhotoConfig> selectPhotoConfigPage(RowBounds rowBounds, @Param("ew") Wrapper<PhotoConfig> wrapper);
}