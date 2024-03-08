package cn.perhome.snapha.mapper;

import cn.perhome.snapha.entity.SerialEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SerialMapper {
    public SerialEntity getValue(String name);
}
