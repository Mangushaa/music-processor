package org.example.service.bdd.context;

import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerContext {
    private ContainerContext() {

    }

    public static PostgreSQLContainer<?> POSTGRES_CONTAINER;
}
