package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.ProcessDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProcessDetailRepository extends JpaRepository<ProcessDetail, Long> {
    Optional<ProcessDetail> findByDetailName(String detailName);

    ProcessDetail findByIntensity(Long intensity);

    @Query("SELECT p FROM ProcessDetail p WHERE p.process.id = :processId")
    List<ProcessDetail> findProcessDetailByProcessId(@Param("processId") Long processId);
}
