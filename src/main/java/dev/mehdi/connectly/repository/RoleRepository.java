package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.model.enums.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(MemberRole name);

    Optional<Role> findByName(MemberRole memberRole);
}