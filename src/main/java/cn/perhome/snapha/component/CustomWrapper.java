package cn.perhome.snapha.component;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;


public class CustomWrapper extends MapWrapper {
    public CustomWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if(useCamelCaseMapping){
            String underName= CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
            if(!underName.equals(name)){
                // 如果将变量转完之后不相等，即传入的数据为驼峰命名的 userName 转完后为user_name
                // 直接返回name
                return name;
            }
            // 否则就转为驼峰命名后再返回
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,name);
        }
        return name;
    }
}
