package com.example.spotifyindiano.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        String message = ex.getMessage();
        mav.addObject("errorMessage", (message != null && !message.isBlank()) ? message : ex.toString());
        return mav;
    }
}