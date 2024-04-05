package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.repositories.MaterialRepository;
import com.backendserver.DigitronixProject.responses.MaterialListResponse;
import com.backendserver.DigitronixProject.responses.MaterialResponse;
import com.backendserver.DigitronixProject.services.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("$S{api.prefix}/materials")
public class MaterialController {
    private final IMaterialService materialService;
    private final MaterialRepository materialRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<MaterialListResponse> getAllWithPaging(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<MaterialResponse> materialPages;
        materialPages = materialService.getAllMaterialWithPaging(keyword, categoryId, pageRequest);
        int totalPages = materialPages.getTotalPages();
        List<MaterialResponse> materials = materialPages.getContent();
        return ResponseEntity.ok(MaterialListResponse
                .builder()
                .materials(materials)
                .totalPages(totalPages)
                .build());
    }
}
