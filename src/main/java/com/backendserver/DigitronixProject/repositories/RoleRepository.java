package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
