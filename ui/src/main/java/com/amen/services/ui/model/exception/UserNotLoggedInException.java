package com.amen.services.ui.model.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String message) {
        super(message);
    }

    public UserNotLoggedInException() {
    }
}