package com.v1rex.warehouse_dispatcher.forklift.dto;

import jakarta.validation.constraints.NotNull;

public record ForkliftLocationUpdateRequest(
        @NotNull Long locationId) {
}
