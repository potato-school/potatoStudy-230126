package me.potato.lec0126_study.services.exceptions;

public class EmailValidationException extends CommonException{
    public EmailValidationException(String code, String massage) {
        super(code, massage);
    }
}
