package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    @Query("SELECT s FROM Salary s WHERE s.user.id = :userId")
    List<Salary> findSalaryByUserId(@Param("userId") Long userId);
}
