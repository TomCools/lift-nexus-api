package com.v1rex.warehouse_dispatcher.planning.dto;

import com.v1rex.warehouse_dispatcher.forklift.dto.ForkliftResponse;
import com.v1rex.warehouse_dispatcher.location.dto.LocationResponse;
import com.v1rex.warehouse_dispatcher.task.dto.TaskResponse;

import java.util.List;

public record WarehouseScheduleResponse(
        List<LocationResponse> locations,
        List<ForkliftResponse> forkLifts,
        List<TaskResponse> unassignedTasks) {


}
