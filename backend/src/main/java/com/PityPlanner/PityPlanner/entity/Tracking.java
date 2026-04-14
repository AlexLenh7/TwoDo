package com.PityPlanner.PityPlanner.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="Tracking")
public class Tracking {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="banner_id")
    private Banner banner;

    public Tracking() {}

    public Tracking(User user, Banner banner) {
        this.user = user;
        this.banner = banner;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner bannerId) {
        this.banner = bannerId;
    }
}
