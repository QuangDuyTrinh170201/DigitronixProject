package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByName(String name);
}
