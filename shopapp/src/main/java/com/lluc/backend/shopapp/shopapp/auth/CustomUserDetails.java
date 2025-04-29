package com.lluc.backend.shopapp.shopapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Long id; // ID del usuario
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountExpired;
    private boolean credentianlsNonExpired;
    private boolean accountNonLocked;
    private boolean hasCompany; 

    // Constructor
    public CustomUserDetails(Long id, String username, String password, 
                                boolean enabled, boolean accountExpired, 
                                boolean credentianlsNonExpired, boolean accountNonLocked,
                                Collection<? extends GrantedAuthority> authorities,
                                boolean hasCompany 
                              ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountExpired = accountExpired;
        this.credentianlsNonExpired = credentianlsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.hasCompany = hasCompany; // Asignar el valor
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentianlsNonExpired;
    }

    public void setCredentianlsNonExpired(boolean credentianlsNonExpired) {
        this.credentianlsNonExpired = credentianlsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isHasCompany() { // Getter para hasCompany
        return hasCompany;
    }

    public void setHasCompany(boolean hasCompany) { // Setter para hasCompany
        this.hasCompany = hasCompany;
    }
}