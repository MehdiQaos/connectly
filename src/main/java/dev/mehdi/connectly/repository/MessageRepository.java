package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}