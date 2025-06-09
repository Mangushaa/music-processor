package org.example.service.bdd;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.ResourceClient;
import org.example.intergration.client.dto.ResourceContentResponse;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ResourceSteps extends CucumberSpringConfiguration {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResourceClient resourceClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String savedResourceId;

    private byte[] content;

    @When("^I upload resource with the following content from file (.+)$")
    public void uploadResource(String filePath) throws IOException {
        ResourceUploadResponse resourceUploadResponse = new ResourceUploadResponse("s3Location");
        content = FileUtils.readFileToByteArray(new ClassPathResource(filePath).getFile());
        when(resourceClient.uploadResource(content)).thenReturn(resourceUploadResponse);
        ResponseEntity<UploadResourceResponse> response = restTemplate.postForEntity("/resources", content, UploadResourceResponse.class);
        savedResourceId = response.getBody().getId();
    }

    @Then("^Resource is saved")
    public void resourceIsSaved() {
        ResourceContentResponse resourceContentResponse = new ResourceContentResponse();
        resourceContentResponse.setContent(content);
        when(resourceClient.getResourceContent(any())).thenReturn(resourceContentResponse);
        ResponseEntity<byte[]> result = restTemplate.getForEntity("/resources/{resourceId}", byte[].class, savedResourceId);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
    }

    @And("^Message is send to message broker")
    public void messageIsSentToMessageBroker() {
        ResourceCreatedEvent result = (ResourceCreatedEvent) rabbitTemplate.receiveAndConvert("resource.created.local");
        assertNotNull(result);
        assertEquals(savedResourceId, result.getResourceId().toString());
    }
}
