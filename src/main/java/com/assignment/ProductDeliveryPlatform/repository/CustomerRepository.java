package com.assignment.ProductDeliveryPlatform.repository;

import com.assignment.ProductDeliveryPlatform.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findFirstByEmail(String newEmail);

}
