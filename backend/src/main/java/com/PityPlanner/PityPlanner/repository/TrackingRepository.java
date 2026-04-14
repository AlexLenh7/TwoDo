package com.PityPlanner.PityPlanner.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PityPlanner.PityPlanner.entity.Banner;
import com.PityPlanner.PityPlanner.entity.Tracking;
import com.PityPlanner.PityPlanner.entity.User;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,UUID> {
    List<Tracking> findByUser(User user);
    boolean existsByUserAndBanner(User user, Banner banner);
}
