package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.OrderDTO;
import com.backendserver.DigitronixProject.responses.OrderResponse;
import com.backendserver.DigitronixProject.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_SALE', 'ROLE_DIRECTOR')")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrder(){
        try{
            List<OrderResponse> orderResponses = orderService.getAllOrder();
            return ResponseEntity.ok(orderResponses);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){
        try{
            OrderResponse orderResponse = orderService.getOrderById(id);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO){
        try{
            OrderResponse orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        try{
            OrderResponse orderResponse = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Delete order successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getOrderByCustomerId(@PathVariable Long id){
        try{
            List<OrderResponse> orderResponse = orderService.getOrderByCustomerId(id);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
