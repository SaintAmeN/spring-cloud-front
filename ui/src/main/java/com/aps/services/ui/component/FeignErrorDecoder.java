package com.aps.services.ui.component;

import com.aps.services.model.exception.ErrorDetails;
import com.aps.services.model.exception.usageerrors.UsageException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;
    @Override
    public Exception decode(String s, Response response) {
        ErrorDetails errorDetails = null;
        String message = "Unknown Exception";
        if (response.body() != null) {
            try {
                errorDetails = objectMapper.readValue(response.body().asInputStream(), new TypeReference<ErrorDetails>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (errorDetails != null){
            message = errorDetails.getMessage();
            log.error("Feign Error; Status code: "+response.status()+ "\nMessage: " + errorDetails.toString());
        } else {
            log.error("Feign Error; Status code: "+response.status());
        }

        switch (response.status()) {
            case (400):
                return new UsageException(message);
            //możemy dopisać wyjątki dla innych errorów otrzymywanych z feignClientów
            default:
                return new RuntimeException("staus code: "+response.status());
        }
    }

}
