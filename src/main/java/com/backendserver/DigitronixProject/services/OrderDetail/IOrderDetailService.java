package com.backendserver.DigitronixProject.services.OrderDetail;

import com.backendserver.DigitronixProject.dtos.OrderDetailDTO;
import com.backendserver.DigitronixProject.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetailResponse> getOrderDetailByOrderId(Long id) throws Exception;

    OrderDetailResponse getOrderDetailById(Long id) throws Exception;

    OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception;

    void deleteOrderDetail(Long id) throws Exception;
}
