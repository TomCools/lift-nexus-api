package com.v1rex.warehouse_dispatcher.location.dto;

import io.smallrye.common.constraint.NotNull;

public record LocationRequest(
    @NotNull Float latitude,
    @NotNull Float longitude
) {
}
