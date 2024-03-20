package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.dto.comment.CommentRequestDto;
import dev.mehdi.connectly.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@RequiredArgsConstructor
@Component
@Order(5)
public class CommentLoader implements CommandLineRunner {
    private final CommentService commentService;

    private final String[] postComments = {
            "Love this!",
            "So true!",
            "Great point!",
            "Absolutely!",
            "Couldn't agree more!",
            "Well said!",
            "This made my day!",
            "Inspiring!",
            "Thanks for sharing!",
            "I needed this today!",
            "This is such an insightful perspective. I really appreciate you sharing this!",
            "I resonate with this on a deep level. It's amazing how a few words can have such a profound impact.",
            "Wow, I'm blown away by the wisdom in this tweet. Definitely going to reflect on this for a while.",
            "Thank you for sharing your thoughts. Your words have a way of uplifting and inspiring!",
            "I couldn't agree more with what you've said here. It's like you've put into words what I've been feeling.",
            "This is exactly what I needed to hear today. Thank you for spreading positivity and encouragement!",
            "I've been struggling with this lately, and your tweet has given me a fresh perspective. Thank you!",
            "Your words resonate with me deeply. It's comforting to know that others share similar thoughts and feelings.",
            "I love the way you articulate your thoughts. It's clear, concise, and filled with wisdom.",
            "I just want to say how much your tweet means to me. It's a reminder of the beauty and complexity of life.",
            "I'm genuinely impressed by the depth of your insight. Your tweet has sparked some meaningful reflection for me.",
            "Your words have struck a chord with me. It's moments like these that remind me of the power of connection.",
            "I feel like you've encapsulated a universal truth in this tweet. It's a beautiful reminder of our shared humanity.",
            "Thank you for sharing your vulnerability and authenticity. It's refreshing to see genuine expression in a sea of noise.",
            "I resonate with your sentiment on a profound level. It's a testament to the universality of human experience.",
            "Your tweet has inspired me to delve deeper into this topic. It's fascinating how a few words can ignite curiosity.",
            "I'm genuinely moved by the sincerity in your words. It's a testament to the power of authentic communication.",
            "Your tweet has sparked a dialogue within me. It's a testament to the transformative power of meaningful discourse.",
            "Your eloquence never fails to impress me. It's clear that your words are crafted with care and intention.",
            "Your tweet has left a lasting impression on me. It's a testament to the enduring impact of thoughtful expression."
    };

    @Override
    public void run(String... args) {
        Random random = new Random();
        Arrays.stream(postComments).forEach(comment -> {
            long memberId = random.nextLong(1, MemberLoader.numberOfMembers + 1);
            long postId = random.nextLong(1, PostLoader.numberOfPosts + 1);
            commentService.createComment(new CommentRequestDto(comment, memberId, postId));
        });
    }
}
