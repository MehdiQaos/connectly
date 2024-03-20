package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.member.EditProfileDto;
import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.exception.InvalidRequestException;
import dev.mehdi.connectly.exception.ResourceExistException;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.model.enums.MemberRole;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.RoleService;
import dev.mehdi.connectly.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final MemberMapper memberMapper;

    @Override
    public void load(List<MemberRequestDto> dtos) {
        dtos.stream().filter(m -> !alreadyExist(m))
                .forEach(member -> {
                    String hashedPassword = passwordEncoder.encode(member.getPassword());
                    member.setPassword(hashedPassword);
                    save(member);
                });
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findAllById(List<Long> ids) {
        return memberRepository.findAllById(ids);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Member> findByFirstNameAndLastName(String firstName, String lastName) {
        return memberRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    private boolean alreadyExist(MemberRequestDto dto) {
        return findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent() ||
                this.findByEmail(dto.getEmail()).isPresent();
    }

    @Override
    public Member save(MemberRequestDto memberRequestDto) {
        Member member = memberMapper.requestToMember(memberRequestDto);
        if (alreadyExist(memberRequestDto)) {
            throw new ResourceExistException("Member already exist");
        }
        Role userRole = roleService.findByName(MemberRole.USER).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );
        member.setRole(userRole);
        member.setEnabled(true);
        return memberRepository.save(member);
    }

    @Override
    public void follow(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw InvalidRequestException.of("follow", "You can't follow yourself");
        }
        Follow follow = findFollowMembers(followerId, followedId);
        follow.follower.follow(follow.following);
        memberRepository.save(follow.follower);
    }

    @Override
    public void unfollow(Long followerId, Long followedId) {
        Follow follow = findFollowMembers(followerId, followedId);
        follow.follower.unfollow(follow.following);
        memberRepository.save(follow.follower);
    }

    @Override
    public void setInfos(Member member, String bio, String location, String profession) {
        member.setBio(bio);
        member.setLocation(location);
        member.setProfession(profession);
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> getByEmail(String email) {
        return memberRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Boolean isFollowing(Long member1Id, Long member2Id) {
        Member member1 = findById(member1Id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        Member member2 = findById(member2Id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        return member1.getFollowings().contains(member2);
    }

    @Override
    public Member update(Long id, EditProfileDto dto) {
        Member member = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        if (memberRepository.otherAccountHasName(dto.getFirstName(), dto.getLastName(), id)) {
            throw new ResourceExistException("Member already exist");
        }
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setBirthDate(dto.getBirthDate());
        member.setBio(dto.getBio());
        member.setLocation(dto.getLocation());
        member.setProfession(dto.getProfession());
        memberRepository.save(member);
        return member;
    }

    @Override
    public Member updatePassword(String oldPassword, String newPassword, Long id) {
        Member member = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw InvalidRequestException.of("password", "Old password is incorrect");
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        return member;
    }

    @Override
    public Member updateEmail(Long id, String email) {
        Member member = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        if (memberRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResourceExistException("Email already exist");
        }
        member.setEmail(email);
        memberRepository.save(member);
        return member;
    }

//    @Override
//    public String storeProfilePicture(Long id, MultipartFile file) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        String url = fileStorageService.store(file);
//        member.setProfilePictureLocation(url);
//        memberRepository.save(member);
//
//        return url;
//    }
//
//    @Override
//    public String storeCoverPicture(Long id, MultipartFile file) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        String url = fileStorageService.store(file);
//        member.setCoverPictureLocation(url);
//        memberRepository.save(member);
//
//        return url;
//    }

    private Follow findFollowMembers(Long followerId, Long followedId) {
        Member follower = findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException("follower not found")
        );
        Member following = findById(followedId).orElseThrow(
                () -> new ResourceNotFoundException("following not found")
        );
        return new Follow(follower, following);
    }

    @Override
    public List<Member> getFollowers(Long id) {
        Member member = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return member.getFollowers().stream().toList();
    }

    @Override
    public List<Member> getFollowing(Long id) {
        Member member = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return member.getFollowings().stream().toList();
    }

    private record Follow(Member follower, Member following) {
    }
}
