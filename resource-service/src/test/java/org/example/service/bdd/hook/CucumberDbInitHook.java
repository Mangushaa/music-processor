package org.example.service.bdd.hook;

import io.cucumber.java.Before;
import org.example.service.bdd.context.ContainerContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CucumberDbInitHook {

    @Before
    public static void dbSetup() {
        PostgreSQLContainer<?> postgres = ContainerContext.POSTGRES_CONTAINER;
        try (Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("db-init-scripts/init.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
