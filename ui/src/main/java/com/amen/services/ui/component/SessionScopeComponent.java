package com.amen.services.ui.component;

import com.amen.services.ui.service.EurekaDiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author amen
 * @project ms-ui
 * @created 15.07.19
 */
@Component
@Scope(value = "session")
@RequiredArgsConstructor
public class SessionScopeComponent {
    private final EurekaDiscoveryService eurekaDiscoveryService;

    public boolean getUserMSAvailability() {
        return eurekaDiscoveryService.checkAvailabilityOfUserMS();
    }

}
