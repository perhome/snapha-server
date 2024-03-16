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
    WORKER_DELETE("worker:delete", "删除"),
    FARMLAND_READ("farmland:read", "查看"),
    FARMLAND_UPDATE("farmland:update", "修改"),
    FARMLAND_CREATE("farmland:create", "新增"),
    FARMLAND_DELETE("farmland:delete", "删除"),
    LIVESTOCK_READ("livestock:read", "查看"),
    LIVESTOCK_UPDATE("livestock:update", "修改"),
    LIVESTOCK_CREATE("livestock:create", "新增"),
    LIVESTOCK_DELETE("livestock:delete", "删除");

    private final String permission;
    private final String name;
}
