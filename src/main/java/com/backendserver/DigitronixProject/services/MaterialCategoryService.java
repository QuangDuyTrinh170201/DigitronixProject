package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.MaterialCategoryDTO;
import com.backendserver.DigitronixProject.models.MaterialCategory;
import com.backendserver.DigitronixProject.repositories.MaterialCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialCategoryService implements IMaterialCategoryService {
    private final MaterialCategoryRepository materialCategoryRepository;

    @Override
    public MaterialCategory createMaterialCategory(MaterialCategoryDTO materialCategoryDTO) throws Exception {
        boolean checkExists = materialCategoryRepository.existsByName(materialCategoryDTO.getName());
        if(checkExists){
            throw new Exception("This category already existed!");
        }
        MaterialCategory newCategory = MaterialCategory.builder()
                .name(materialCategoryDTO.getName())
                .build();
        return materialCategoryRepository.save(newCategory);
    }

    @Override
    public List<MaterialCategory> getAllMaterialCategories() throws Exception {
        List<MaterialCategory> categories = materialCategoryRepository.findAll();
        return categories;
    }

    @Override
    public MaterialCategory editMaterialCategory(Long id, MaterialCategoryDTO materialCategoryDTO) throws Exception {
        MaterialCategory updateCat = materialCategoryRepository.findById(id).orElseThrow(() -> new Exception("Cannot find this material category in application!"));
        Optional<MaterialCategory> checkExists = materialCategoryRepository.findByName(materialCategoryDTO.getName());
        if(checkExists.isPresent()){
            throw new Exception("Cannot update because another category has the same name is exist!");
        }
        updateCat.setName(materialCategoryDTO.getName());
        return materialCategoryRepository.save(updateCat);
    }

    @Override
    public void deleteMaterialCategories(Long id) throws Exception {
        MaterialCategory findCat = materialCategoryRepository.findById(id).orElseThrow(() -> new Exception("Cannot find this category, please check again!"));
        materialCategoryRepository.delete(findCat);
    }
}
