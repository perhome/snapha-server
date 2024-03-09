package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.DepartmentEntity;
import cn.perhome.snapha.mapper.DepartmentMapper;
import cn.perhome.snapha.service.DepartmentService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentEntity>
        implements DepartmentService {
}

