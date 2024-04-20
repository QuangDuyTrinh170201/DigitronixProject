package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.models.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    @Query("SELECT p FROM Production p WHERE p.order.id = :orderId")
    List<Production> findProductionByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT p FROM Production p WHERE p.user.id = :userId")
    List<Production> findProductionByUserId(@Param("userId") Long userId);
}
