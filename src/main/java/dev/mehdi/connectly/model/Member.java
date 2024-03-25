package dev.mehdi.connectly.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "profile_picture_location")
    private String profilePictureLocation;

    @Column(name = "bio", columnDefinition = "LONGTEXT")
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "profession")
    private String profession;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private final Set<Member> followings = new LinkedHashSet<>();

    //    @ManyToMany(mappedBy = "followings")
    @ManyToMany
    @JoinTable(
            name = "follow",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private final Set<Member> followers = new LinkedHashSet<>();

    @OneToMany(
            mappedBy = "affectedMember",
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<Event> newEvents = new LinkedHashSet<>();

    public void addEvent(Event event) {
        newEvents.add(event);
        event.setAffectedMember(this);
    }

    public void removeEvent(Event event) {
        newEvents.remove(event);
        event.setAffectedMember(null);
    }

    public void follow(Member member) {
        followings.add(member);
//        member.followers.add(this);
    }

    public void unfollow(Member member) {
        followings.remove(member);
//        member.followers.remove(this);
    }

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private final Set<Post> posts = new LinkedHashSet<>();

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private Set<Comment> comments = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "like_post",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @Builder.Default
    private Set<Post> likedPosts = new LinkedHashSet<>();

    public void addLikedPost(Post post) {
        likedPosts.add(post);
        post.getLikedMembers().add(this);
    }

    public void removeLikedPost(Post post) {
        likedPosts.remove(post);
        post.getLikedMembers().remove(this);
    }

    @ManyToMany
    @JoinTable(
            name = "Member_conversations",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "conversations_id")
    )
    @Builder.Default
    private Set<Conversation> conversations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private final List<Token> tokens = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Member member = (Member) obj;
        return Objects.equals(id, member.id);
    }
}