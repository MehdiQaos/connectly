package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.PostMapper;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.model.enums.EventType;
import dev.mehdi.connectly.repository.CommentRepository;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.repository.PostRepository;
import dev.mehdi.connectly.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PictureService pictureService;
    private final EventService eventService;
    private final CommentRepository commentRepository;

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
        return postRepository.findAllByMemberOrderByCreatedAtDesc(member);
    }

    @Override
    public List<Post> getPostsByFollowings(Long memberId) {
        Member member = findMemberOrThrow(memberId);
        return member.getFollowings().stream()
                .flatMap(following -> following.getPosts().stream())
                .toList();
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
        if (
                eventService.findLikeEvent(member, post).isEmpty() &&
                        !member.equals(post.getMember())
        ) {
            Event newEvent = new Event(null, EventType.LIKE, post, null, member, post.getMember());
            post.getMember().addEvent(newEvent);
        }
        memberRepository.save(member);
    }

    @Override
    public void unlikePost(Long memberId, Long postId) {
        Member member = findMemberOrThrow(memberId);
        Post post = findPostOrThrow(postId);
        eventService.findLikeEvent(member, post)
                .ifPresent(event -> post.getMember().removeEvent(event));
        member.removeLikedPost(post);
        postRepository.save(post);
    }

    @Override
    public Post newPost(Long memberId, String text, MultipartFile file) {
        Member member = findMemberOrThrow(memberId);
        Post newPost = new Post();
        newPost.setContent(text);
        if (file != null && !file.isEmpty()) {
            Long fileId = pictureService.save(file);
            newPost.setImageLocation(String.valueOf(fileId));
        }
        newPost.setMember(member);
        return postRepository.save(newPost);
    }

    @Override
    public List<Post> search(String query) {
        return postRepository.findAllByContentContaining(query);
    }

    @Override
    public void deleteById(Long postId) {
        Post post = findPostOrThrow(postId);
        if (post.getImageLocation() != null) {
            pictureService.deletePicture(Long.parseLong(post.getImageLocation()));
        }
        post.getMember().getPosts().remove(post);
        eventService.findByPostId(postId).forEach(eventService::delete);
        List<Member> likedMembers = new ArrayList<>(post.getLikedMembers());
        for (Member member : likedMembers) {
            member.removeLikedPost(post);
        }
        post.getReports().forEach(report -> {
            report.getReportedPost().removeReport(report);
            report.getReportingMember().removeReport(report);
        });
        post.getComments().forEach((comment) -> {
            eventService.findByComment(comment).ifPresent(eventService::delete);
            commentRepository.delete(comment);
        });
        postRepository.delete(post);
    }

    @Override
    public List<Post> getTimeline(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        List<Member> followings = new ArrayList<>(member.getFollowings());
        followings.add(member);
        return postRepository.findByMemberInOrderByCreatedAtDesc(followings);
    }

    @Override
    public List<Post> getSuggestions(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        List<Member> followings = new ArrayList<>(member.getFollowings());
        followings.add(member);
        return postRepository.findByMemberNotInOrderByCreatedAtDesc(followings);
    }

    @Override
    public boolean isOwner(Long memberId, Long postId) {
        Member member = findMemberOrThrow(memberId);
        Post post = findPostOrThrow(postId);
        return member.equals(post.getMember());
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
