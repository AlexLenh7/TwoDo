package com.TwoDo.TwoDo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TwoDo.TwoDo.entity.Banner;
import com.TwoDo.TwoDo.entity.Tracking;
import com.TwoDo.TwoDo.entity.User;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,UUID> {
    List<Tracking> findByUser(User user);
    boolean existsByUserAndBanner(User user, Banner banner);
}
