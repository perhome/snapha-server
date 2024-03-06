package cn.perhome.snapha.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    WORKER_READ("worker:read"),
    WORKER_UPDATE("worker:update"),
    WORKER_CREATE("worker:create"),
    WORKER_DELETE("worker:delete")
    ;

    private final String permission;
}
