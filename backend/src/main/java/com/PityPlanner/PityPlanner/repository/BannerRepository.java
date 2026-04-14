package com.PityPlanner.PityPlanner.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PityPlanner.PityPlanner.entity.Banner;
import com.PityPlanner.PityPlanner.entity.Game;

@Repository
public interface BannerRepository extends JpaRepository<Banner,UUID> {
    List<Banner> findByGame(Game game);
    List<Banner> findByGameAndBannerType(Game game, String bannerType);
    List<Banner> findByEndDateAfter(LocalDateTime date);
}
