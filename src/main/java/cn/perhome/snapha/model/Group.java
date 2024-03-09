package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.GoodsCategoryEntity;
import cn.perhome.snapha.entity.GroupEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Group extends GroupEntity {
    private List<Group> children;
}
