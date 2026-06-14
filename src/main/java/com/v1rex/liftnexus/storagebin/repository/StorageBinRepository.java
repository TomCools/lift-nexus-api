package com.v1rex.liftnexus.storagebin.repository;

import com.v1rex.liftnexus.storagebin.domain.StorageBin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StorageBinRepository extends JpaRepository<StorageBin, Long> {
  boolean existsByBinCode(String binCode);

  @Query("SELECT s FROM StorageBin s")
  List<StorageBin> findAllForPlanning();
}
