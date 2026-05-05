package com.migtation.server.configs.seccurity.web;

import com.migtation.server.entities.UserEntity;
import com.migtation.server.entities.UserRole;
import com.migtation.server.repositories.UserRepositories;
import com.migtation.server.repositories.UserRoleRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailsCustom implements UserDetailsService {
    private final UserRepositories userRepositories;
    private final UserRoleRepositories userRoleRepositories;

    @Autowired
    public UserDetailsCustom(UserRepositories userRepositories, UserRoleRepositories userRoleRepositories) {
        this.userRepositories = userRepositories;
        this.userRoleRepositories = userRoleRepositories;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepositories.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Set<UserRole> roles = userRoleRepositories.findByUser(user);

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(roles))
                .build();
    }

    private Collection<GrantedAuthority> mapToGrantedAuthorities(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()))
                .collect(Collectors
                        .toList());
    }
}
