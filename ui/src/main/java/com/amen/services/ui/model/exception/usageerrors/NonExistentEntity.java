package com.amen.services.ui.model.exception.usageerrors;

public class NonExistentEntity extends UsageException {
    private static final String MESSAGE = "This dtoName does not exist.";

    public NonExistentEntity() {
        super(MESSAGE);
    }

    public NonExistentEntity(String referTo) {
        super(MESSAGE);
        this.referTo = referTo;
    }
}
