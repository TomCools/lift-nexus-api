package com.v1rex.warehouse_dispatcher.solver;

import ai.timefold.solver.core.api.score.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import com.v1rex.warehouse_dispatcher.domain.Forklift;
import com.v1rex.warehouse_dispatcher.domain.Task;

public class WarehouseConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                forkliftCapacity(constraintFactory),
                minimizeTravelDistance(constraintFactory)
        };
    }


    // Hard constraint: check if all the assigned tasks to a Forklift does
    // not exceed the capacity of the forklift
    private Constraint forkliftCapacity(ConstraintFactory factory) {
        return factory.forEach(Task.class) // Start with the Task
            .filter(task -> task.getForklift() != null)
            .filter(task -> task.getWeight() > task.getForklift().getWeightCapacity())
            .penalize(HardSoftScore.ONE_HARD)
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