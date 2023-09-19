package com.assignment.ProductDeliveryPlatform.service;

import com.assignment.ProductDeliveryPlatform.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private List<User> store = new ArrayList<>();

    public UserService(){
        store.add(new User(UUID.randomUUID().toString(),"Aniket","aniketofficial061299@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"Abhishek","abhishek@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"sachin","sachin@gmail.com"));
    }

    public List<User> getUsers() {
        return this.store;
    }
}
