package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.mapper.GoodsCheckinMapper;
import cn.perhome.snapha.model.GoodsCheckin;
import cn.perhome.snapha.service.GoodsCheckinService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GoodsCheckinServiceImpl extends ServiceImpl<GoodsCheckinMapper, GoodsCheckin>
        implements GoodsCheckinService {

}

