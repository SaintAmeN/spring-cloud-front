package com.amen.services.ui.apiclients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-ms")
public interface AuthorizationMS extends IUserMS {

}
