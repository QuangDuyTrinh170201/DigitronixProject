package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
