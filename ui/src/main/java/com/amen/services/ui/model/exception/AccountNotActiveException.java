package com.amen.services.ui.model.exception;

import org.springframework.security.authentication.AccountStatusException;

public class AccountNotActiveException extends AccountStatusException {
    public AccountNotActiveException(String msg) {
        super(msg);
    }
}
