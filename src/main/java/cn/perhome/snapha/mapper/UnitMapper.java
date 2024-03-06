package cn.perhome.snapha.mapper;
import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.entity.UnitEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UnitMapper extends BaseMapper<UserEntity> {

    
    @Select("SELECT t1.* FROM unit t1 WHERE t1.uid = #{uid}")
    public UnitEntity getById(@Param("uid") int uid);

    @Select("SELECT t1.* FROM unit t1 WHERE t1.name = #{name}")
    public UnitEntity getByName(@Param("name") String name);
    
    public int save(UnitEntity entity);

    @Select("SELECT * FROM unit ORDER BY weight DESC")
    public List<UnitEntity> getList();
    
    @Update("UPDATE unit SET deleted = 1  WHERE uid = #{uid}")
    public int softDelete(@Param("uid") int uid);
    
    @Delete("DELETE FROM unit WHERE uid = #{uid}")
    public int delete(@Param("uid") int uid);

}

