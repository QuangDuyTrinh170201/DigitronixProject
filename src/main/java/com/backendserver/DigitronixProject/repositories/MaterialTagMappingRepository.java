package com.backendserver.DigitronixProject.repositories;

import com.backendserver.DigitronixProject.models.MaterialTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialTagMappingRepository extends JpaRepository<MaterialTagMapping, Long> {
    MaterialTagMapping findByMaterial_IdAndTag_Id(Long materialId, Long tagId);
}
