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
import java.util.Optional;

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

    @Override
    public List<Post> getPostsByFollowings(Long memberId) {
        Member member = memberService.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return member.getFollowings().stream()
                .flatMap(following -> following.getPosts().stream())
                .toList();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }
}
