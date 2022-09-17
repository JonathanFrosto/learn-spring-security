package com.learn.security.service.impl;

import com.learn.security.entity.Client;
import com.learn.security.entity.Role;
import com.learn.security.enums.RoleType;
import com.learn.security.repository.ClientRepository;
import com.learn.security.repository.RoleRepository;
import com.learn.security.service.UserService;
import com.learn.security.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<UserDTO> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(UserServiceImpl::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return clientRepository.findById(id)
                .map(UserServiceImpl::entityToDTO)
                .orElseThrow(() -> new IllegalArgumentException("usuario nao existe"));
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirmation())) {
            throw new IllegalArgumentException("senha e confirmacao de senha devem ser iguais");
        }

        if (clientRepository.existsById(userDTO.getId())) {
            throw new IllegalArgumentException("usuario ja existe");
        }

        Client newClient = new Client(userDTO.getEmail(), userDTO.getPassword());
        Client savedClient = clientRepository.save(newClient);

        userDTO.setId(savedClient.getId());
        userDTO.setPassword(null);
        userDTO.setPasswordConfirmation(null);

        return userDTO;
    }

    @Override
    public void turnAdmin(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not foudn"));

        Set<Role> roles = client.getRoles();

        Role role = roleRepository.findByRole(RoleType.ROLE_ADMIN);
        client.getRoles().add(role);

        clientRepository.save(client);
    }

    @Override
    public void changePassword(UserDTO userDTO) {
        throw new UnsupportedOperationException("not implemented");
    }

    private static UserDTO entityToDTO(Client entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setEmail(entity.getEmail());
        return userDTO;
    }
}
