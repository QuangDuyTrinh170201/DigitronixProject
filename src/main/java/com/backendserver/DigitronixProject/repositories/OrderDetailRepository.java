package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.OrderDetail;
import com.backendserver.DigitronixProject.models.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT o FROM OrderDetail o WHERE o.order.id = :orderId")
    List<OrderDetail> findOrderDetailByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT o FROM OrderDetail o WHERE o.product.id = :productId")
    List<OrderDetail> findOrderDetailByProductId(@Param("productId") Long productId);
}
