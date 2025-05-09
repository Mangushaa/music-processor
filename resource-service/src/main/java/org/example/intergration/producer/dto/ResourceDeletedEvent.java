package org.example.intergration.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResourceDeletedEvent {
    private List<Integer> resourceIds;
}
