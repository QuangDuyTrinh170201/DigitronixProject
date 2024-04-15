package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.ProcessDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessDetailRepository extends JpaRepository<ProcessDetail, Long> {
    Optional<ProcessDetail> findByDetailName(String detailName);
}
