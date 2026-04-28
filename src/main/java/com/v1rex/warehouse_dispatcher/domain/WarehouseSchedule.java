package com.v1rex.warehouse_dispatcher.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.HardSoftScore;
import lombok.*;

import java.util.List;

@PlanningSolution
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseSchedule {

    @ProblemFactCollectionProperty
    private List<Location> locations;

    @ValueRangeProvider(id = "taskPoolRange")
    @PlanningEntityCollectionProperty
    private List<Task> taskPool;

    @PlanningEntityCollectionProperty
    private List<Forklift> forklifts;

    @PlanningScore
    private HardSoftScore score;
}