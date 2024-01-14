package com.example.configvaultserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.configvaultserver.dto.response.GetAllUsersDto;
import com.example.configvaultserver.dto.response.GetUserByIdDto;
import com.example.configvaultserver.enums.EntityEnum;
import com.example.configvaultserver.exceptions.CustomException;
import com.example.configvaultserver.exceptions.EntityNotFoundException;
import com.example.configvaultserver.models.UserModel;
import com.example.configvaultserver.repository.UserRepository;

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

    // public void deleteUserById(String id) throws NotFoundException {
    // userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    public void deleteUserById(String id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (!userModel.isPresent()) {
            System.out.println("*************************");
            System.out.println("*************************");
            System.out.println("*************************");
            System.out.println("*************************");
            System.out.println("*************************");
            System.out.println("*************************");
            System.out.println("*************************");

            throw new EntityNotFoundException(EntityEnum.USER);
        }
        userRepository.deleteById(id);
    }

}