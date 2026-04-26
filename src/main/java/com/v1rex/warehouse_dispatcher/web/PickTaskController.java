package com.v1rex.warehouse_dispatcher.web;


import com.v1rex.warehouse_dispatcher.dto.ForkliftResponse;
import com.v1rex.warehouse_dispatcher.dto.PickTaskRequest;
import com.v1rex.warehouse_dispatcher.dto.PickTaskResponse;
import com.v1rex.warehouse_dispatcher.service.PickTaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/picktasks")
@Validated
@RequiredArgsConstructor
public class PickTaskController {
    private final PickTaskService pickTaskService;

    @PostMapping
    public ResponseEntity<PickTaskResponse> createPickTask(
            @RequestBody @Valid PickTaskRequest pickTaskRequest
            ){
        PickTaskResponse savedPickTask =
                pickTaskService.createPickTask(pickTaskRequest);


        URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedPickTask.id())
        .toUri();

        return ResponseEntity.created(location).body(savedPickTask);

    }


    @GetMapping
    public ResponseEntity<Page<PickTaskResponse>> findAllPickTasks(
          @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(pickTaskService.findAll(pageable));
    }


    @GetMapping("/search")
    public ResponseEntity<Page<PickTaskResponse>> findWithWeight(
            @RequestParam @Min(1) Integer minCapacity,
            @PageableDefault(size = 10, sort = "weight") Pageable pageable
    ) {
        return ResponseEntity.ok(pickTaskService.findWithCapacityGreaterThan(minCapacity, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PickTaskResponse> getPickTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(pickTaskService.findById(id));
    }




}
