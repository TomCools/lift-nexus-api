package com.v1rex.warehouse_dispatcher.service;


import com.v1rex.warehouse_dispatcher.domain.Location;
import com.v1rex.warehouse_dispatcher.dto.PickTaskRequest;
import com.v1rex.warehouse_dispatcher.exceptions.ResourceNotFoundException;
import com.v1rex.warehouse_dispatcher.mapper.PickTaskMapper;
import com.v1rex.warehouse_dispatcher.repository.PickTaskRepository;
import com.v1rex.warehouse_dispatcher.dto.PickTaskResponse;
import com.v1rex.warehouse_dispatcher.domain.PickTask;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PickTaskService {
    private final PickTaskRepository pickTaskRepository;
    private final PickTaskMapper pickTaskMapper;

    private final LocationService locationService;

    @Transactional
    public PickTaskResponse createPickTask(PickTaskRequest request){
        Location pickLocation = locationService.findEntityById(request.pickLocationId());
        Location deliveryLocation = locationService.findEntityById(request.deliveryLocationId());

        PickTask task = pickTaskMapper.toEntity(request);

        task.setPickLocation(pickLocation);
        task.setDeliveryLocation(deliveryLocation);


        PickTask savedTask = pickTaskRepository.save(task);

        return pickTaskMapper.toResponse(savedTask);
    }

    @Transactional(readOnly = true)
    public PickTaskResponse findById(Long id) {
        return pickTaskMapper.toResponse(findEntityById(id));
    }

     @Transactional(readOnly = true)
    public Page<PickTaskResponse> findAll(Pageable pageable) {
         return findAllEntities(pageable).map(pickTaskMapper::toResponse);
     }

     @Transactional(readOnly = true)
     public Page<PickTaskResponse> findWithCapacityGreaterThan(
                                                            Integer weightCapacity,
                                                            Pageable pageable
     ){
         return findEntitiesWithWeightGreaterThan(weightCapacity, pageable)
                 .map(pickTaskMapper::toResponse);
     }

     public PickTask findEntityById(Long id) {
        return pickTaskRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("PickTask with " + id + " not found.") );
    }

     public Page<PickTask> findAllEntities(Pageable pageable){
        return pickTaskRepository.findAll(pageable);
 }

     public Page<PickTask> findEntitiesWithWeightGreaterThan(
             @Min(0 )Integer weight,
             Pageable pageable){

         return pickTaskRepository.findByWeightGreaterThan(weight, pageable);

     }

}
