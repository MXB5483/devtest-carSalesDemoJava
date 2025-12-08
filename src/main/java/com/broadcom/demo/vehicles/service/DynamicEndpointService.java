package com.broadcom.demo.vehicles.service;

import org.springframework.stereotype.Service;

@Service
public class DynamicEndpointService {

    private final DynamicPropertiesService props;

    public DynamicEndpointService(DynamicPropertiesService props) {
        this.props = props;
    }

    /**
     * Resolves the active URL for a given API group (e.g., "vehicle" or "sales").
     */
    public String resolve(String endpointKey) {
        String activeEnv = props.get(endpointKey + ".ACTIVE");
        if (activeEnv == null || activeEnv.isBlank()) {
            System.err.println("No ACTIVE environment for " + endpointKey + ", defaulting to DEV");
            activeEnv = "DEV";
        }

        String url = props.get(endpointKey + "." + activeEnv);
        if (url == null || url.isBlank()) {
            System.err.println("Missing URL for " + endpointKey + "." + activeEnv + ", defaulting to DEV");
            url = props.get(endpointKey + ".DEV");
        }

        return url;
    }
}
