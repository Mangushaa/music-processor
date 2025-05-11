package org.example.integration.listener;

public interface ResourceQueueListener {
    void handleResourceCreated(byte[] resourceCreatedEvent);

    void handleResourceDeleted(byte[] resourceDeletedEvent);
}
