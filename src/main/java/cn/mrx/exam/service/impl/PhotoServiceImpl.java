package cn.mrx.exam.service.impl;

import cn.mrx.exam.pojo.Photo;
import cn.mrx.exam.mapper.PhotoMapper;
import cn.mrx.exam.service.IPhotoService;
import cn.mrx.exam.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author Mr.X
 * @since 2017-05-03
 */
@Service
public class PhotoServiceImpl extends BaseServiceImpl<PhotoMapper, Photo> implements IPhotoService {
	
}
