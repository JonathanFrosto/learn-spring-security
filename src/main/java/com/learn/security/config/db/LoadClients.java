package com.learn.security.config.db;

import com.learn.security.entity.Client;
import com.learn.security.entity.ClientRole;
import com.learn.security.enums.RoleType;
import com.learn.security.repository.ClientRepository;
import com.learn.security.repository.ClientRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class LoadClients implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ClientRoleRepository clientRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        ClientRole admin = clientRoleRepository.save(new ClientRole(RoleType.ROLE_ADMIN));
        ClientRole user = clientRoleRepository.save(new ClientRole(RoleType.ROLE_USER));

        Client client1 = Client.builder()
                .email("jonataanthony@gmail.com")
                .password(passwordEncoder.encode("!Jonathan1"))
                .roles(Arrays.asList(admin))
                .build();

        Client client2 =Client.builder()
                .email("vanessa@gmail.com")
                .password(passwordEncoder.encode("97016003vk"))
                .roles(Arrays.asList(user))
                .build();

        clientRepository.saveAll(Arrays.asList(client1, client2));
    }
}
