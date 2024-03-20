package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.member.EditProfileDto;
import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.model.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    void load(List<MemberRequestDto> memberRequestDtos);

    List<Member> getMembers();

    Optional<Member> findById(Long id);

    List<Member> findAllById(List<Long> ids);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);

    Member save(MemberRequestDto memberRequestDto);

    void follow(Long followerId, Long followedId);

    Member updateEmail(Long id, String email);

    List<Member> getFollowers(Long id);

    List<Member> getFollowing(Long id);

    void unfollow(Long followerId, Long followedId);

    void setInfos(Member member, String bio, String location, String profession);

    Optional<Member> getByEmail(String email);

    Boolean isFollowing(Long member1Id, Long member2Id);

    Member update(Long id, EditProfileDto dto);

    Member updatePassword(String oldPassword, String newPassword, Long id);

//    String storeProfilePicture(Long id, MultipartFile file);

}
