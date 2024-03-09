package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.DepartmentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Department extends DepartmentEntity {
    private List<Department> children;
}

