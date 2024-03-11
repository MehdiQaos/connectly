package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}