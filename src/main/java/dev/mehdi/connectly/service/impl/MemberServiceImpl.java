package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.exception.ResourceExistException;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.RoleService;
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
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Member> findByFirstNameAndLastName(String firstName, String lastName) {
        return memberRepository.findByFirstNameAndLastName(firstName
                , lastName);
    }

    private boolean alreadyExist(MemberRequestDto dto) {
        return findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent() ||
                this.findByEmail(dto.getEmail()).isPresent();
    }

    @Override
    public Member save(MemberRequestDto memberRequestDto) {
        Member member = memberMapper.toMember(memberRequestDto);
        if (alreadyExist(memberRequestDto)) {
            throw new ResourceExistException("Member already exist");
        }
        Role role = roleService.findById(memberRequestDto.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );
        member.setRole(role);
        return memberRepository.save(member);
    }
}
