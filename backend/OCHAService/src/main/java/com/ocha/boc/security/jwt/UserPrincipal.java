package com.ocha.boc.security.jwt;

import com.ocha.boc.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class UserPrincipal implements UserDetails {


    private String id;
    private String username;
    private String phone;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id, String username, String phone, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.authorities = authorities;
    }

    public UserPrincipal(String id, String phone, String username) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.authorities = authorities;
    }

    public static UserPrincipal createCMS(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getPhone(),
                authorities
        );
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getPhone(),
                authorities

        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
