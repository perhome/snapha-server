package cn.perhome.snapha.mapper;

import cn.perhome.snapha.dto.query.QueryCfrProductDto;
import cn.perhome.snapha.entity.CfrProductEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CfrProductMapper extends BaseMapper<CfrProductEntity>  {

    List<CfrProductEntity> getList(QueryCfrProductDto query);
}

