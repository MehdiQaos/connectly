package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(PostRequestDto postRequestDto);

    List<Post> getPostsByMemberId(Long memberId);

    List<Post> getPostsByFollowings(Long memberId);

    Optional<Post> findById(Long postId);

    void likePost(Long memberId, Long postId);

    void unlikePost(Long memberId, Long postId);
}
