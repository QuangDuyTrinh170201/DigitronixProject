package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.MaterialDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.models.MaterialCategory;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.repositories.MaterialCategoryRepository;
import com.backendserver.DigitronixProject.repositories.MaterialRepository;
import com.backendserver.DigitronixProject.repositories.TagRepository;
import com.backendserver.DigitronixProject.responses.MaterialResponse;
import com.backendserver.DigitronixProject.responses.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService implements IMaterialService{
    private final MaterialRepository materialRepository;
    private final MaterialCategoryRepository materialCategoryRepository;
    private final TagRepository tagRepository;

    @Override
    public Material createMaterial(MaterialDTO materialDTO) throws DataNotFoundException {
        MaterialCategory existedCat = materialCategoryRepository.findById(materialDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Cannot find this category!"));
        Material newMaterial = new Material();
        newMaterial.setName(materialDTO.getName());
        newMaterial.setPrice(materialDTO.getPrice());
        newMaterial.setCategory(existedCat);
        newMaterial.setQuantity(materialDTO.getQuantity());

        newMaterial = materialRepository.save(newMaterial);
        return newMaterial;
    }

    @Override
    public Page<MaterialResponse> getAllMaterialWithPaging(String keyword, Long id, PageRequest pageRequest) {
        //lâý danh sách sản phẩm theo page và limit
        Page<Material> materialPage;
        materialPage = materialRepository.searchMaterial(id, keyword, pageRequest);
        return materialPage.map(MaterialResponse::fromMaterial);
    }

    @Override
    public List<MaterialResponse> getAllMaterialWithoutPaging() throws DataNotFoundException {
        List<Material> listAllMaterial = materialRepository.findAll();
        if(listAllMaterial.isEmpty()){
            throw new DataNotFoundException("Empty material in database!");
        }
        return listAllMaterial.stream().map(MaterialResponse::fromMaterial).toList();
    }

    @Override
    public void deleteMaterial(Long id) throws DataNotFoundException {
        Optional<Material> material = materialRepository.findById(id);
        if(material.isEmpty()){
            throw new DataNotFoundException("Cannot find this material to delete, please check again!");
        }
        else {
            materialRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Material updateMaterial(Long id, MaterialDTO materialDTO) throws DataNotFoundException {
        Optional<Material> findExistMaterial = materialRepository.findById(id);
        if(findExistMaterial.isEmpty()){
            throw new DataNotFoundException("Cannot find this material!");
        }
        MaterialCategory existingCat = materialCategoryRepository.findById(materialDTO.getCategoryId())
                .orElseThrow(() -> new DataIntegrityViolationException("Cannot find this material category!"));
        String oldImage = findExistMaterial.get().getImage();
        findExistMaterial.get().setCategory(existingCat);
        findExistMaterial.get().setName(materialDTO.getName());
        findExistMaterial.get().setQuantity(materialDTO.getQuantity());
        findExistMaterial.get().setPrice(materialDTO.getPrice());
        findExistMaterial.get().setImage(oldImage);
        return materialRepository.save(findExistMaterial.get());
    }

    @Override
    public Material addTagToMaterial(Long materialId, Long tagId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // Kiểm tra xem sản phẩm đã có tag chưa
        if (material.getTags().contains(tag)) {
            throw new IllegalArgumentException("Material already has this tag");
        }
        // Thêm tag vào sản phẩm
        material.getTags().add(tag);
        // Lưu sản phẩm và trả về
        return materialRepository.save(material);
    }
}
