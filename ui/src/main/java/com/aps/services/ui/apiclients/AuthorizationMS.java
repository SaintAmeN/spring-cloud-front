package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IUserMS;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-ms")
public interface AuthorizationMS extends IUserMS {

}
