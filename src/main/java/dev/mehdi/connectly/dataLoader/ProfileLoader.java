package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.config.DataProperties;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@RequiredArgsConstructor
@Component
@Order(7)
public class ProfileLoader implements CommandLineRunner {
    private final MemberService memberService;
    private final DataProperties dataProperties;

    private final String[] bios = {
            "I'm a passionate software engineer with over a decade of experience in developing web applications. My journey in coding began when I was just a teenager tinkering with BASIC on my first computer. Since then, I've honed my skills in various programming languages and frameworks, specializing in full-stack development. I thrive in dynamic environments where I can collaborate with talented teams to tackle complex problems and deliver high-quality solutions. Outside of coding, I enjoy hiking, playing guitar, and experimenting with new recipes in the kitchen.",
            "Environmental conservation has been my lifelong commitment. From a young age, I was fascinated by the beauty of nature and driven by a desire to protect it. With a degree in environmental science, I've worked on projects ranging from wildlife conservation to renewable energy initiatives. My goal is to raise awareness about the importance of sustainability and inspire others to take action. When I'm not in the field conducting research, you can find me exploring remote wilderness areas or volunteering at local conservation organizations.",
            "I consider myself an intrepid explorer, always seeking out new adventures and pushing the boundaries of my comfort zone. Whether it's trekking through dense rainforests, summiting towering peaks, or scuba diving in crystal-clear waters, I'm constantly fueled by a sense of wanderlust. Traveling has not only broadened my horizons but also taught me invaluable lessons about resilience, adaptability, and cultural diversity. My bucket list seems to grow longer with each journey, but I wouldn't have it any other way.",
            "Art has been my lifelong passion and greatest form of self-expression. From a young age, I knew that I was destined to create. Whether it's painting vibrant landscapes, sculpting intricate forms, or capturing fleeting moments through photography, art allows me to communicate ideas and emotions in ways that words cannot. My work is heavily influenced by nature, spirituality, and the human experience. I believe that art has the power to inspire, heal, and transform lives, and I'm committed to sharing my gift with the world.",
            "Teaching is not just a profession for me – it's a calling. For over two decades, I've had the privilege of shaping young minds and instilling a love of learning in my students. From kindergarten to college, I've taught a diverse range of subjects, but my passion lies in literature and language arts. I believe that education is the key to unlocking human potential and building a brighter future for generations to come. Outside of the classroom, I enjoy writing poetry, volunteering in my community, and spending time with my family.",
            "I'm a self-professed tech geek with an insatiable curiosity for all things digital. From coding to cybersecurity, artificial intelligence to blockchain, I'm constantly fascinated by the ever-evolving landscape of technology. With a background in computer science, I've had the opportunity to work on cutting-edge projects that push the boundaries of innovation. Whether it's developing a new app, optimizing algorithms, or troubleshooting complex systems, I thrive on the challenge of solving problems and pushing technology forward.",
            "Animals have always held a special place in my heart. From the loyal family dog to the exotic creatures of the rainforest, I've been captivated by the beauty and diversity of the animal kingdom. As a dedicated animal rights activist, I'm committed to advocating for the welfare and protection of all living beings. Whether it's rescuing abandoned pets, volunteering at wildlife sanctuaries, or raising awareness about endangered species, I'm driven by a deep sense of compassion and empathy. In a world where animals are often overlooked and mistreated, I strive to be their voice and champion their cause.",
            "Fitness isn't just a hobby for me – it's a way of life. As a certified personal trainer and nutrition coach, I'm passionate about helping others achieve their health and wellness goals. Whether it's through personalized workout plans, nutritional guidance, or motivational support, I'm dedicated to empowering my clients to live their best lives. My approach to fitness is holistic, focusing on the mind-body connection and promoting balance in all aspects of life. When I'm not in the gym or the kitchen, you can find me hiking in the mountains, practicing yoga on the beach, or competing in obstacle course races.",
            "I've always had an entrepreneurial spirit and a drive to create something from nothing. From launching my first lemonade stand as a kid to founding multiple successful startups as an adult, I've never been afraid to take risks and pursue my passions. My ventures have spanned various industries, including e-commerce, hospitality, and technology. While not every endeavor has been a success, each failure has taught me invaluable lessons and fueled my determination to keep pushing forward. As a serial entrepreneur, I thrive on the excitement of innovation and the challenge of building something that makes a positive impact on the world.",
            "Food is my love language. From exotic street food to gourmet delicacies, I'm always on the hunt for the next culinary adventure. As a passionate foodie and amateur chef, I find joy in experimenting with new flavors, techniques, and cuisines from around the world. Whether it's hosting dinner parties for friends, exploring local farmers' markets, or documenting my culinary creations on social media, food brings people together and nourishes the soul. I believe that good food should be savored, shared, and celebrated, and I'm on a mission to discover the most delicious dishes the world has to offer."
    };
    private final String[] professions = {
            "Solution Manager - Creative Tim Officer",
            "Data Scientist - Innovation Guru",
            "Marketing Director - Branding Expert",
            "Product Manager - Customer Experience Specialist",
            "Financial Advisor - Wealth Management Strategist",
            "Human Resources Manager - Talent Acquisition Specialist",
            "Artificial Intelligence Engineer - Machine Learning Architect",
            "Environmental Scientist - Sustainability Consultant",
            "Fashion Designer - Trendsetter",
            "Healthcare Administrator - Patient Care Coordinator"
    };

    private final String[] locations = {
            "New York City, USA",
            "Tokyo, Japan",
            "London, UK",
            "Sydney, Australia",
            "Paris, France",
            "Mumbai, India",
            "Rio de Janeiro, Brazil",
            "Cape Town, South Africa",
            "Vancouver, Canada",
            "Dubai, UAE"
    };

    @Override
    public void run(String... args) {
        if (!dataProperties.getInit())
            return;
        Random random = new Random();
        memberService.getMembers().forEach(member -> memberService.setInfos(
                member,
                bios[random.nextInt(bios.length)],
                locations[random.nextInt(locations.length)],
                professions[random.nextInt(professions.length)]
        ));
    }
}
