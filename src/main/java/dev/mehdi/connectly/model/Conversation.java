package dev.mehdi.connectly.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToMany(mappedBy = "conversations")
    private Set<Member> members = new LinkedHashSet<>();

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Message> messages = new LinkedHashSet<>();
//  TODO: Add hashcode and equals methods
}
