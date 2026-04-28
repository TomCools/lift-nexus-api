package com.v1rex.warehouse_dispatcher.location.repository;

import com.v1rex.warehouse_dispatcher.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
