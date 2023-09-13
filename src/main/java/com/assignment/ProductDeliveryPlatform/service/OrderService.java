package com.assignment.ProductDeliveryPlatform.service;

import com.assignment.ProductDeliveryPlatform.model.Customer;
import com.assignment.ProductDeliveryPlatform.model.Order;
import com.assignment.ProductDeliveryPlatform.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(Order order) {

        order.setOrderCreationTime(LocalDateTime.now());
        orderRepository.save(order);
    }
    public Order getOrderForCustomer(Customer customer) {

        return orderRepository.findFirstByCustomer(customer);
    }

    public void cancelOrder(Order order) {

        orderRepository.delete(order);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }
}
