package cn.perhome.snapha.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

public enum GroupCategoryEnum {
    DEFAULT("DEFAULT", "默认"),
    TEAM("TEAM", "团队")
    ;

    @Getter
    @EnumValue
    private String code;
    @Getter
    private String desc;

    GroupCategoryEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
