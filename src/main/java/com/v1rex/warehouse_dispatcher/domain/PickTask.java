package com.v1rex.warehouse_dispatcher.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PickTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pick_location_id", nullable = false)
    private Location pickLocation;

    @ManyToOne
    @JoinColumn(name = "delivery_location_id", nullable = false)
    private Location deliveryLocation;


    @NotNull @Min(value = 1, message = "Weight must be greater than 0")
    @Column(name = "weight", nullable = false, columnDefinition = "integer check (weight > 0)")
    private Integer weight;


    @ManyToOne
    @JoinColumn(name = "forklift_id")
    @JsonIgnore
    private Forklift forklift;
}