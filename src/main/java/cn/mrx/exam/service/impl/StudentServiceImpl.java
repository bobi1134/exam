package cn.mrx.exam.service.impl;

import cn.mrx.exam.pojo.Student;
import cn.mrx.exam.mapper.StudentMapper;
import cn.mrx.exam.service.IStudentService;
import cn.mrx.exam.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author Mr.X
 * @since 2017-03-30
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student> implements IStudentService {
	
}
