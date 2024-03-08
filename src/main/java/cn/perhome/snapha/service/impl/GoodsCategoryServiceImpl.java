package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.GoodsCategoryEntity;
import cn.perhome.snapha.mapper.GoodsCategoryMapper;
import cn.perhome.snapha.service.GoodsCategoryService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategoryEntity>
        implements GoodsCategoryService {
}
