package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailIgnoreCase(@NonNull String email);
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);
}