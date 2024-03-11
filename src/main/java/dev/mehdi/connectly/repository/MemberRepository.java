package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmailIgnoreCase(@NonNull String email);
}