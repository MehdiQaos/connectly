package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
@Order(2)
public class MemberLoader implements CommandLineRunner {
    private final MemberService memberService;
    final static int numberOfMembers = 4;

    private final List<MemberRequestDto> memberRequestDtos = List.of(
            new MemberRequestDto("El Mehdi", "Qaos", "m@q.com", "123456", LocalDate.of(1990, 1, 24)),
            new MemberRequestDto("Mohammed", "Qaos", "q@m.com", "123456", LocalDate.of(1980, 12, 1)),
            new MemberRequestDto("Adil", "Ouadoudi", "adil@limwir.com", "123456", LocalDate.of(1988, 12, 20)),
            new MemberRequestDto("Oussama", "Jamradi", "oussama@greenzor.com", "123456", LocalDate.of(1991, 12, 20))
    );

    private final MemberRequestDto admin = new MemberRequestDto("Mo", "Ibrahim", "mo@g.com", "123456", LocalDate.of(2003, 1, 24));

    @Override
    public void run(String... args) throws Exception {
        memberService.load(memberRequestDtos);
        memberService.createAdmin(admin);
    }
}
