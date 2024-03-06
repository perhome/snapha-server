package cn.perhome.snapha.config;



import cn.perhome.snapha.component.MyUserInsertListener;
import cn.perhome.snapha.entity.UserEntity;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {

    private final MyUserInsertListener myUserInsertListener;

    public MyBatisFlexConfig(MyUserInsertListener myUserInsertListener) {
        this.myUserInsertListener = myUserInsertListener;
    }

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        globalConfig.registerUpdateListener(myUserInsertListener, UserEntity.class);
        globalConfig.registerInsertListener(myUserInsertListener, UserEntity.class);
    }
}
