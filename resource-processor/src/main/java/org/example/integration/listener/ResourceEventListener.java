package org.example.integration.listener;

public interface ResourceEventListener {
    void handleResourceCreated(byte[] resourceCreatedEvent);

    void handleResourceDeleted(byte[] resourceDeletedEvent);
}
