package com.amen.services.ui.model.exception.usageerrors;

public class UnauthorizedException extends UsageException {
    private final static String MESSAGE = "You are unauthorized to perform this operation";

    public UnauthorizedException() {
        super(MESSAGE);
    }

    public UnauthorizedException(String referTo) {
        super(MESSAGE, referTo);
    }
}
