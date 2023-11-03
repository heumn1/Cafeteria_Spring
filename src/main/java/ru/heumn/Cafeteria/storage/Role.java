package ru.heumn.Cafeteria.storage;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    COOK_ROLE, ADMIN_ROLE, SELLER_ROLE, MANAGER_ROLE;

    @Override
    public String getAuthority() {
        return name();
    }
}
