package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}