package com.backendserver.DigitronixProject.services.OrderDetail;

import com.backendserver.DigitronixProject.dtos.OrderDetailDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.models.OrderDetail;
import com.backendserver.DigitronixProject.models.Product;
import com.backendserver.DigitronixProject.repositories.OrderDetailRepository;
import com.backendserver.DigitronixProject.repositories.OrderRepository;
import com.backendserver.DigitronixProject.repositories.ProductRepository;
import com.backendserver.DigitronixProject.responses.OrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderDetailResponse> getOrderDetailByOrderId(Long id) throws Exception {
        List<OrderDetail> orderDetailResponses = orderDetailRepository.findOrderDetailByOrderId(id);
        return orderDetailResponses.stream().map(OrderDetailResponse::fromOrderDetail).toList();
    }

    @Override
    public OrderDetailResponse getOrderDetailById(Long id) throws Exception {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find this order detail"));
        return OrderDetailResponse.fromOrderDetail(orderDetail);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail orderDetail = orderDetailRepository.getReferenceById(id);
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this order!"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()->new DataNotFoundException("Cannot find this product"));

        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setProduct(product);
        orderDetail.setOrder(order);
        orderDetail = orderDetailRepository.save(orderDetail);
        return OrderDetailResponse.fromOrderDetail(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) throws Exception {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find this order detail"));
        orderDetailRepository.delete(orderDetail);
    }
}
