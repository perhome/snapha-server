package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.entity.GoodsEntity;
import cn.perhome.snapha.mapper.GoodsMapper;
import cn.perhome.snapha.service.GoodsService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity>
        implements GoodsService {

}
