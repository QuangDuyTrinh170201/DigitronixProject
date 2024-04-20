package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.OrderDTO;
import com.backendserver.DigitronixProject.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> getAllOrder() throws Exception;

    OrderResponse getOrderById(Long id) throws Exception;

    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws Exception;

    void deleteOrder(Long id) throws Exception;

    List<OrderResponse> getOrderByCustomerId(Long id);
}
