package cn.perhome.snapha.flowable;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.Group;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class CustomGroupQueryImpl extends GroupQueryImpl {
    @Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<Group> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<Group> executeQuery() {
        log.info("构造用户组查询");
        if (getUserId() != null) {
            return findGroupsByUser(getUserId());
        } else {
            return findAllGroups();
        }
    }


    protected List<Group> findGroupsByUser(String userId) {
        log.info("查询用户组成员 {}", userId);
        List<Group> list = Lists.newArrayList();
        return list;
    }

    protected List<Group> findAllGroups() {
        log.info("列所有的用户组");
        return new ArrayList<>();
    }
}
