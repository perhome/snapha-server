package cn.perhome.snapha.service.impl;


import cn.perhome.snapha.service.UnitService;
import cn.perhome.snapha.entity.UnitEntity;
import cn.perhome.snapha.mapper.UnitMapper;

import org.springframework.stereotype.Service;
import com.mybatisflex.spring.service.impl.ServiceImpl;

@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, UnitEntity> implements UnitService {

}