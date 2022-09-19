package com.learn.security.config.db;

import com.learn.security.entity.Authority;
import com.learn.security.entity.Client;
import com.learn.security.entity.Role;
import com.learn.security.enums.RoleType;
import com.learn.security.repository.AuthorityRepository;
import com.learn.security.repository.ClientRepository;
import com.learn.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

import static com.learn.security.constants.Authorities.ADMIN_TURN_ADMIN;
import static com.learn.security.constants.Authorities.USER_LIST;

@Component
@RequiredArgsConstructor
public class LoadClients implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Authority auth1 = Authority.builder().name(USER_LIST).build();
        Authority auth2 = Authority.builder().name(ADMIN_TURN_ADMIN).build();
        authorityRepository.saveAll(Arrays.asList(auth1, auth2));

        Role admin = Role.builder()
                .role(RoleType.ROLE_ADMIN)
                .authorities(Set.of(auth1, auth2))
                .build();

        Role user = Role.builder()
                .role(RoleType.ROLE_USER)
                .authorities(Set.of(auth1))
                .build();

        roleRepository.saveAll(Arrays.asList(admin, user));

        Client client1 = Client.builder()
                .email("jonataanthony@gmail.com")
                .password(passwordEncoder.encode("!Jonathan1"))
                .roles(Set.of(admin))
                .build();

        Client client2 =Client.builder()
                .email("vanessa@gmail.com")
                .password(passwordEncoder.encode("97016003vk"))
                .roles(Set.of(user))
                .build();

        clientRepository.saveAll(Arrays.asList(client1, client2));
    }
}
