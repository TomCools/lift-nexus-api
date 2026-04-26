package com.v1rex.warehouse_dispatcher.repository;

import com.v1rex.warehouse_dispatcher.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
