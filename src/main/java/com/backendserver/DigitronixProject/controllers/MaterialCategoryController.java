package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.MaterialCategoryDTO;
import com.backendserver.DigitronixProject.models.MaterialCategory;
import com.backendserver.DigitronixProject.services.IMaterialCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/material_categories")
public class MaterialCategoryController {
    private final IMaterialCategoryService materialCategoryService;
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMaterialCategories(){
        try{
            List<MaterialCategory> listCategory = materialCategoryService.getAllMaterialCategories();
            return ResponseEntity.ok().body(listCategory);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> createMaterialCategory(@RequestBody MaterialCategoryDTO materialCategoryDTO){
        try{
            MaterialCategory newMaterialCategory = materialCategoryService.createMaterialCategory(materialCategoryDTO);
            return ResponseEntity.ok().body(newMaterialCategory);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody MaterialCategoryDTO materialCategoryDTO){
        try{
            MaterialCategory existingCategory = materialCategoryService.editMaterialCategory(id, materialCategoryDTO);
            return ResponseEntity.ok().body(existingCategory);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try{
            materialCategoryService.deleteMaterialCategories(id);
            return ResponseEntity.ok().body("Delete category successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
