package com.TwoDo.TwoDo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "user")
    private List<UserGames> userGames = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Tracking> tracking = new ArrayList<>();

    // User email from sign in
    @Column(unique=true, nullable=false)
    private String email;
    
    // User name from sign in
    private String name;

    // User picture from sign in
    private String picture;

    // Auth sign in provider (e.g. google)
    @Column(length=50)
    private String authProvider;

    // Time created at automatically created
    @CreatedDate
    private LocalDateTime createdAt;

    // Default constructor managed by JPA
    protected User() {}

    // Constructor to write to database
    public User(String name, String email, String picture, String authProvider) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authProvider = authProvider;
    }

    // Get and Set Methods
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}