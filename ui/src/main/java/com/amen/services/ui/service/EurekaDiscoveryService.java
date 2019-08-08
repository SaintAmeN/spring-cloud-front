package com.amen.services.ui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

/**
 * @author amen
 * @project ms-ui
 * @created 15.07.19
 */
@Service
@RequiredArgsConstructor
public class EurekaDiscoveryService {
    private final DiscoveryClient discoveryClient;

    public boolean checkAvailabilityOfUserMS() {
        return discoveryClient.getInstances("user-ms").size() > 0;
    }
}
