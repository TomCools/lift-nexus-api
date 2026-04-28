package com.v1rex.warehouse_dispatcher.dto;

import com.v1rex.warehouse_dispatcher.enums.EquipmentType;
import com.v1rex.warehouse_dispatcher.enums.TaskStatus;

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
