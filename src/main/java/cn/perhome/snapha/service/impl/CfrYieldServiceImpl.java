package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.entity.CfrYieldEntity;
import cn.perhome.snapha.mapper.CfrYieldMapper;
import cn.perhome.snapha.service.CfrYieldService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CfrYieldServiceImpl extends ServiceImpl<CfrYieldMapper, CfrYieldEntity>
        implements CfrYieldService {
}

