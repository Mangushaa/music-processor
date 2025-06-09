package org.example.service.impl;

import org.apache.commons.io.FileUtils;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.ResourceClient;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.service.configuration.ServiceTestConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(ServiceTestConfiguration.class)
class ResourceServiceImplTest {

    @Autowired
    private ResourceServiceImpl resourceService;

    @Autowired
    private ResourceClient resourceClient;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.13-management")
            .withExposedPorts(5672, 15672);

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getMappedPort(5672));
        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }

    @BeforeAll
    public static void dbSetup() {
        try (Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("db-init-scripts/init.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void uploadResource() throws IOException {
        byte[] resourceData = FileUtils.readFileToByteArray(new ClassPathResource("test-mp3/valid-sample-with-required-tags.mp3").getFile());
        ResourceUploadResponse resourceUploadResponse = new ResourceUploadResponse("s3Location");
        when(resourceClient.uploadResource(resourceData)).thenReturn(resourceUploadResponse);
        UploadResourceResponse result = resourceService.uploadResource(resourceData);
        assertNotNull(result);
        assertNotNull(result.getId());
        Optional<Resource> savedResource = resourceRepository.findById(Integer.valueOf(result.getId()));
        assertTrue(savedResource.isPresent());
        assertEquals("s3Location", savedResource.get().getResourceLocation());
        ResourceCreatedEvent resourceCreatedEvent = (ResourceCreatedEvent) rabbitTemplate.receiveAndConvert("resource.created.local");
        assertNotNull(resourceCreatedEvent);
        assertEquals(result.getId(), resourceCreatedEvent.getResourceId().toString());
        amqpAdmin.purgeQueue("resource.created.local", false);
    }

    @Test
    void getResource() throws IOException {
        byte[] resourceData = FileUtils.readFileToByteArray(new ClassPathResource("test-mp3/valid-sample-with-required-tags.mp3").getFile());
        ResourceUploadResponse resourceUploadResponse = new ResourceUploadResponse("s3Location");
        when(resourceClient.uploadResource(resourceData)).thenReturn(resourceUploadResponse);
        UploadResourceResponse result = resourceService.uploadResource(resourceData);
        assertNotNull(result);
        assertNotNull(result.getId());
        Optional<Resource> savedResource = resourceRepository.findById(Integer.valueOf(result.getId()));
        assertTrue(savedResource.isPresent());
        assertEquals("s3Location", savedResource.get().getResourceLocation());
        amqpAdmin.purgeQueue("resource.created.local", false);
    }

    @Test
    void deleteResources() throws IOException {
        byte[] resourceData = FileUtils.readFileToByteArray(new ClassPathResource("test-mp3/valid-sample-with-required-tags.mp3").getFile());
        ResourceUploadResponse resourceUploadResponse = new ResourceUploadResponse("s3Location");
        when(resourceClient.uploadResource(resourceData)).thenReturn(resourceUploadResponse);
        UploadResourceResponse result = resourceService.uploadResource(resourceData);
        assertNotNull(result);
        assertNotNull(result.getId());
        DeleteResourceResponse deleteResourceResponse = resourceService.deleteResources(List.of(Integer.valueOf(result.getId())));
        assertEquals(deleteResourceResponse.getRemovedIds(), List.of(Integer.valueOf(result.getId())));
    }
}