package com.backendserver.DigitronixProject.services.Material;

import com.backendserver.DigitronixProject.dtos.MaterialDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.responses.MaterialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IMaterialService {
    Material createMaterial(MaterialDTO materialDTO) throws DataNotFoundException;

    Page<MaterialResponse> getAllMaterialWithPaging(String keyword, Long categoryId, PageRequest pageRequest) throws Exception;

    List<MaterialResponse> getAllMaterialWithoutPaging() throws DataNotFoundException;

    MaterialResponse updateMaterial(Long id, MaterialDTO materialDTO) throws Exception;

    void deleteMaterial(Long id) throws DataNotFoundException;

    Material addTagToMaterial(Long materialId, Long tagId);

    Material updateMaterialImage(Long materialId, String fileName) throws DataNotFoundException;

    MaterialResponse getMaterialWithId(Long id) throws DataNotFoundException;
}
