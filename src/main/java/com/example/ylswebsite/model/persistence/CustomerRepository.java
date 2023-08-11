package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
    List<Customer> findById(long id);
    Optional<Customer> findByUserName(String username);
}
