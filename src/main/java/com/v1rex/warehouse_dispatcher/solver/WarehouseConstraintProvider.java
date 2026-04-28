package com.v1rex.warehouse_dispatcher.solver;

import ai.timefold.solver.core.api.score.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import com.v1rex.warehouse_dispatcher.domain.Forklift;
import com.v1rex.warehouse_dispatcher.domain.Task;

import java.util.List;

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

    // Soft constraint: sum complete travel distance of the forklift
    private Constraint minimizeTravelDistance(ConstraintFactory factory) {
        return factory.forEach(Forklift.class)
                .filter(forklift -> !forklift.getTasks().isEmpty())
                .penalize(HardSoftScore.ONE_SOFT, forklift ->{
                    int totalTraveledDistance = 0;

                    List<Task> tasks = forklift.getTasks();
                    // initial drive to the first task
                    totalTraveledDistance += (int) forklift.getCurrentLocation()
                            .distanceTo(tasks.get(0).getPickLocation());

                for (int i = 0; i < tasks.size(); i++) {
                    Task current = tasks.get(i);

                    // We calculate the travel distance from currentTask
                    // to the Delivery Location
                    totalTraveledDistance += (int) current.getPickLocation()
                            .distanceTo(current.getDeliveryLocation());

                    // if there is a next task, we calculate the travel distance
                    // from the delivery location to the pick location
                    // of the next task
                    if (i < tasks.size() - 1) {
                        Task next = tasks.get(i + 1);
                        totalTraveledDistance += (int) current.getDeliveryLocation()
                                .distanceTo(next.getPickLocation());
                    }
                }
                // todo: think about the metrics!!
                return totalTraveledDistance;

                })
                .asConstraint("Minimize travel distance");
    }
}