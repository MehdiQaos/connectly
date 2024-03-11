package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.model.enums.MemberRole;
import dev.mehdi.connectly.repository.RoleRepository;
import dev.mehdi.connectly.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(MemberRole memberRole) {
        return roleRepository.findByName(memberRole);
    }

    @Override
    public void createIfNotExist(MemberRole memberRole) {
        if (roleRepository.existsByName(memberRole)) {
            return;
        }
        Role newRole = Role.builder().name(memberRole).build();
        roleRepository.save(newRole);
    }

    @Override
    public void createRoles() {
        Arrays.stream(MemberRole.values()).forEach(this::createIfNotExist);
    }

    @Override
    public Optional<Role> findById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
