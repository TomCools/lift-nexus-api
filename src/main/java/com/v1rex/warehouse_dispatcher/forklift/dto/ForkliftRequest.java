package com.v1rex.warehouse_dispatcher.forklift.dto;

import com.v1rex.warehouse_dispatcher.forklift.domain.EquipmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;


public record ForkliftRequest(
        @NotNull @Min(1) Integer weightCapacity,
        @NotNull EquipmentType equipmentType
) {}
