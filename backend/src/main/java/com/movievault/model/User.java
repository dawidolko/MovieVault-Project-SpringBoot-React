package com.movievault.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private String avatarFilename;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Transient
    public String getAvatarUrl() {
        if (avatarFilename != null && !avatarFilename.isEmpty()) {
            return "/avatars/" + avatarFilename;
        }
        return null;
    }
}
