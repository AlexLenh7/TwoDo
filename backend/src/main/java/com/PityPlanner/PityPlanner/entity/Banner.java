package com.PityPlanner.PityPlanner.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name="Banners")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "banner")
    private List<Tracking> tracking = new ArrayList<>();
    
    // Weapon or Character
    @Column(length=50)
    private String bannerType;
    
    // Banner limited or rerun
    Boolean isLimited;
    Boolean isRerun;

    // Name of the character
    private String characterName;

    // Banner start date
    private LocalDateTime startDate;

    // Banner end date
    private LocalDateTime endDate;

    public Banner() {}

    public Banner(String characterName, LocalDateTime startDate, LocalDateTime endDate, Game game, String bannerType) {
        this.characterName = characterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.game = game;
        this.bannerType = bannerType;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public Boolean getIsLimited() {
        return isLimited;
    }

    public void setIsLimited(Boolean isLimited) {
        this.isLimited = isLimited;
    }

    public Boolean getIsRerun() {
        return isRerun;
    }

    public void setIsRerun(Boolean isRerun) {
        this.isRerun = isRerun;
    }
}
