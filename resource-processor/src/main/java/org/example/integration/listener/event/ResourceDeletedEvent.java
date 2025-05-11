package org.example.integration.listener.event;

import lombok.Data;

import java.util.List;

@Data
public class ResourceDeletedEvent {
    private List<Integer> resourceIds;
}
