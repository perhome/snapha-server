package cn.perhome.snapha.flowable;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class CustomUserQueryImpl extends UserQueryImpl {
    @Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<User> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<User> executeQuery() {
        if (getId() != null) {
            List<User> result = new ArrayList<>();
            UserEntity user = findById(getId());
            if (user != null) {
                result.add(user);
            }
            return result;


        } else if (getIdIgnoreCase() != null) {
            List<User> result = new ArrayList<>();
            UserEntity user = findById(getIdIgnoreCase());
            if (user != null) {
                result.add(user);
            }
            return result;

        } else if (getFullNameLike() != null) {
            return executeNameQuery(getFullNameLike());

        } else if (getFullNameLikeIgnoreCase() != null) {
            return executeNameQuery(getFullNameLikeIgnoreCase());

        } else {
            return executeAllUserQuery();
        }
    }

    protected List<User> executeNameQuery(String name) {
        return null;
    }

    protected List<User> executeAllUserQuery() {
        return null;
    }

    protected UserEntity findById(final String userId) {
        return null;
    }
}
