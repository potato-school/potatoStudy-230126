package me.potato.lec0126_study.services.exceptions;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{

    private final String code;
    private final String message;

    public CommonException(String code, String massage) {
        super(String.format("%s:%s",code, massage));
        this.code = code;
        this.message = massage;
    }
}
