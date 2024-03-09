package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.JobEntity;
import cn.perhome.snapha.mapper.JobMapper;
import cn.perhome.snapha.service.JobService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, JobEntity>
        implements JobService {
}
