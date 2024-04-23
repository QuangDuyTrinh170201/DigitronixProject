package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.ProductionDetailDTO;
import com.backendserver.DigitronixProject.responses.ProductionDetailResponse;
import com.backendserver.DigitronixProject.services.ProductionDetail.IProductionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/production_details")
public class ProductionDetailController {
    private final IProductionDetailService productionDetailService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER', 'ROLE_WORKER')")
    public ResponseEntity<?> getAllProductionDetails(){
        try{
            List<ProductionDetailResponse> productionDetailResponseList = productionDetailService.getAllProductionDetail();
            return ResponseEntity.ok(productionDetailResponseList);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER', 'ROLE_WORKER')")
    public ResponseEntity<?> getProductionDetailById(@PathVariable Long id){
        try{
            ProductionDetailResponse productionDetailResponse = productionDetailService.getProductionDetailById(id);
            return ResponseEntity.ok(productionDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/production/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER', 'ROLE_WORKER')")
    public ResponseEntity<?> getProductionDetailByProductionId(@PathVariable Long id){
        try{
            ProductionDetailResponse productionDetailResponse = productionDetailService.getProductionDetailById(id);
            return ResponseEntity.ok(productionDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_WORKER')")
    public ResponseEntity<?> getProductionByUserId(@PathVariable Long id){
        try{
            List<ProductionDetailResponse> productionDetailResponseList = productionDetailService.getProductionDetailByUserId(id);
            return ResponseEntity.ok(productionDetailResponseList);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> createProductionDetail(@RequestBody ProductionDetailDTO productionDetailDTO){
        try{
            ProductionDetailResponse productionDetailResponse = productionDetailService.createProductionDetail(productionDetailDTO);
            return ResponseEntity.ok(productionDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER', 'ROLE_WORKER')")
    public ResponseEntity<?> editProductionDetail(@PathVariable Long id, @RequestBody ProductionDetailDTO productionDetailDTO){
        try{
            ProductionDetailResponse productionDetailResponse = productionDetailService.editProductionDetail(id, productionDetailDTO);
            return ResponseEntity.ok(productionDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> deleteProductionDetail(@PathVariable Long id){
        try{
            productionDetailService.deleteProductionDetail(id);
            return ResponseEntity.ok("Delete production detail successfully");
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
