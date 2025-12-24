package com.elmergram.models;

import com.elmergram.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String bio;
    private String pfp_url;
    private Integer followers=0;
    private Integer following=0;
    private String password;


    //ROLES

    @ManyToOne(fetch = FetchType.EAGER) // eager because we always need it
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    // POSTS
    @Column(name = "created_at")
    private Instant createdAt= Instant.now();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PostEntity> postEntities;


}

