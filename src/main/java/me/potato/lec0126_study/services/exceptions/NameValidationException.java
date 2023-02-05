package me.potato.lec0126_study.services.exceptions;

public class NameValidationException extends CommonException{
    public NameValidationException(String code, String massage) {
        super(code, massage);
    }
}
