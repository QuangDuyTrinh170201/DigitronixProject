package com.backendserver.DigitronixProject.services.Delivery;

import com.backendserver.DigitronixProject.dtos.DeliveryDTO;
import com.backendserver.DigitronixProject.responses.DeliveryResponse;

import java.util.List;

public interface IDeliveryService {
    List<DeliveryResponse> getAllDelivery();

    DeliveryResponse getDeliveryById(Long id) throws Exception;

    DeliveryResponse createDelivery(DeliveryDTO deliveryDTO) throws Exception;

    DeliveryResponse updateDelivery(Long id, DeliveryDTO deliveryDTO) throws Exception;

    void deleteDelivery(Long id) throws Exception;
}
