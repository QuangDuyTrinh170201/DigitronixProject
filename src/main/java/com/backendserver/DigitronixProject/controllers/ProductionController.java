package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.ProductionDTO;
import com.backendserver.DigitronixProject.responses.ProductionResponse;
import com.backendserver.DigitronixProject.services.Production.IProductionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/productions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER')")
public class ProductionController {
    private final IProductionService productionService;

    @GetMapping("")
    public ResponseEntity<?> getAllProduction(){
        try{
            List<ProductionResponse> productionResponse = productionService.getAllProduction();
            return ResponseEntity.ok(productionResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductionWithId(@PathVariable Long id){
        try{
            ProductionResponse productionResponse = productionService.getProductionWithId(id);
            return ResponseEntity.ok(productionResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createProduction(@RequestBody ProductionDTO productionDTO){
        try{
            ProductionResponse productionResponse = productionService.createProduction(productionDTO);
            return ResponseEntity.ok(productionResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduction(@PathVariable Long id, @RequestBody ProductionDTO productionDTO){
        try{
            ProductionResponse productionResponse = productionService.updateProduction(id, productionDTO);
            return ResponseEntity.ok(productionResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getProductionWithUserId(@PathVariable Long id){
        try{
            List<ProductionResponse> productionResponse = productionService.getProductionWithUserId(id);
            return ResponseEntity.ok(productionResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("order/{id}")
    public ResponseEntity<?> getProductionWithOrderId(@PathVariable Long id){
        try{
            List<ProductionResponse> productionResponses = productionService.getProductionWithOrderId(id);
            return ResponseEntity.ok(productionResponses);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
