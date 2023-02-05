package me.potato.lec0126_study.services.exceptions;

public class PasswordValidationException extends CommonException{
    public PasswordValidationException(String code, String massage) {
        super(code, massage);
    }
}
