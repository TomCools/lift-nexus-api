package com.v1rex.warehouse_dispatcher.dto;

public record PickTaskResponse(
        Long id,
        LocationResponse pickLocation,
        LocationResponse deliveryLocation,
        Integer weight,
        Long forkliftId
) {

}
