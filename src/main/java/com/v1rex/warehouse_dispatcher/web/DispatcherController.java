package com.v1rex.warehouse_dispatcher.web;

import com.v1rex.warehouse_dispatcher.domain.WarehouseSchedule;
import com.v1rex.warehouse_dispatcher.service.WarehouseDispatcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dispatcher")
@RequiredArgsConstructor
public class DispatcherController {

    private final WarehouseDispatcherService dispatcherService;

    @PostMapping("/solve")
    public String solve() {
        dispatcherService.startSolving();
        return "Solver started in the background. Optimization is running.";
    }

    @GetMapping("/solution")
    public WarehouseSchedule getSolution() {
        return dispatcherService.buildCurrentState();
    }
}