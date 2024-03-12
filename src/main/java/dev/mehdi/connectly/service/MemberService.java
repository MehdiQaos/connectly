package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    void load(List<MemberRequestDto> memberRequestDtos);

    List<Member> getMembers();

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);

    Member save(MemberRequestDto memberRequestDto);
}
