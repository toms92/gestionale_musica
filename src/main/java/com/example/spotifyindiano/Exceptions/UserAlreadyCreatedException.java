package com.example.spotifyindiano.Exceptions;

import org.springframework.lang.Contract;

public class UserAlreadyCreatedException extends Exception{
    public UserAlreadyCreatedException(String message) {
        super(message);
    }

    public UserAlreadyCreatedException() {super();}
}
