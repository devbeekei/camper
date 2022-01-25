package com.ss.camper.oauth2.dto;

import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {

    private long id;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(UserInfoDTO user) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority("" + UserRole.USER))
        );
    }

    public static UserPrincipal create(User user) {
        return UserPrincipal.create(UserInfoDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build()
        );
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}