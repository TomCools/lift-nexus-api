package com.v1rex.warehouse_dispatcher.dto;

import com.v1rex.warehouse_dispatcher.enums.TaskStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        @NotNull Long pickLocationId,
        @NotNull Long deliveryLocationId,
        TaskStatus status,
        @NotNull @Min(1) Integer weight
) {}
