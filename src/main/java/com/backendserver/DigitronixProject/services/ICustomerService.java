package com.backendserver.DigitronixProject.services;

import com.backendserver.DigitronixProject.dtos.CustomerDTO;
import com.backendserver.DigitronixProject.responses.CustomerResponse;

import java.util.List;

public interface ICustomerService {
    List<CustomerResponse> getAllCustomer() throws Exception;

    CustomerResponse getCustomerWithId(Long id) throws Exception;

    CustomerResponse createCustomer(CustomerDTO customerDTO) throws Exception;

    CustomerResponse updateCustomer(Long id, CustomerDTO customerDTO) throws Exception;

    void deleteCustomer(Long id) throws Exception;
}
