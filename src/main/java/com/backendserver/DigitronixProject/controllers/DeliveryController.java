package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.DeliveryDTO;
import com.backendserver.DigitronixProject.responses.DeliveryResponse;
import com.backendserver.DigitronixProject.services.Delivery.IDeliveryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final IDeliveryService deliveryService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_DRIVER')")
    public ResponseEntity<?> getAllDelivery(){
        try{
            List<DeliveryResponse> deliveryResponseList = deliveryService.getAllDelivery();
            return ResponseEntity.ok(deliveryResponseList);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_DRIVER')")
    public ResponseEntity<?> getDeliveryById(@PathVariable Long id){
        try{
            DeliveryResponse deliveryResponse = deliveryService.getDeliveryById(id);
            return ResponseEntity.ok(deliveryResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> createNewDelivery(@RequestBody DeliveryDTO deliveryDTO){
        try{
            DeliveryResponse deliveryResponse = deliveryService.createDelivery(deliveryDTO);
            return ResponseEntity.ok(deliveryResponse);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_DRIVER')")
    public ResponseEntity<?> editDelivery(@PathVariable Long id, @RequestBody DeliveryDTO deliveryDTO){
        try{
            DeliveryResponse deliveryResponse = deliveryService.updateDelivery(id, deliveryDTO);
            return ResponseEntity.ok(deliveryResponse);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> deleteDelivery(@PathVariable Long id){
        try{
            deliveryService.deleteDelivery(id);
            return ResponseEntity.ok("Delete delivery successfully!");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
