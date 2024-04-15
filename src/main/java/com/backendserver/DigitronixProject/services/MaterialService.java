package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.MaterialDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.models.MaterialCategory;
import com.backendserver.DigitronixProject.models.Tag;
import com.backendserver.DigitronixProject.repositories.MaterialCategoryRepository;
import com.backendserver.DigitronixProject.repositories.MaterialRepository;
import com.backendserver.DigitronixProject.repositories.TagRepository;
import com.backendserver.DigitronixProject.responses.MaterialResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Page<MaterialResponse> getAllMaterialWithPaging(String keyword, Long categoryId, PageRequest pageRequest) throws Exception {

        Page<Material> materialPage;
        materialPage = materialRepository.searchMaterial(categoryId, keyword, pageRequest);
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
    public MaterialResponse getMaterialWithId(Long id) throws DataNotFoundException {
        Material existingMaterial = materialRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find this material"));
        return MaterialResponse.fromMaterial(existingMaterial);
    }

    @Override
    public void deleteMaterial(Long id) throws DataNotFoundException {
        Optional<Material> material = materialRepository.findById(id);
        if(material.isEmpty()){
            throw new DataNotFoundException("Cannot find this material to delete, please check again!");
        }
        else if(material.get().getImage() != null){
            Path deleteFile = Paths.get("uploads/", material.get().getImage());
            File file = new File(String.valueOf(deleteFile));
            file.delete();
            material.ifPresent(materialRepository::delete);
        }else{
            material.ifPresent(materialRepository::delete);
        }
    }

    @Override
    @Transactional
    public MaterialResponse updateMaterial(Long id, MaterialDTO materialDTO) throws DataNotFoundException {
        Material findExistMaterial = materialRepository.getReferenceById(id);
        Optional<MaterialCategory> existingCat = materialCategoryRepository.findById(materialDTO.getCategoryId());
        if(existingCat.isEmpty()){
            throw new DataNotFoundException("Cannot find this category!");
        }
        String oldImage = findExistMaterial.getImage();
        findExistMaterial.setCategory(existingCat.get());
        findExistMaterial.setName(materialDTO.getName());
        findExistMaterial.setQuantity(materialDTO.getQuantity());
        findExistMaterial.setPrice(materialDTO.getPrice());
        findExistMaterial.setImage(oldImage);
        return MaterialResponse.fromMaterial(materialRepository.save(findExistMaterial));
    }

    @Override
    @Transactional
    public Material updateMaterialImage(Long materialId, String fileName) throws DataNotFoundException {
        // Tìm sản phẩm theo ID
        Material existingMaterial = materialRepository.findById(materialId)
                .orElseThrow(() -> new DataNotFoundException("Không thể tìm thấy sản phẩm với id: " + materialId));

        // Thêm URL hình ảnh vào cột img_url của sản phẩm
        existingMaterial.setImage(fileName);
        return materialRepository.save(existingMaterial);
    }


    @Override
    @Transactional
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
