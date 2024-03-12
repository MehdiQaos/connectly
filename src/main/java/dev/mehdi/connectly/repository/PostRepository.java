package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMember(Member member);
}