package cn.perhome.snapha.model;


import cn.perhome.snapha.security.TokenType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_token")
public class UserToken implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Long           tid;
    @Enumerated(EnumType.STRING)
    public  TokenType      tokenType = TokenType.BEARER;
    private String         token;
    private Long           userId;
    private boolean        revoked;
    private boolean        expired;
    @JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date created;
}

