package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.PostMapper;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.repository.PostRepository;
import dev.mehdi.connectly.service.FileStorageService;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Post createPost(PostRequestDto postRequestDto) {
        Member member = findMemberOrThrow(postRequestDto.getMemberId());
        Post newPost = postMapper.toPost(postRequestDto);
        newPost.setMember(member);
        return postRepository.save(newPost);
    }

    @Override
    public List<Post> getPostsByMemberId(Long memberId) {
        Member member = findMemberOrThrow(memberId);
        return postRepository.findAllByMember(member);
    }

    @Override
    public List<Post> getPostsByFollowings(Long memberId) {
        Member member = findMemberOrThrow(memberId);
        return member.getFollowings().stream()
                .flatMap(following -> following.getPosts().stream())
                .toList();
//        return member.getFollowings().stream()
//                .flatMap(following -> following.getPosts().stream())
//                .map(post -> {
//                    PostResponseDto dto = postMapper.toDto(post);
//                    dto.setLiked(post.getLikedMembers().contains(member));
//                    return dto;
//                })
//                .toList();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public void likePost(Long memberId, Long postId) {
        Member member = findMemberOrThrow(memberId);
        Post post = findPostOrThrow(postId);
        member.addLikedPost(post);
        System.out.println("Liking post " + postId + " by member " + memberId);
        memberRepository.save(member);
//        postRepository.save(post);
    }

    @Override
    public void unlikePost(Long memberId, Long postId) {
        Member member = findMemberOrThrow(memberId);
        Post post = findPostOrThrow(postId);
        member.removeLikedPost(post);
        postRepository.save(post);
    }

    @Override
    public Post newPost(Long memberId, String text, MultipartFile file) {
        Member member = findMemberOrThrow(memberId);
        Post newPost = new Post();
        newPost.setContent(text);
        if (file != null && !file.isEmpty()) {
            Long fileId = fileStorageService.save(file);
            newPost.setImageLocation(String.valueOf(fileId));
        }
        newPost.setMember(member);
        return postRepository.save(newPost);
    }

    @Override
    public Page<Post> searchWithPagination(String query, Pageable pageable) {
        return postRepository.findAllByContentContaining(query, pageable);
    }

    @Override
    public List<Post> search(String query) {
        return postRepository.findAllByContentContaining(query);
    }

    private Member findMemberOrThrow(Long memberId) {
        return memberService.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
    }

    private Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found")
        );
    }
}
