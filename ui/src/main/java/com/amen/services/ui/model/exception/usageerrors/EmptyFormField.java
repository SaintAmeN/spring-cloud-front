package com.amen.services.ui.model.exception.usageerrors;

public class EmptyFormField extends UsageException {
    private final static String MESSAGE = "Field '%s' cannot be empty.";

    public EmptyFormField(String fieldName) {
        super(String.format(MESSAGE, fieldName));
    }

    public EmptyFormField(String fieldName, String referTo) {
        super(String.format(MESSAGE, fieldName), referTo);
    }
}
