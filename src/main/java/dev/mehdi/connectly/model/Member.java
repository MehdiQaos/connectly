package dev.mehdi.connectly.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Getter @Setter @Builder
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(
            mappedBy = "follower",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<Follow> followings = new LinkedHashSet<>();

    @OneToMany(
            mappedBy = "following",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<Follow> followers = new LinkedHashSet<>();

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
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<Reaction> reactions = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "Member_conversations",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "conversations_id"))
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

//  TODO: Add hashcode and equals methods
}