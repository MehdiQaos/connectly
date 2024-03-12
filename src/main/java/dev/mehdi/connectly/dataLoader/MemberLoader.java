package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
@Order(2)
public class MemberLoader implements CommandLineRunner {
    private final MemberService memberService;

    private final List<MemberRequestDto> memberRequestDtos = List.of(
            new MemberRequestDto("El Mehdi", "Qaos", "m@q.com", "123456", LocalDate.of(1990, 1, 24), true, 1L),
            new MemberRequestDto("Mohammed", "Qaos", "q@m.com", "123456", LocalDate.of(1990, 1, 24),  true, 1L)
    );

    @Override
    public void run(String... args) throws Exception {
        memberService.load(memberRequestDtos);
    }
}
