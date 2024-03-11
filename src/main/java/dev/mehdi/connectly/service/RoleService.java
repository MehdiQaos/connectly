package dev.mehdi.connectly.service;

import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.model.enums.MemberRole;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(MemberRole memberRole);

    void createIfNotExist(MemberRole memberRole);

    void createRoles();

    Optional<Role> findById(Long roleId);

    List<Role> getRoles();
}
