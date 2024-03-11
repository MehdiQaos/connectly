package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}