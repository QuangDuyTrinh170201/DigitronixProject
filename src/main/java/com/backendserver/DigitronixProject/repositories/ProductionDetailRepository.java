package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.models.ProductionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductionDetailRepository extends JpaRepository<ProductionDetail, Long> {

    @Query("SELECT p FROM ProductionDetail p WHERE p.production.id = :productionId")
    List<ProductionDetail> findProductionDetailByProductionId(@Param("productionId") Long productionId);

    @Query("SELECT p FROM ProductionDetail p WHERE p.user.id = :userId")
    List<ProductionDetail> findProductionDetailByUserId(@Param("userId") Long userId);
}
