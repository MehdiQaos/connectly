package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.config.DataProperties;
import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@RequiredArgsConstructor
@Component
@Order(3)
public class PostLoader implements CommandLineRunner {
    private final Random random = new Random();
    private final PostService postService;
    private final DataProperties dataProperties;
    final static int numberOfPosts = 20;

    private final String[] postsContent = {
            "Feeling great today! 😊 #goodvibes",
            "Just finished a productive coding session. 💻 #coding",
            "Enjoying a cup of coffee while working on my project. ☕ #productivity",
            "Excited to learn something new today! 📚 #learning",
            "Weekend plans: coding and chill. 💻🍷 #weekendvibes",
            "Started a new project and feeling inspired! 💡 #inspiration",
            "Reflecting on my achievements and setting new goals. 🌟 #goalsetting",
            "Taking a break from coding to enjoy the sunshine. 🌞 #relaxation",
            "Code, coffee, repeat. ☕💻 #codinglife",
            "Feeling motivated to tackle challenges head-on! 💪 #motivation",
            "Just finished reading an amazing book! Highly recommend it to everyone. 📖 #bookrecommendation",
            "Working on a new project and feeling excited about the possibilities! 💼 #projectdevelopment",
            "Spent the day exploring new hiking trails and enjoying nature's beauty. 🌲🌿 #hikingadventures",
            "Trying out a new recipe for dinner tonight. Fingers crossed it turns out well! 🍽️ #cookingexperiment",
            "Attended an inspiring workshop today and learned so much. Feeling motivated to apply new ideas! 💡 #workshop",
            "Started a journaling habit and already noticing its positive impact on my mindset. 📝 #journaling",
            "Taking some time off to relax and recharge. Self-care is important! 💆‍♂️ #selfcare",
            "Just signed up for a course to learn a new skill. Excited to expand my knowledge! 🎓 #lifelonglearning",
            "Watching the sunset with a cup of tea in hand. Sometimes, it's the simple things that bring the most joy. 🌅☕ #gratitude",
            "Reflecting on the past year and setting intentions for the year ahead. Here's to growth and new beginnings! 🌱✨ #newyeargoals"
    };

    @Override
    public void run(String... args) {
        if (!dataProperties.getInit())
            return;
        Arrays.stream(postsContent).forEach(content -> {
            long randomIndex = random.nextLong(1, 4);
            PostRequestDto postRequestDto = new PostRequestDto(content, randomIndex);
            postService.createPost(postRequestDto);
        });
    }
}
