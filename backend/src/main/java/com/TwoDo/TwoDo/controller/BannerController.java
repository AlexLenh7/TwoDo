package com.TwoDo.TwoDo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TwoDo.TwoDo.dto.BannerRequest;
import com.TwoDo.TwoDo.dto.BannerResponse;
import com.TwoDo.TwoDo.service.BannerService;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin
public class BannerController {
    
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    // Get request returns all banners
    @GetMapping
    public List<BannerResponse>getAllBanners() {
        return bannerService.getAllBanners();
    }

    // GET request returns single banner
    @GetMapping("/{id}")
    public ResponseEntity<BannerResponse> getBannersById(@PathVariable UUID id) {
        return bannerService.getBannerById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // GET request returns banner by active
    @GetMapping("/active")
    public List<BannerResponse> getActiveBanners() {
        return bannerService.getActiveBanners();
    }

    // GET request returns all banners for game
    @GetMapping("/game/{gameId}")
    public List<BannerResponse> getBannersByGame(@PathVariable UUID gameId) {
        return bannerService.getBannersByGame(gameId);
    }

    // POST request creates a banner
    @PostMapping
    public ResponseEntity<BannerResponse> createBanner(@RequestBody BannerRequest banner) {
        return ResponseEntity.status(201).body(bannerService.createBanner(banner));
    }

    // PUT request updates a banner
    @PutMapping("/{id}")
    public ResponseEntity<BannerResponse> updateBanner(@PathVariable UUID id, @RequestBody BannerRequest request) {
        return ResponseEntity.ok(bannerService.updateBanner(id, request));
    }

    // DELETE request deletes a banner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable UUID id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
}
