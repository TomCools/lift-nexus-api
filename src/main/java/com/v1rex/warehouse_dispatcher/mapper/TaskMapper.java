package com.v1rex.warehouse_dispatcher.mapper;

import com.v1rex.warehouse_dispatcher.domain.Task;
import com.v1rex.warehouse_dispatcher.dto.TaskRequest;
import com.v1rex.warehouse_dispatcher.dto.TaskResponse;
import com.v1rex.warehouse_dispatcher.enums.EquipmentType;
import com.v1rex.warehouse_dispatcher.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // Automatically injects the LocationMapper
public class TaskMapper {

    private final LocationMapper locationMapper;

    public Task toEntity(TaskRequest request){
        if (request == null) return null;
        return Task.builder()
                .weight(request.weight())
                .status(request.status() != null ?
                        request.status() :
                        TaskStatus.OPEN)
                .requiredEquipment(request.requiredEquipment() != null ?
                        request.requiredEquipment() :
                        EquipmentType.STANDARD)
                .build();

    }

    public TaskResponse toResponse(Task entity) {
        if (entity == null) return null;
        return new TaskResponse(
                entity.getId(),
                locationMapper.toResponse(entity.getPickLocation()) ,
                locationMapper.toResponse(entity.getDeliveryLocation()),
                entity.getWeight(),
                entity.getRequiredEquipment(),
                entity.getStatus(),
                entity.getForklift() != null ? entity.getForklift().getId() : null
        );
    }

}