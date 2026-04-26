package com.v1rex.warehouse_dispatcher.mapper;

import com.v1rex.warehouse_dispatcher.domain.Location;
import com.v1rex.warehouse_dispatcher.domain.Forklift;
import com.v1rex.warehouse_dispatcher.domain.Task;
import com.v1rex.warehouse_dispatcher.dto.WarehouseScheduleResponse;
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