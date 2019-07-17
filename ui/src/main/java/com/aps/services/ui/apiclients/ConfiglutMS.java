package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IConfiglutMS;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("configlut-ms")
public interface ConfiglutMS extends IConfiglutMS {
}
