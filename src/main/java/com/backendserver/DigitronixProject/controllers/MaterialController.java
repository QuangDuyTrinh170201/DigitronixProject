package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.MaterialDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Material;
import com.backendserver.DigitronixProject.repositories.MaterialRepository;
import com.backendserver.DigitronixProject.responses.MaterialListResponse;
import com.backendserver.DigitronixProject.responses.MaterialResponse;
import com.backendserver.DigitronixProject.services.Material.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/materials")
public class MaterialController {
    private final IMaterialService materialService;
    private final MaterialRepository materialRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllWithPaging(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) throws Exception {
        try{
            PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
            Page<MaterialResponse> materialPage;
            materialPage = materialService.getAllMaterialWithPaging(keyword, categoryId, pageRequest);
            int totalPages = materialPage.getTotalPages();
            List<MaterialResponse> materials = materialPage.getContent();
            return ResponseEntity.ok(MaterialListResponse
                    .builder()
                    .materials(materials)
                    .totalPages(totalPages)
                    .build());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/allNoPaging")
    public ResponseEntity<?> getAllNoPaging(){
        try{
            List<MaterialResponse> materialResponses = materialService.getAllMaterialWithoutPaging();
            return ResponseEntity.ok(materialResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById (@PathVariable Long id){
        try{
            MaterialResponse materialResponse = materialService.getMaterialWithId(id);
            return ResponseEntity.ok(materialResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> createMaterial(@RequestBody MaterialDTO materialDTO){
        try{
            Material newMaterial = materialService.createMaterial(materialDTO);
            return ResponseEntity.ok(newMaterial);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> updateMaterials(@PathVariable Long id, @RequestBody MaterialDTO materialDTO){
        try{
            MaterialResponse updateMaterial = materialService.updateMaterial(id, materialDTO);
            return ResponseEntity.ok(updateMaterial);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try{
            Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if(resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }else{
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpg").toUri()));
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{materialId}/image")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> updateMaterialImage(@PathVariable("materialId") Long materialId,
                                                @ModelAttribute MultipartFile file) {
        try {
            String imageUpdate = storeFile(file);
            Material updateMaterial = materialService.updateMaterialImage(materialId, imageUpdate);
            return ResponseEntity.ok().body("Import image successfully!");
        } catch (DataNotFoundException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đến file đích
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long id){
        try{
            materialService.deleteMaterial(id);
            return ResponseEntity.ok("Delete successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{materialId}/tags/{tagId}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> addTagToMaterial(@PathVariable Long materialId, @PathVariable Long tagId) {
        Material updatedMaterial = materialService.addTagToMaterial(materialId, tagId);
        return ResponseEntity.ok().body("Add Tag Successfully");
    }
}
