package me.potato.lec0126_study.services.exceptions;

public class UserNotExistException extends CommonException{
    public UserNotExistException(String code, String massage) {
        super(code, massage);
    }
}
