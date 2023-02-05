package me.potato.lec0126_study.controller;

import me.potato.lec0126_study.controller.dtos.CreateUserRequest;
import me.potato.lec0126_study.controller.dtos.ErrorResponse;
import me.potato.lec0126_study.controller.dtos.UpdateRequest;
import me.potato.lec0126_study.controller.dtos.UserResponse;
import me.potato.lec0126_study.services.exceptions.CommonException;
import me.potato.lec0126_study.stores.entities.User;

public class EntityDtoUtil {

    //User로 받은 데이터를 UserResponse로 바꿔줌
    public static UserResponse toUserDto(User user) {
        var userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    //CreateUserRequest로 받은 데이터를 User로 바꿔줌
    public static User toEntity(CreateUserRequest createUserRequest) {
        var user = new User();
        user.setName(createUserRequest.getName());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        return user;
    }

    //UpdateUserRequest로 받은 데이터를 User로 바꿔줌
    public static User toEntity(UpdateRequest updateUserRequest) {
        var user = new User();
        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        return user;
    }

    public static ErrorResponse toErrorDto(CommonException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }
}
