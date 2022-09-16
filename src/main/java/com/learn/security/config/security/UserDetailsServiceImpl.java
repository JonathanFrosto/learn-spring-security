package com.learn.security.config.security;

import com.learn.security.entity.Client;
import com.learn.security.repository.ClientRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository
                .findClientByEmail(username)
                .map(UserDetailsServiceImpl::entityToUser)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    private static User entityToUser(Client client) {
        List<GrantedAuthority> authorities = client.getRoles()
                .stream()
                .map(roleEntity -> (GrantedAuthority) () -> roleEntity.getRole().toString())
                .collect(Collectors.toList());

        return new User(client.getEmail(), client.getPassword(), authorities);
    }
}
