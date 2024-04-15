package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    Optional<Process> findByProcessName(String processName);

    @Query("SELECT p FROM Process p WHERE p.product.id = :productId")
    List<Process> findProcessesByProductId(@Param("productId") Long productId);
}
