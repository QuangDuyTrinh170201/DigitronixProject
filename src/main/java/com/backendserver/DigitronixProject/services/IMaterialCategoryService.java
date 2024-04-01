package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.MaterialCategoryDTO;
import com.backendserver.DigitronixProject.models.MaterialCategory;

import java.util.List;

public interface IMaterialCategoryService {
    MaterialCategory createMaterialCategory(MaterialCategoryDTO materialCategoryDTO) throws Exception;

    List<MaterialCategory> getAllMaterialCategories() throws Exception;

    MaterialCategory editMaterialCategory(Long id, MaterialCategoryDTO materialCategoryDTO) throws Exception;

    void deleteMaterialCategories(Long id) throws Exception;
}
