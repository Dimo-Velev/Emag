package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.DTOs.user.*;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.emag.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserWithoutPassDTO register(RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords mismatch!");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists!");
        }
        User u = mapper.map(dto, User.class);
        u.setPassword(encoder.encode(u.getPassword()));
        u.setCreatedAt(LocalDateTime.now());
        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO changePass(ChangePassDTO dto, int userId) {
        User u = getUserById(userId);
        if (!encoder.matches(dto.getPassword(), u.getPassword())) {
            throw new BadRequestException("You have provided invalid password for authentication");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Passwords must match!");
        }
        u.setPassword(encoder.encode(dto.getConfirmNewPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO login(LoginDTO dto) {
        Optional<User> u = userRepository.getByEmail(dto.getEmail());
        if (u.isEmpty()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (!encoder.matches(dto.getPassword(), u.get().getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO viewUserInfo(int userId) {
        User u = getUserById(userId);
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public void editUserInfo(EditProfileDTO dto, int loggedId) {
        User u = getUserById(loggedId);
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) {
            u.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getUserName() != null) {
            u.setUserName(dto.getUserName());
        }
        if (dto.getIsMale() != u.isMale()) {
            u.setMale(!u.isMale());
        }
        if (dto.getBirthdayDate() != null) {
            u.setBirthdayDate(dto.getBirthdayDate());
        }
        userRepository.save(u);
    }

    public String editSubscription(int loggedId) {
        User u = getUserById(loggedId);
        u.setSubscribed(!u.isSubscribed());
        userRepository.save(u);
        return (u.isSubscribed()) ? "User subscribed" : "User unsubscribed";
    }

    public Page<ProductViewDTO> viewUserHistory(int userId, Pageable pageable) {
        Page<Product> productPage = productRepository.findProductsByUserId(userId, pageable);
        return productPage.map(product -> mapper.map(product, ProductViewDTO.class));
    }

    public boolean isAdmin(int id) {
        User user = getUserById(id);
        return user.isAdmin();
    }

    public void setUserAsAdmin(int id) {
        User u = getUserById(id);
        u.setAdmin(true);
        userRepository.save(u);
    }
}
