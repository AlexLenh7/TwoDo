package com.TwoDo.TwoDo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TwoDo.TwoDo.dto.BannerRequest;
import com.TwoDo.TwoDo.dto.BannerResponse;
import com.TwoDo.TwoDo.entity.Banner;
import com.TwoDo.TwoDo.entity.Game;
import com.TwoDo.TwoDo.repository.BannerRepository;
import com.TwoDo.TwoDo.repository.GameRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;
    private final GameRepository gameRepository;

    // constructor
    public BannerService(BannerRepository bannerRepository, GameRepository gameRepository) {
        this.bannerRepository = bannerRepository;
        this.gameRepository = gameRepository;
    }

    // Create the banner 
    public BannerResponse createBanner(BannerRequest request) {
        Game game = gameRepository.findById(request.gameId())
            .orElseThrow(() -> new EntityNotFoundException("Game not found: " + request.gameId()));
        
        Banner banner = new Banner(
            request.characterName(),
            request.startDate(),
            request.endDate(),
            game,
            request.bannerType()
        );
        
        return toResponse(bannerRepository.save(banner));
    }

    private BannerResponse toResponse(Banner banner) {
        return new BannerResponse(
            banner.getId(),
            banner.getGame().getId(),
            banner.getGame().getName(),
            banner.getCharacterName(),
            banner.getBannerType(),
            banner.getStartDate(),
            banner.getEndDate()
        );
    }

    // grab the banner character name
    public Optional<BannerResponse> getBannerById(UUID id) {
        return bannerRepository.findById(id).map(this::toResponse);
    }

    // delete a banner
    public void deleteBanner(UUID id) {
        if (!bannerRepository.existsById(id)) {
            throw new EntityNotFoundException("Banner not found with id: " + id);
        }
        bannerRepository.deleteById(id);
    }

    // grab active banners
    public List<BannerResponse> getActiveBanners() {
        return bannerRepository.findByEndDateAfter(LocalDateTime.now())
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    // grab banners by game 
    public List<BannerResponse> getBannersByGame(UUID gameId) {
        Game game = gameRepository.findById(gameId)
            .orElseThrow(() -> new EntityNotFoundException("Game not found: " + gameId));
        
        return bannerRepository.findByGame(game)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public BannerResponse updateBanner(UUID id, BannerRequest request) {
        Banner banner = bannerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Banner not found with id: " + id));
        Game game = gameRepository.findById(request.gameId()).orElseThrow(()->new EntityNotFoundException("Game not found with id: " + id));

        banner.setCharacterName(request.characterName());
        banner.setBannerType(request.bannerType());
        banner.setEndDate(request.endDate());
        banner.setStartDate(request.startDate());
        banner.setGame(game);

        // return repository
        return toResponse(bannerRepository.save(banner));
    }   

    // grab all banners
    public List<BannerResponse> getAllBanners() {
        return bannerRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}
