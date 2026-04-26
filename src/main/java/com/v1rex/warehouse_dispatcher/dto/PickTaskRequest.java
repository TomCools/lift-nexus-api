package com.v1rex.warehouse_dispatcher.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PickTaskRequest(
        @NotNull Long pickLocationId,
        @NotNull Long deliveryLocationId,
        @NotNull @Min(1) Integer weight
) {}
