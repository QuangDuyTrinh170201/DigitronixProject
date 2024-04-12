package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    Optional<Process> findByProcessName(String processName);
}
