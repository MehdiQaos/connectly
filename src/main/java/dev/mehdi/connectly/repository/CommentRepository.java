package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}