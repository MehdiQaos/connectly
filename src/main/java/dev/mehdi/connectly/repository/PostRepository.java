package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMemberOrderByCreatedAtDesc(Member member);
    List<Post> findAllByContentContaining(String query);
    List<Post> findByMemberInOrderByCreatedAtDesc(List<Member> members);
    List<Post> findByMemberNotInOrderByCreatedAtDesc(List<Member> members);
}