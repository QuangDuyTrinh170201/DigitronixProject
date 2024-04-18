package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.HandleProcessDetailDTO;
import com.backendserver.DigitronixProject.dtos.ProcessDetailDTO;
import com.backendserver.DigitronixProject.responses.ProcessDetailResponse;
import com.backendserver.DigitronixProject.services.IProcessDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/process_details")
public class ProcessDetailController {
    private final IProcessDetailService processDetailService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_PRODUCTION_MANAGER', 'ROLE_DIRECTOR')")
    public ResponseEntity<?> getAllProcessDetail(){
        try{
            List<ProcessDetailResponse> processDetailResponse = processDetailService.getAllProcessDetail();
            return ResponseEntity.ok(processDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_PRODUCTION_MANAGER', 'ROLE_DIRECTOR')")
    public ResponseEntity<?> getProcessDetailById(@PathVariable Long id){
        try{
            ProcessDetailResponse processDetailResponse = processDetailService.getProcessDetailId(id);
            return ResponseEntity.ok(processDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> createProcessDetail(@RequestBody ProcessDetailDTO processDetailDTO){
        try{
            ProcessDetailResponse processDetailResponse = processDetailService.createProcessDetail(processDetailDTO);
            return ResponseEntity.ok(processDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> updateProcessDetail(@PathVariable Long id, @RequestBody ProcessDetailDTO processDetailDTO){
        try{
            ProcessDetailResponse processDetailResponse = processDetailService.updateProcessDetail(id, processDetailDTO);
            return ResponseEntity.ok(processDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> deleteProcessDetail(@PathVariable Long id){
        try{
            processDetailService.deleteProcessDetail(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> switchIntensity(@PathVariable Long id, @RequestBody HandleProcessDetailDTO handleProcessDetailDTO){
        try{
            String value = processDetailService.switchProcessIntensity(id, handleProcessDetailDTO);
            return ResponseEntity.ok(value);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/setIsFinal/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> setIsFinal(@PathVariable Long id){
        try{
            String value = processDetailService.setIsFinal(id);
            return ResponseEntity.ok(value);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
