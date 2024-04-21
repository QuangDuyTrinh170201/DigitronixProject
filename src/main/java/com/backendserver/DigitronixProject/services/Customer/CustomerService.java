package com.backendserver.DigitronixProject.services.Customer;

import com.backendserver.DigitronixProject.dtos.CustomerDTO;
import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.Customer;
import com.backendserver.DigitronixProject.repositories.CustomerRepository;
import com.backendserver.DigitronixProject.responses.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(CustomerResponse::fromCustomer).toList();
    }

    @Override
    public CustomerResponse getCustomerWithId(Long id) throws Exception{
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find this customer!"));
        return CustomerResponse.fromCustomer(customer);
    }

    @Override
    public CustomerResponse createCustomer(CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findCustomerByName(customerDTO.getName());
        if(existingCustomer != null){
            throw new DataIntegrityViolationException("Already has a customer in this application, please check again!");
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(customerDTO.getName());
        newCustomer.setAddress(customerDTO.getAddress());
        newCustomer.setPhone(customerDTO.getPhone());
        newCustomer.setFacebook(customerDTO.getFacebook());
        newCustomer.setPaymentInfo(customerDTO.getPaymentInfo());
        newCustomer = customerRepository.save(newCustomer);
        return CustomerResponse.fromCustomer(newCustomer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerDTO customerDTO) throws Exception {
        Customer findCustomer = customerRepository.getReferenceById(id);
        findCustomer.setName(customerDTO.getName());
        findCustomer.setPhone(customerDTO.getPhone());
        findCustomer.setAddress(customerDTO.getAddress());
        findCustomer.setPaymentInfo(customerDTO.getPaymentInfo());
        findCustomer.setFacebook(customerDTO.getFacebook());
        return CustomerResponse.fromCustomer(customerRepository.save(findCustomer));
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find this customer!"));
        customerRepository.delete(customer);
    }
}
