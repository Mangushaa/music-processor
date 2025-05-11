package org.example.integration.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Retryable(
        include = {FeignException.GatewayTimeout.class},
        backoff = @Backoff(delay = 2000))
@FeignClient(name = "resource-service", url = "${clients.url.service.resource}")
public interface ResourceServiceClient {

    @GetMapping("/resources/{resourceId}")
    byte[] getResource(@PathVariable("resourceId") Integer resourceId);
}
