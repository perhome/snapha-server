package cn.perhome.snapha.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.perhome.snapha.security.Permission.*;

@Getter

public enum Role {

    USER(Collections.emptySet(), "普通用户"),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
            , "管理员"
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
            , "主管"
    ),
    WORKER(
            Set.of(
                    WORKER_READ,
                    WORKER_UPDATE,
                    WORKER_DELETE,
                    WORKER_CREATE
            )
            , "一线员工"
    )
    ;


    private final Set<Permission> permissions;
    private final String name;

    Role(Set<Permission> permissions, String name) {
        this.permissions = permissions;
        this.name = name;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
