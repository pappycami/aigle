package com.ainapapy.aigle.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();
}
