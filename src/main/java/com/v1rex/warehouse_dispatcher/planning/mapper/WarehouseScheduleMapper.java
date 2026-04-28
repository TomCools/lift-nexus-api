package com.v1rex.warehouse_dispatcher.planning.mapper;

import com.v1rex.warehouse_dispatcher.forklift.mapper.ForkliftMapper;
import com.v1rex.warehouse_dispatcher.location.domain.Location;
import com.v1rex.warehouse_dispatcher.forklift.domain.Forklift;
import com.v1rex.warehouse_dispatcher.task.domain.Task;
import com.v1rex.warehouse_dispatcher.planning.dto.WarehouseScheduleResponse;
import com.v1rex.warehouse_dispatcher.location.mapper.LocationMapper;
import com.v1rex.warehouse_dispatcher.task.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WarehouseScheduleMapper {

    private final LocationMapper locationMapper;
    private final ForkliftMapper forkliftMapper;
    private final TaskMapper taskMapper;

    public WarehouseScheduleResponse toResponse(
            List<Location> locations,
            List<Forklift> forklifts,
            List<Task> unassignedTasks) {

        return new WarehouseScheduleResponse(
                locations.stream().map(locationMapper::toResponse).toList(),
                forklifts.stream().map(forkliftMapper::toResponse).toList(),
                unassignedTasks.stream().map(taskMapper::toResponse).toList()
        );
    }
}