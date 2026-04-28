package com.v1rex.warehouse_dispatcher.location.mapper;

import com.v1rex.warehouse_dispatcher.location.domain.Location;
import com.v1rex.warehouse_dispatcher.location.dto.LocationRequest;
import com.v1rex.warehouse_dispatcher.location.dto.LocationResponse;
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