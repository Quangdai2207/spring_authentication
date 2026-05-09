package com.migration.server.configs.seccurity.web;

import com.migration.server.entities.UserEntity;
import com.migration.server.entities.UserRole;
import com.migration.server.repositories.UserRepositories;
import com.migration.server.repositories.UserRoleRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
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

        List<UserRole> roles = userRoleRepositories.findByUser(user);
        roles.forEach(role -> System.out.println(role.getRole().getName()));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(roles))
                .build();
    }

    private Collection<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()))
                .collect(Collectors
                        .toList());
    }
}
