package com.amen.services.ui.model.exception;

import com.amen.services.ui.model.exception.usageerrors.UsageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@RestController
public class MessageExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UsageException.class})
    public final ResponseEntity<ErrorDetails> handleUsageException(UsageException ue, WebRequest request) {
        log.error("Usage Exception: "+ue.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ue.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotLoggedInException.class})
    public final ResponseEntity<ErrorDetails> handleUserNotLoggedInException(UserNotLoggedInException ex, WebRequest request) {
        log.error("UserNotLoggedInException: "+ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
