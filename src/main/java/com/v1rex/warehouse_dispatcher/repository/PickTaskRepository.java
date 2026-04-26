package com.v1rex.warehouse_dispatcher.repository;

import com.v1rex.warehouse_dispatcher.domain.PickTask;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickTaskRepository extends JpaRepository<PickTask, Long> {
    List<PickTask> findByForkliftIsNull();

    Page<PickTask> findByWeightGreaterThan(
            @NotNull
           @Min(value = 1, message = "Weight must be greater than 0") Integer weightIsGreaterThan,
            Pageable pageable);
}
