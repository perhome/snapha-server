package cn.perhome.snapha.config;


import cn.perhome.snapha.component.MyUserInsertListener;
import cn.perhome.snapha.entity.UserEntity;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {

    private final        MyUserInsertListener myUserInsertListener;
    private static final Logger               logger
            = LoggerFactory.getLogger("mybatis-flex-sql");

    public MyBatisFlexConfig(MyUserInsertListener myUserInsertListener) {

        this.myUserInsertListener = myUserInsertListener;
        AuditManager.setAuditEnable(true);
        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.info("{}, {}, {}, {}ms", auditMessage.getHostIp()
                        , auditMessage.getModule()
                        , auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );
    }

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        globalConfig.registerUpdateListener(myUserInsertListener, UserEntity.class);
        globalConfig.registerInsertListener(myUserInsertListener, UserEntity.class);
    }
}
