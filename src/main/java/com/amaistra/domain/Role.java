package com.amaistra.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{

    ADMIN;
    
    @Override
    public String getAuthority() {
        return name();
    }
}
