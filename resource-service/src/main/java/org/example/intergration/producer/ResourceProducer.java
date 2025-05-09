package org.example.intergration.producer;

import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.example.intergration.producer.dto.ResourceDeletedEvent;

public interface ResourceProducer {
    void sendResourceCreated(ResourceCreatedEvent resourceCreatedEvent);

    void sendResourceDeleted(ResourceDeletedEvent resourceDeletedEvent);
}
