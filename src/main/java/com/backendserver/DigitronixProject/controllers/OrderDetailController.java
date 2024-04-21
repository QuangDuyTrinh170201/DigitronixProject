package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.dtos.OrderDetailDTO;
import com.backendserver.DigitronixProject.responses.OrderDetailResponse;
import com.backendserver.DigitronixProject.responses.OrderResponse;
import com.backendserver.DigitronixProject.services.OrderDetail.IOrderDetailService;
import com.backendserver.DigitronixProject.services.OrderDetail.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order_details")
@PreAuthorize("hasAnyRole('ROLE_SALE', 'ROLE_DIRECTOR', 'ROLE_PRODUCTION_MANAGER')")
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable Long id){
        try{
            List<OrderDetailResponse> orderDetailResponse = orderDetailService.getOrderDetailByOrderId(id);
            return ResponseEntity.ok(orderDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable Long id){
        try{
            OrderDetailResponse orderDetailResponse = orderDetailService.getOrderDetailById(id);
            return ResponseEntity.ok(orderDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDTO orderDetailDTO){
        try{
            OrderDetailResponse orderDetailResponse = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(orderDetailResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id){
        try{
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
