package me.potato.lec0126_study.controller.dtos;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
}
