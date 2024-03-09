package cn.perhome.snapha.mapper;
import cn.perhome.snapha.entity.DepartmentEntity;
import cn.perhome.snapha.model.GoodsCategory;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.*;
import cn.perhome.snapha.model.Department;
import java.util.List;
import java.util.Map;
    
@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentEntity> {
    List<Department> getTreeList(Long parentDid);
}

