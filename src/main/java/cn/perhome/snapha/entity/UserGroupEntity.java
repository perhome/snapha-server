package cn.perhome.snapha.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table("user_group")
public class UserGroupEntity {
    @Id(keyType = KeyType.Auto)
    private Long userId;
    @Id(keyType = KeyType.Auto)
    private Long groupId;
    private Long weight;
    private String groupCategory;
    private Integer deleted;
}
