package me.potato.lec0126_study.controller.dtos;

import lombok.Data;

@Data
public class UpdateRequest {

    private String password;
    private String name;
    private String email;
}
