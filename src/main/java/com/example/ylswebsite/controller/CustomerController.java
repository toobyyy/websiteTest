package com.example.ylswebsite.controller;

import com.example.ylswebsite.dto.CustomerDTO;
import com.example.ylswebsite.model.Customer;
import com.example.ylswebsite.model.persistence.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "customers")
public class CustomerController {
    private final CustomerRepository customerService;

    public CustomerController(CustomerRepository customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getUsers(Model model) {
        Iterable<Customer> customers = customerService.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach(c -> customerDTOS.add(convertToDto(c)));
        model.addAttribute("customers", customerDTOS);
        return "customers";
    }

    protected CustomerDTO convertToDto(Customer entity) {
        CustomerDTO dto = new CustomerDTO(entity.getId(), entity.getUserName(), entity.getName(), entity.getEmail(), entity.getPhoneNumber());
        return dto;
    }

    protected Customer convertToEntity(CustomerDTO dto) {
        //29-06-1963
        Customer customer = new Customer(dto.getUsername(), dto.getName(), dto.getEmail(), dto.getPhoneNumber());
        if (!StringUtils.isEmpty(dto.getId())) {
            customer.setId(dto.getId());
        }
        return customer;
    }
}
