package com.v1rex.warehouse_dispatcher.forklift.dto;

import com.v1rex.warehouse_dispatcher.forklift.domain.EquipmentType;
import com.v1rex.warehouse_dispatcher.location.dto.LocationResponse;
import com.v1rex.warehouse_dispatcher.task.dto.TaskResponse;

import java.util.List;

public record ForkliftResponse(
        Long id,
        Integer weightCapacity,
        EquipmentType equipmentType,
        List<TaskResponse> tasks,
        LocationResponse currentLocation
) {}
