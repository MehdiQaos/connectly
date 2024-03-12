package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.PostMapper;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.repository.PostRepository;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberService memberService;

    @Override
    public Post createPost(PostRequestDto postRequestDto) {
        Member member = memberService.findById(postRequestDto.getMemberId()).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        Post newPost = postMapper.toPost(postRequestDto);
        newPost.setMember(member);
        return postRepository.save(newPost);
    }

    @Override
    public List<Post> getPostsByMemberId(Long memberId) {
        Member member = memberService.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return postRepository.findAllByMember(member);
    }
}
