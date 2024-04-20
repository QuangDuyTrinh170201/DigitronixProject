package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findOrderByCustomerId(@Param("customerId") Long customerId);
}
