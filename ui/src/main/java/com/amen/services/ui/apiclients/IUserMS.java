package com.amen.services.ui.apiclients;

import com.amen.services.ui.model.dto.userservice.requests.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserMS {
    @PostMapping(value = "${security.jwt.user.urilogin}", consumes = "application/json")
    ResponseEntity login(@RequestBody AuthenticationRequestDto authenticationRequestDto);
}
