package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailIgnoreCase(@NonNull String email);
    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);
    @Query("""
            SELECT CASE WHEN (COUNT(*) > 0) THEN TRUE ELSE FALSE END
            FROM Member m
            WHERE m.firstName = :firstName AND
                  m.lastName = :lastName AND
                  m.id != :id
           """
    )
    boolean otherAccountHasName(String firstName, String lastName, Long id);

    boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}