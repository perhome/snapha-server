package cn.perhome.snapha.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.api.UserQuery;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.IdmIdentityServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration

public class CustomIdmIdentityServiceImpl extends IdmIdentityServiceImpl {
    public CustomIdmIdentityServiceImpl(IdmEngineConfiguration idmEngineConfiguration) {
        super(idmEngineConfiguration);
    }

    @Override
    public UserQuery createUserQuery() {
        log.info("构造用户查询");
        // 自定义的用户查询器实现
        return new CustomUserQueryImpl();
    }

    @Override
    public GroupQuery createGroupQuery() {
        log.info("构造用户组别查询");
        // 自定义的组别查询器实现
        return new CustomGroupQueryImpl();
    }
}
