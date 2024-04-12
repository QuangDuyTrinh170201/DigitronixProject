package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.ProcessDTO;
import com.backendserver.DigitronixProject.models.Process;
import com.backendserver.DigitronixProject.responses.ProcessResponse;
import com.backendserver.DigitronixProject.services.IProcessService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/processes")
public class ProcessController {

    private final IProcessService processService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_PRODUCTION_MANAGER', 'ROLE_DIRECTOR')")
    public ResponseEntity<?> getAllProcess(){
        try{
            List<ProcessResponse> listProcess = processService.getAllProcess();
            return ResponseEntity.ok(listProcess);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_PRODUCTION_MANAGER', 'ROLE_DIRECTOR')")
    public ResponseEntity<?> getProcessById(@PathVariable Long id){
        try{
            Optional<ProcessResponse> process = processService.getProcessById(id);
            return ResponseEntity.ok(process);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> addNewProcess(@RequestBody ProcessDTO processDTO){
        try{
            ProcessResponse newProcess = processService.createProcess(processDTO);
            return ResponseEntity.ok(newProcess);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> editProcess(@PathVariable Long id, @RequestBody ProcessDTO processDTO){
        try{
            ProcessResponse editProcess = processService.updateProcess(id, processDTO);
            return ResponseEntity.ok(editProcess);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_MANAGER')")
    public ResponseEntity<?> deleteProcess(@PathVariable Long id){
        try{
            processService.deleteProcess(id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
