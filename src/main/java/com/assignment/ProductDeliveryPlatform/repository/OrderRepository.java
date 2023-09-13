package com.assignment.ProductDeliveryPlatform.repository;

import com.assignment.ProductDeliveryPlatform.model.Customer;
import com.assignment.ProductDeliveryPlatform.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findFirstByCustomer(Customer customer);
}
