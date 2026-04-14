package com.PityPlanner.PityPlanner.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.PityPlanner.PityPlanner.dto.UserRequest;
import com.PityPlanner.PityPlanner.dto.UserResponse;
import com.PityPlanner.PityPlanner.entity.User;
import com.PityPlanner.PityPlanner.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User toResponse
    private UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getPicture(),
            user.getCreatedAt()
        );
    }

    // get user's profile
    public Optional<UserResponse> getUserProfile(UUID id) {
        return userRepository.findById(id).map(this::toResponse);
    }

    // update user's name
    @Transactional
    public UserResponse updateUserName(UUID id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id: " + id));
        user.setName(request.name());
        return toResponse(userRepository.save(user));
    }

    // create user or find user
    @Transactional
    public User findOrCreateUser(String email, String name, String picture, String authProvider) {
        return userRepository.findByEmail(email)
            .orElseGet(() -> userRepository.save(new User(name, email, picture, authProvider)));
    }

    // delete account
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
