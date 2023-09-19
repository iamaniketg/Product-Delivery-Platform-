package com.assignment.ProductDeliveryPlatform.controller;

import com.assignment.ProductDeliveryPlatform.model.User;
import com.assignment.ProductDeliveryPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    UserService userService;
    @GetMapping("/user")
    public List<User> getUsers(){
        System.out.println("getting users");
        return userService.getUsers();
    }
}
