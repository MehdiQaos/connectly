package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.config.DataProperties;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.LongStream;

@RequiredArgsConstructor
@Component
@Order(6)
public class LikeLoader implements ApplicationRunner {
    private final PostService postService;
    private final DataProperties dataProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!dataProperties.getInit())
            return;
        postService.likePost(1L, 1L);
        Random random = new Random();
        for (int i = 1; i < 40; i++) {
            Long memberId = random.nextLong(MemberLoader.numberOfMembers) + 1;
            Long postId = random.nextLong(PostLoader.numberOfPosts) + 1;
            postService.likePost(memberId, postId);
        }
    }
}
