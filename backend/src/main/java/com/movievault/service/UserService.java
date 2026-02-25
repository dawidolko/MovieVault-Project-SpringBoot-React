package com.movievault.service;

import com.movievault.dto.RegisterRequestDTO;
import com.movievault.dto.UserDTO;
import com.movievault.model.Role;
import com.movievault.model.RoleName;
import com.movievault.model.User;
import com.movievault.repository.ReviewRepository;
import com.movievault.repository.RoleRepository;
import com.movievault.repository.UserRepository;
import com.movievault.security.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       ReviewRepository reviewRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.reviewRepository = reviewRepository;
        this.passwordHasher = passwordHasher;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail().toLowerCase());
        user.setPassword(passwordHasher.hashPassword(dto.getPassword()));
        user.setRole(userRole);

        return userRepository.save(user);
    }

    public User updateProfile(Long id, String firstName, String lastName, String bio) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);
        if (bio != null) user.setBio(bio);
        return userRepository.save(user);
    }

    public UserDTO toDTO(User user) {
        long reviewCount = reviewRepository.countByUserId(user.getId());
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName().name(),
                user.getAvatarUrl(),
                user.getBio(),
                user.getCreatedAt(),
                reviewCount
        );
    }
}
