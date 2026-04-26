package com.v1rex.warehouse_dispatcher.mapper;

import com.v1rex.warehouse_dispatcher.domain.PickTask;
import com.v1rex.warehouse_dispatcher.dto.PickTaskRequest;
import com.v1rex.warehouse_dispatcher.dto.PickTaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // Automatically injects the LocationMapper
public class PickTaskMapper {

    private final LocationMapper locationMapper;

    public PickTask toEntity(PickTaskRequest request){
        if (request == null) return null;
        return PickTask.builder()
                .weight(request.weight())
                .build();

    }

    public PickTaskResponse toResponse(PickTask entity) {
        if (entity == null) return null;
        return new PickTaskResponse(
                entity.getId(),
                locationMapper.toResponse(entity.getPickLocation()) ,
                locationMapper.toResponse(entity.getDeliveryLocation()),
                entity.getWeight(),
                entity.getForklift() != null ? entity.getForklift().getId() : null
        );
    }

}