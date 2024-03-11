package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.entity.ProductCategoryEntity;
import cn.perhome.snapha.mapper.ProductCategoryMapper;
import cn.perhome.snapha.service.ProductCategoryService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategoryEntity>
        implements ProductCategoryService {
}

