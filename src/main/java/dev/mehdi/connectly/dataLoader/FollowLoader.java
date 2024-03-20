package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(4)
public class FollowLoader implements CommandLineRunner {
    private final MemberService memberService;

    @Override
    public void run(String... args) {
        memberService.getMembers().forEach(member -> {
            if (member.getId() == 1) return;
            memberService.follow(1L, member.getId());
        });
    }
}
