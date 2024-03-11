package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}