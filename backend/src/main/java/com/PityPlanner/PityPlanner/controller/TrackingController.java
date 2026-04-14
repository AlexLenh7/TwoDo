package com.PityPlanner.PityPlanner.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PityPlanner.PityPlanner.dto.TrackingRequest;
import com.PityPlanner.PityPlanner.dto.TrackingResponse;
import com.PityPlanner.PityPlanner.service.TrackingService;

@RestController
@RequestMapping("/api/user/{userId}/tracking")
@CrossOrigin
public class TrackingController {
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    // GET request grabs all tracked banners
    @GetMapping
    public List<TrackingResponse> getAllTracking(@PathVariable UUID userId) {
        return trackingService.getAllTracking(userId);
    }

    // POST request creates tracker
    @PostMapping
    public ResponseEntity<TrackingResponse> createTracking(@PathVariable UUID userId, @RequestBody TrackingRequest request) {
        return ResponseEntity.status(201).body(trackingService.createTracking(userId, request));
    }

    // DELETE request deletes tracking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTracking(@PathVariable UUID id) {
        trackingService.deleteTracking(id);
        return ResponseEntity.noContent().build();
    }

}
