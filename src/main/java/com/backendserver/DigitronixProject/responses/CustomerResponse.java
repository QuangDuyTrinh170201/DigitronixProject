package com.backendserver.DigitronixProject.responses;

import com.backendserver.DigitronixProject.models.Customer;
import com.backendserver.DigitronixProject.models.Material;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse extends BaseResponse{
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String facebook;

    @JsonProperty("payment_info")
    private String paymentInfo;

    public static CustomerResponse fromCustomer(Customer customer) {
        CustomerResponse customerResponse = null;
        customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .facebook(customer.getFacebook())
                .paymentInfo(customer.getPaymentInfo())
                .build();
        customerResponse.setCreatedAt(customer.getCreatedAt());
        customerResponse.setUpdatedAt(customer.getUpdatedAt());
        return customerResponse;
    }
}
