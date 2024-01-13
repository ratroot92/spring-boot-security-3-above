package com.example.configvaultserver.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.configvaultserver.controllers.UserController;
import com.example.configvaultserver.dto.response.GetAllUsersDto;
import com.example.configvaultserver.dto.response.GetUserByIdDto;
import com.example.configvaultserver.exceptions.CustomException;
import com.example.configvaultserver.helpers.ApiResponse;
import com.example.configvaultserver.models.UserModel;
import com.example.configvaultserver.repository.UserRepository;
import com.example.configvaultserver.response.ApiErrorResponse;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public GetAllUsersDto get() {
        List<UserModel> users = userRepository.findAll();
        GetAllUsersDto getAllUsersDto = new GetAllUsersDto(users);
        return getAllUsersDto;
    }


    public GetUserByIdDto getById(String id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new CustomException("User not found");
        } else {
            GetUserByIdDto getUserByIdDto = new GetUserByIdDto(user.get());
            return getUserByIdDto;
        }

    }

    public void deleteUserById(String id) throws NotFoundException {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        userRepository.deleteById(id);
    }

}