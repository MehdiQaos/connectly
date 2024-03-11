package dev.mehdi.connectly.model;

import dev.mehdi.connectly.model.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false, unique = true)
    private MemberRole name;
}
