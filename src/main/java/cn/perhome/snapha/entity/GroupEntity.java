package cn.perhome.snapha.entity;

import cn.perhome.snapha.enums.GroupCategoryEnum;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Table("group")
public class GroupEntity implements Serializable {

  @Id
  private Long              gid;
  private String            name;
  private Integer           deleted;
  private Integer           weight;
  private String            gsn;
  private Long              parentGid;
  private GroupCategoryEnum category;

}
