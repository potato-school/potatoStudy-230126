package me.potato.lec0126_study.services.exceptions;

public class EmailAlreadyExistException extends CommonException{

    public EmailAlreadyExistException(String code, String massage) {
        super(code, massage);
    }
}
