package com.example.emag.service;

import com.example.emag.model.DTOs.user.*;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.emag.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService extends AbstractService{

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserWithoutPassDTO register(RegisterDTO dto) {
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new BadRequestException("Passwords mismatch!");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
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
        if(!encoder.matches(dto.getPassword(),u.getPassword())){
            throw new BadRequestException("Passwords must match!");
        }
        if(!encoder.matches(dto.getPassword(),u.getPassword())){
            throw new BadRequestException("You have provided invalid password for authentication");
        }
        u.setPassword(encoder.encode(dto.getConfirmNewPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO login(LoginDTO dto) {
        Optional<User> u = userRepository.getByEmail(dto.getEmail());
        if(u.isEmpty()){
            throw new UnauthorizedException("Wrong credentials");
        }
        if(!encoder.matches(dto.getPassword(),u.get().getPassword())){
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u,UserWithoutPassDTO.class);
    }


    public UserWithoutPassDTO viewUserInfo(int userId) {
        User u = getUserById(userId);
        return mapper.map(u,UserWithoutPassDTO.class);
    }

    public void editUserInfo(EditProfileDTO dto, int loggedId) {
        //TODO
        // add validation for all data
        // throw Exceptions if provided is null

        User u = getUserById(loggedId);
        if (dto.getFirstName() != null && !dto.getFirstName().equals(u.getFirstName())) {
            u.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null && !dto.getLastName().equals(u.getLastName())) {
            u.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null && !dto.getEmail().equals(u.getEmail())) {
            u.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().equals(u.getPhoneNumber())) {
            u.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getUserName() != null && !dto.getUserName().equals(u.getUserName())) {
            u.setUserName(dto.getUserName());
        }
        if (dto.isMale() != u.isMale()) {
            u.setMale(dto.isMale());
        }
        if (dto.getBirthdayDate() != null && !dto.getBirthdayDate().equals(u.getBirthdayDate())) {
            u.setBirthdayDate(dto.getBirthdayDate());
        }
        userRepository.save(u);
    }

    public void editSubscription(int loggedId) {
        User u = getUserById(loggedId);
        u.setSubscribed(!u.isSubscribed());
        userRepository.save(u);
    }

}
