package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(PostRequestDto postRequestDto);

    List<Post> getPostsByMemberId(Long memberId);
}
