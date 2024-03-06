package cn.perhome.snapha.entity;

import cn.perhome.snapha.security.TokenType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Data
@Table("token")
public class TokenEntity {
    @Id
    public Long id;
    public Long userId;
    public String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public Boolean revoked;
    public Boolean expired;
    @JsonFormat(pattern = "yy-MM-dd HH:mm", timezone="GMT+8")
    private java.util.Date created;
}
