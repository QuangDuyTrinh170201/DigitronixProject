package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.services.MaterialTagMapping.IMaterialTagMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/material_tags_mapping")
@PreAuthorize("hasRole('ROLE_DIRECTOR')")
@RequiredArgsConstructor
public class MaterialTagMappingController {
    private final IMaterialTagMapping materialTagMapping;
    @DeleteMapping("/{materialId}/tags/{tagId}")
    public ResponseEntity<String> removeTagFromMaterial(@PathVariable Long materialId, @PathVariable Long tagId){
        try{
            materialTagMapping.removeTagOnMaterial(materialId, tagId);
            return ResponseEntity.ok("Remove successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
