package me.potato.lec0126_study.services.exceptions;

public class PasswordNotMatchedException extends CommonException{
    public PasswordNotMatchedException(String code, String massage) {
        super(code, massage);
    }
}
