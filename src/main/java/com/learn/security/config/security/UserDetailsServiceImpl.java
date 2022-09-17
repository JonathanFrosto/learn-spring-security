package com.learn.security.config.security;

import com.learn.security.entity.Client;
import com.learn.security.entity.Role;
import com.learn.security.repository.ClientRepository;
import com.learn.security.repository.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository, RoleRepository roleRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository
                .findClientByEmail(username)
                .map(this::entityToUser)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    private User entityToUser(Client client) {
        List<GrantedAuthority> authorities = client.getRoles()
                .stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .map(authority -> (GrantedAuthority) authority::getName)
                .collect(Collectors.toList());

        return new User(client.getEmail(), client.getPassword(), authorities);
    }
}
