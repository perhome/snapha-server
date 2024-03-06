package cn.perhome.snapha.model;

import cn.perhome.snapha.entity.UserEntity;
import cn.perhome.snapha.security.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends UserEntity {
  private String departmentName;
}
