package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final MemberMapper memberMapper;

    @Override
    public void load(List<MemberRequestDto> memberRequestDtos) {
        memberRequestDtos.stream().filter((m) -> !memberRepository.existsByEmailIgnoreCase(m.getEmail()))
                .forEach(this::loadIfNotExist);
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    private void loadIfNotExist(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmailIgnoreCase(memberRequestDto.getEmail())) {
            return;
        }
        Member newMember = memberMapper.toMember(memberRequestDto);
        Role role = roleService.findById(memberRequestDto.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        );
        newMember.setRole(role);
        memberRepository.save(newMember);
    }
}
