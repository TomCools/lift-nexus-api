package com.v1rex.warehouse_dispatcher.mapper;

import com.v1rex.warehouse_dispatcher.domain.Forklift;
import com.v1rex.warehouse_dispatcher.dto.ForkliftRequest;
import com.v1rex.warehouse_dispatcher.dto.ForkliftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ForkliftMapper {

    private final PickTaskMapper taskMapper;

    public ForkliftResponse toResponse(Forklift entity) {
        if (entity == null) return null;
        return new ForkliftResponse(
                entity.getId(),
                entity.getWeightCapacity(),
                entity.getTasks().stream()
                        .map(taskMapper::toResponse)
                        .toList()
        );
    }

    public Forklift toEntity(ForkliftRequest request) {
    if (request == null) return null;
    return Forklift.builder()
            .weightCapacity(request.weightCapacity())
            .build();
    }
}