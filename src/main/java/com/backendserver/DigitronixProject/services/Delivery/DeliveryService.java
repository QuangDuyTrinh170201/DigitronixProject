package com.backendserver.DigitronixProject.services.Delivery;

import com.backendserver.DigitronixProject.dtos.DeliveryDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Delivery;
import com.backendserver.DigitronixProject.models.Order;
import com.backendserver.DigitronixProject.repositories.DeliveryRepository;
import com.backendserver.DigitronixProject.repositories.OrderRepository;
import com.backendserver.DigitronixProject.responses.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService implements IDeliveryService{
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    @Override
    public List<DeliveryResponse> getAllDelivery() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream().map(DeliveryResponse::fromDelivery).toList();
    }

    @Override
    public DeliveryResponse getDeliveryById(Long id) throws Exception {
        Delivery delivery= deliveryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find this delivery order!"));
        return DeliveryResponse.fromDelivery(delivery);
    }

    @Override
    public DeliveryResponse createDelivery(DeliveryDTO deliveryDTO) throws Exception {
        Order order = orderRepository.findById(deliveryDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with this delivery"));
        order.setStatus("on_delivery");
        orderRepository.save(order);
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate());
        delivery.setOrder(order);
        delivery.setStatus(deliveryDTO.getStatus());
        delivery = deliveryRepository.save(delivery);
        return DeliveryResponse.fromDelivery(delivery);
    }

    @Override
    public DeliveryResponse updateDelivery(Long id, DeliveryDTO deliveryDTO) throws Exception {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find this delivery"));
        Order order = orderRepository.findById(deliveryDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with this delivery"));
        if(deliveryDTO.getStatus().equals(true)){
            order.setStatus("delivered");
            orderRepository.save(order);
        }
        else{
            delivery.setOrder(order);
        }
        delivery.setDeliveryDate(deliveryDTO.getDeliveryDate());
        delivery.setStatus(deliveryDTO.getStatus());
        return DeliveryResponse.fromDelivery(deliveryRepository.save(delivery));
    }

    @Override
    public void deleteDelivery(Long id) throws Exception {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find this delivery!"));
        Order existingOrder = orderRepository.findById(delivery.getOrder().getId())
                        .orElseThrow(()->new DataNotFoundException("Cannot find this order!"));
        if(delivery.getStatus().equals(false)){
            existingOrder.setStatus("produced");
            orderRepository.save(existingOrder);
        }
        deliveryRepository.delete(delivery);
    }
}
