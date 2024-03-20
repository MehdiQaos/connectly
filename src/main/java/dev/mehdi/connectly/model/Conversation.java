package dev.mehdi.connectly.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conversation")
public class Conversation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToMany(mappedBy = "conversations")
    @Builder.Default
    private Set<Member> members = new LinkedHashSet<>();

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private Set<Message> messages = new LinkedHashSet<>();

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
        Conversation conversation = (Conversation) obj;
        return Objects.equals(id, conversation.id);
    }
}
