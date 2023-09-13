package com.assignment.ProductDeliveryPlatform.controller;

import com.assignment.ProductDeliveryPlatform.model.Customer;
import com.assignment.ProductDeliveryPlatform.model.Order;
import com.assignment.ProductDeliveryPlatform.model.Vendor;
import com.assignment.ProductDeliveryPlatform.model.dto.SignInInput;
import com.assignment.ProductDeliveryPlatform.model.dto.SignUpOutput;
import com.assignment.ProductDeliveryPlatform.service.AuthService;
import com.assignment.ProductDeliveryPlatform.service.CustomerService;
import com.assignment.ProductDeliveryPlatform.service.OrderService;
import com.assignment.ProductDeliveryPlatform.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/customer/v1/api/")
@RestController
@Validated
public class CustomerController {
    @Autowired
    OrderService orderService;
    @Autowired
    AuthService authService;
    @Autowired
    CustomerService customerService;
    @Autowired
    VendorService vendorService;
    @PostMapping("/signup")
    public SignUpOutput signUpCustomer(@Valid @RequestBody Customer customer){
        return customerService.signUpCustomer(customer);
    }

    @PostMapping("/signIn")
    public String signInCustomer( @RequestBody @Valid SignInInput signInInput){
        return customerService.signInCustomer(signInInput);
    }
    @PutMapping("/update")
    public String updateCustomer(@Valid @RequestParam String email, @RequestParam String token, @RequestBody Customer customer) {
        return customerService.updateCustomer(email, token, customer);
    }

    @GetMapping("/fetchAll")
    List<Vendor> getAllVendor(){
        return vendorService.getAllVendors();
    }

    @DeleteMapping("/signOut")
    public String signOutCustomer(@RequestParam String email, @RequestParam String token){
        if(authService.authenticate(email,token)) {
            return customerService.signOutCustomer(email);
        }
        else {
            return "Sign out not allowed for non authenticated customer.";
        }
    }
    @PostMapping("/schedule")
    public String  scheduleOrder(@Valid @RequestBody Order order,String email, String token){
        if(authService.authenticate(email,token)) {
            boolean status = customerService.scheduleOrder(order);
            return status ? "oder scheduled":"error occurred during scheduling order";
        }
        else{
            return "Scheduling failed because invalid authentication";
        }
    }
    @DeleteMapping("/cancel")
    public String  cancelOrder(@RequestParam String email, @RequestParam String token){

        if(authService.authenticate(email,token)) {
            customerService.cancelOrder(email);
            return "cancelled Order successfully";
        }
        else{
            return "Scheduling failed because invalid authentication";
        }
    }

    @GetMapping("/allOrders")
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

}
