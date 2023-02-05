package me.potato.lec0126_study.controller.dtos;

import lombok.Data;

@Data
public class CheckPasswordRequest {
    private String email;
    private String password;
}
