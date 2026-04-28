package com.v1rex.warehouse_dispatcher.task.dto;

import com.v1rex.warehouse_dispatcher.forklift.domain.EquipmentType;
import com.v1rex.warehouse_dispatcher.task.enums.TaskStatus;
import com.v1rex.warehouse_dispatcher.location.dto.LocationResponse;

public record TaskResponse(
        Long id,
        LocationResponse pickLocation,
        LocationResponse deliveryLocation,
        Integer weight,
        EquipmentType requiredEquipment,
        TaskStatus status,
        Long forkliftId
) {

}
