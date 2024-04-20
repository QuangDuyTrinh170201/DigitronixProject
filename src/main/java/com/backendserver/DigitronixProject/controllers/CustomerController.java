package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.CustomerDTO;
import com.backendserver.DigitronixProject.responses.CustomerResponse;
import com.backendserver.DigitronixProject.services.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE')")
    public ResponseEntity<?> getAllCustomer(){
        try{
            List<CustomerResponse> customerResponse = customerService.getAllCustomer();
            return ResponseEntity.ok(customerResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE')")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        try{
            CustomerResponse customerResponse = customerService.getCustomerWithId(id);
            return ResponseEntity.ok(customerResponse);
        }catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE')")
    public ResponseEntity<?> createNewCustomer(@RequestBody CustomerDTO customerDTO){
        try{
            CustomerResponse customerResponse = customerService.createCustomer(customerDTO);
            return ResponseEntity.ok(customerResponse);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE')")
    public ResponseEntity<?> editCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        try{
            CustomerResponse customerResponse = customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.ok(customerResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR', 'ROLE_SALE')")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        try{
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
