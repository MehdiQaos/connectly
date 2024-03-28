package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.PostMapper;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.repository.PostRepository;
import dev.mehdi.connectly.service.EventService;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PictureService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private PostMapper postMapper;
    @Mock
    private PictureService pictureService;
    @Mock
    private EventService eventService;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private PostServiceImpl postService;

    private Member member;
    private Post post;
    private PostRequestDto postRequestDto;
    private Event event;

    @BeforeEach
    void setUp() {
        member = new Member();
        post = new Post();
        event = new Event();
        post.setMember(member);
        postRequestDto = new PostRequestDto();
        postRequestDto.setMemberId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(postRepository, memberService, postMapper, eventService);
    }

    @Test
    void createPost_WhenMemberExists_PostIsSaved() {
        when(memberService.findById(postRequestDto.getMemberId())).thenReturn(Optional.of(member));
        when(postMapper.toPost(postRequestDto)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.createPost(postRequestDto);

        assertThat(result).isEqualTo(post);
        verify(memberService, times(1)).findById(postRequestDto.getMemberId());
        verify(postMapper, times(1)).toPost(postRequestDto);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void createPost_MemberNotFound_ThrowsException() {
        when(memberService.findById(postRequestDto.getMemberId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createPost(postRequestDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(memberService, times(1)).findById(postRequestDto.getMemberId());
        verify(postMapper, never()).toPost(postRequestDto);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void newPost_WithFile_PostIsCreated() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(pictureService.save(file)).thenReturn(1L);
        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.newPost(1L, "content", file);

        assertThat(result).isEqualTo(post);
        verify(pictureService, times(1)).save(file);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void newPost_WithoutFile_PostIsCreated() {
        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.newPost(1L, "content", null);

        assertThat(result).isEqualTo(post);
        assertThat(result.getImageLocation()).isNull();
        verify(pictureService, never()).save(any(MultipartFile.class));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void newPost_EmptyFile_PostIsCreatedWithoutImage() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post result = postService.newPost(1L, "content", file);

        assertThat(result).isEqualTo(post);
        assertThat(result.getImageLocation()).isNull();
        verify(pictureService, never()).save(any(MultipartFile.class));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void newPost_NonExistingMember_ThrowsException() {
        Long nonExistingMemberId = 999L;
        when(memberService.findById(nonExistingMemberId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.newPost(nonExistingMemberId, "content", null))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(memberService, times(1)).findById(nonExistingMemberId);
        verify(pictureService, never()).save(any(MultipartFile.class));
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void likePost_ValidMemberAndPost_AddsLike() {
        member = mock(Member.class);
        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(eventService.findLikeEvent(any(Member.class), any(Post.class))).thenReturn(Optional.empty());

        postService.likePost(1L, 1L);

        verify(member, times(1)).addLikedPost(any(Post.class));
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void likePost_NonExistingMemberOrPost_ThrowsException() {
        member = mock(Member.class);
        when(memberService.findById(anyLong())).thenReturn(Optional.empty());
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.likePost(999L, 999L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(member, never()).addLikedPost(any(Post.class));
        verify(memberRepository, never()).save(any(Member.class));
        verify(eventService, never()).findLikeEvent(any(Member.class), any(Post.class));
    }

    @Test
    void unlikePost_ValidMemberAndPost_RemovesLike() {
        member = mock(Member.class);
        when(memberService.findById(anyLong())).thenReturn(Optional.of(member));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(eventService.findLikeEvent(any(Member.class), any(Post.class))).thenReturn(Optional.of(event));

        postService.unlikePost(1L, 1L);

        verify(member, times(1)).removeLikedPost(any(Post.class));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void unlikePost_NonExistingMemberOrPost_ThrowsException() {
        member = mock(Member.class);
        when(memberService.findById(anyLong())).thenReturn(Optional.empty());
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.unlikePost(999L, 999L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(member, never()).removeLikedPost(any(Post.class));
        verify(postRepository, never()).save(any(Post.class));
        verify(eventService, never()).findLikeEvent(any(Member.class), any(Post.class));
    }
}