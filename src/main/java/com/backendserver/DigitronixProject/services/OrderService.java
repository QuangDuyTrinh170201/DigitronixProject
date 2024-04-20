package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.OrderDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Customer;
import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.models.User;
import com.backendserver.DigitronixProject.repositories.CustomerRepository;
import com.backendserver.DigitronixProject.repositories.OrderRepository;
import com.backendserver.DigitronixProject.repositories.UserRepository;
import com.backendserver.DigitronixProject.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<OrderResponse> getAllOrder() throws Exception {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(OrderResponse::fromOrder).toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) throws Exception {
        return OrderResponse.fromOrder(orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find this order in application!")));
    }

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        User checkExistUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user in application"));
        Customer checkExistCustomer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this customer in application!"));
        Order newOrder = new Order();
        newOrder.setCreatedDate(orderDTO.getCreatedDate());
        newOrder.setDeadline(orderDTO.getDeadline());
        newOrder.setPaymentMethod(orderDTO.getPaymentMethod());
        newOrder.setDeliveryMethod(orderDTO.getDeliveryMethod());
        newOrder.setTotalPrice(orderDTO.getTotalPrice());
        newOrder.setStatus(orderDTO.getStatus());
        newOrder.setUser(checkExistUser);
        newOrder.setCustomer(checkExistCustomer);
        newOrder = orderRepository.save(newOrder);
        return OrderResponse.fromOrder(newOrder);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order checkExisting = orderRepository.getReferenceById(id);
        User checkExistUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this user in application"));
        Customer checkExistCustomer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find this customer in application!"));
        checkExisting.setCreatedDate(orderDTO.getCreatedDate());
        checkExisting.setDeadline(orderDTO.getDeadline());
        checkExisting.setPaymentMethod(orderDTO.getPaymentMethod());
        checkExisting.setDeliveryMethod(orderDTO.getDeliveryMethod());
        checkExisting.setTotalPrice(orderDTO.getTotalPrice());
        checkExisting.setStatus(orderDTO.getStatus());
        checkExisting.setUser(checkExistUser);
        checkExisting.setCustomer(checkExistCustomer);
        return OrderResponse.fromOrder(orderRepository.save(checkExisting));
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(()->new DataNotFoundException("Cannot find this order to delete!"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getOrderByCustomerId(Long id) {
        List<Order> orderList = orderRepository.findOrderByCustomerId(id);
        return orderList.stream().map(OrderResponse::fromOrder).toList();
    }

    @Override
    public List<OrderResponse> getOrderByUserId(Long id) throws Exception{
        List<Order> orderList = orderRepository.findOrderByUserId(id);
        return orderList.stream().map(OrderResponse::fromOrder).toList();
    }
}
