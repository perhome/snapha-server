package cn.perhome.snapha.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class MapUtils {
    public static Object convertToObj(Class type, Map map) {
        try {
            BeanInfo  beanInfo  = Introspector.getBeanInfo(type);
            Object    obj       = type.getDeclaredConstructor().newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    descriptor.getWriteMethod().invoke(obj, value);
                }
            }
            return obj;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
