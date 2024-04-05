package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.MaterialDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.responses.MaterialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IMaterialService {
    Material createMaterial(MaterialDTO materialDTO) throws DataNotFoundException;

    Page<MaterialResponse> getAllMaterialWithPaging(String keyword, Long id, PageRequest pageRequest);

    Material addTagToMaterial(Long materialId, Long tagId);

    List<MaterialResponse> getAllMaterialWithoutPaging() throws DataNotFoundException;

    Material updateMaterial(Long id, MaterialDTO materialDTO) throws DataNotFoundException;

    void deleteMaterial(Long id) throws DataNotFoundException;
}
