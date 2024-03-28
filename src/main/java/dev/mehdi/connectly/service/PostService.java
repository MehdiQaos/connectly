package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(PostRequestDto postRequestDto);

    List<Post> getPostsByMemberId(Long memberId);

    List<Post> getPostsByFollowings(Long memberId);

    Optional<Post> findById(Long postId);

    void likePost(Long memberId, Long postId);

    void unlikePost(Long memberId, Long postId);

    Post newPost(Long memberId, String text, MultipartFile file);

    List<Post> search(String query);

    void deleteById(Long postId);

    List<Post> getTimeline(Long memberId);

    List<Post> getSuggestions(Long memberId);
}
