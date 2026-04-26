package com.v1rex.warehouse_dispatcher.mapper;

import com.v1rex.warehouse_dispatcher.domain.Location;
import com.v1rex.warehouse_dispatcher.dto.LocationRequest;
import com.v1rex.warehouse_dispatcher.dto.LocationResponse;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationRequest request){
        if (request == null ) return null;
        return Location.builder()
                .longitude(request.longitude())
                .latitude(request.latitude())
                .build();
    }

    public LocationResponse toResponse(Location entity) {
        if (entity == null) return null;
        return new LocationResponse(
                entity.getId(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }
}