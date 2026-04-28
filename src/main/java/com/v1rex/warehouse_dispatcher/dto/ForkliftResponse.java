package com.v1rex.warehouse_dispatcher.dto;

import com.v1rex.warehouse_dispatcher.enums.EquipmentType;

import java.util.List;

public record ForkliftResponse(
        Long id,
        Integer weightCapacity,
        EquipmentType equipmentType,
        List<TaskResponse> tasks,
        LocationResponse currentLocation
) {}
