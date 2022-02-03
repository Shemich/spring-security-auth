package ru.shemich.auth.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shemich.auth.model.Status;
import ru.shemich.auth.model.UserApp;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUserApp implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUserApp(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static  UserDetails fromUserApp(UserApp userApp){
        return new User(userApp.getLogin(),userApp.getPassword(),
                userApp.getStatus().equals(Status.ACTIVE),
                userApp.getStatus().equals(Status.ACTIVE),
                userApp.getStatus().equals(Status.ACTIVE),
                userApp.getStatus().equals(Status.ACTIVE),
                userApp.getRole().getAuthorities()
        );
    }
}
