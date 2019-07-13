package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IAuthorization;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-ms")
public interface AuthorizationMS extends IAuthorization {

}
