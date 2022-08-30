package com.example.Atiperatask.Exceptions;

public class NotExistingLoginException extends IllegalArgumentException{
    public NotExistingLoginException(String s) {
        super(s);
    }
}
