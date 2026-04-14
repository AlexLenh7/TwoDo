package com.PityPlanner.PityPlanner.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.PityPlanner.PityPlanner.dto.TrackingRequest;
import com.PityPlanner.PityPlanner.dto.TrackingResponse;
import com.PityPlanner.PityPlanner.entity.Banner;
import com.PityPlanner.PityPlanner.entity.Tracking;
import com.PityPlanner.PityPlanner.entity.User;
import com.PityPlanner.PityPlanner.repository.BannerRepository;
import com.PityPlanner.PityPlanner.repository.TrackingRepository;
import com.PityPlanner.PityPlanner.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TrackingService {
    private final TrackingRepository trackingRepository;
    private final UserRepository userRepository;
    private final BannerRepository bannerRepository;

    // default constructor
    public TrackingService(TrackingRepository trackingRepository, UserRepository userRepository, BannerRepository bannerRepository) {
        this.trackingRepository = trackingRepository;
        this.userRepository = userRepository;
        this.bannerRepository = bannerRepository;
    }

    // create tracking
    public TrackingResponse createTracking(UUID userId, TrackingRequest request) {
        // grab the user
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        
        // grab game information
        Banner banner = bannerRepository.findById(request.bannerId())
            .orElseThrow(() -> new EntityNotFoundException("Banner not found: " + request.bannerId()));

        if (trackingRepository.existsByUserAndBanner(user, banner)) {
            throw new IllegalStateException("Banner already pinned");
        }

        Tracking tracking = new Tracking(
            user,
            banner
        );

        return toResponse(trackingRepository.save(tracking));
    }

    // trackingRepository toResponse
    private TrackingResponse toResponse(Tracking tracking) {
        return new TrackingResponse(
            tracking.getId(),
            tracking.getUser().getId(),
            tracking.getBanner().getId(),
            tracking.getBanner().getCharacterName(),
            tracking.getBanner().getGame().getName(),
            tracking.getBanner().getBannerType()
        );
    }

    // grab all banners tracked by user
    public List<TrackingResponse> getAllTracking(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        return trackingRepository.findByUser(user)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    // Stop/delete tracking 
    public void deleteTracking(UUID id) {
        if (!trackingRepository.existsById(id)) {
            throw new EntityNotFoundException("Tracker not found with id: " + id);
        }
        trackingRepository.deleteById(id);
    }
}
