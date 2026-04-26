package com.v1rex.warehouse_dispatcher.dto;

import java.util.List;

public record WarehouseScheduleResponse(
        List<LocationResponse> locations,
        List<ForkliftResponse> forkLifts,
        List<TaskResponse> unassignedTasks) {


}
