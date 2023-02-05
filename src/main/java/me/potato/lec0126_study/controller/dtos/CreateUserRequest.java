package me.potato.lec0126_study.controller.dtos;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;
    private String name;
    private String password;
    private String passwordConfirm;


    public boolean isPasswordMatched() {
        return password != null && password.equals(passwordConfirm);
    }
}
