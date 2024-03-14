package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.entity.CfrProductEntity;
import cn.perhome.snapha.mapper.CfrProductMapper;
import cn.perhome.snapha.service.CfrProductService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CfrProductServiceImpl extends ServiceImpl<CfrProductMapper, CfrProductEntity>
        implements CfrProductService {
}

