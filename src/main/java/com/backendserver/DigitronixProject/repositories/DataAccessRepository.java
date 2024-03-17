package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.DataAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataAccessRepository extends JpaRepository<DataAccess, Long> {
    Optional<DataAccess> findByProductName(String productName);
}
