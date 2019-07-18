package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IOrderingMS;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ajarmolkowicz
 */
@FeignClient("ordering-ms")
public interface OrderingMS extends IOrderingMS {
}
