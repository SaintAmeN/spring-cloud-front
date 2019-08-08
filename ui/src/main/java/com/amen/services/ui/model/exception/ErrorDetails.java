package com.amen.services.ui.model.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetails {
    @JsonFormat(pattern = "dd-MM-yyyy'T'HH-mm")
    private LocalDateTime timestamp;
    private String message;
    private String details;

}