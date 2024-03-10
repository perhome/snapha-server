package cn.perhome.snapha.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read", "查看"),
    ADMIN_UPDATE("admin:update", "修改"),
    ADMIN_CREATE("admin:create", "新增"),
    ADMIN_DELETE("admin:delete", "删除"),
    MANAGER_READ("management:read", "查看"),
    MANAGER_UPDATE("management:update", "修改"),
    MANAGER_CREATE("management:create", "新增"),
    MANAGER_DELETE("management:delete", "删除"),
    WORKER_READ("worker:read", "查看"),
    WORKER_UPDATE("worker:update", "修改"),
    WORKER_CREATE("worker:create", "新增"),
    WORKER_DELETE("worker:delete", "删除");

    private final String permission;
    private final String name;
}
