package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeleteResourceResponse {
    private List<Integer> removedIds;
}
