package com.v1rex.warehouse_dispatcher.solver;

import ai.timefold.solver.core.api.score.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import com.v1rex.warehouse_dispatcher.domain.Forklift;
import com.v1rex.warehouse_dispatcher.domain.PickTask;

public class WarehouseConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                forkliftCapacity(constraintFactory),
                minimizeTravelDistance(constraintFactory)
        };
    }

    // 1. HARD CONSTRAINT: Sum weights inside the Forklift's list
    private Constraint forkliftCapacity(ConstraintFactory factory) {
        return factory.forEach(Forklift.class)
                .filter(forklift -> !forklift.getTasks().isEmpty())
                .penalize(HardSoftScore.ONE_HARD,
                        forklift -> {
                            int totalWeight = forklift.getTasks().stream()
                                    .mapToInt(PickTask::getWeight)
                                    .sum();
                            return Math.max(0, totalWeight - forklift.getWeightCapacity());
                        })
                .asConstraint("Forklift capacity limit");
    }

    // 2. SOFT CONSTRAINT: Sum travel distance for all tasks in all lists
    private Constraint minimizeTravelDistance(ConstraintFactory factory) {
        return factory.forEach(Forklift.class)
                .flattenLast(Forklift::getTasks) // This turns the list into individual tasks for the solver
                .penalize(HardSoftScore.ONE_SOFT,
                        task -> (int) task.getPickLocation().distanceTo(task.getDeliveryLocation()))
                .asConstraint("Minimize travel distance");
    }
}