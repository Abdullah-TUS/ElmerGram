package com.elmergram.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter @Getter
@Entity
@Table(name = "followers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"follower_id", "following_id"})
        })@NoArgsConstructor
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private UserEntity follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private UserEntity following;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public FollowEntity(UserEntity follower, UserEntity following) {
        this.follower=follower;
        this.following=following;
    }
    public FollowEntity(int followerId, int followingId) {
        this.follower = new UserEntity();
        this.follower.setId(followerId);

        this.following = new UserEntity();
        this.following.setId(followingId);
    }

}
