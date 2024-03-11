package cn.perhome.snapha.service.impl;

import cn.perhome.snapha.entity.ProductEntity;
import cn.perhome.snapha.mapper.ProductMapper;
import cn.perhome.snapha.service.ProductService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity>
        implements ProductService {
}
