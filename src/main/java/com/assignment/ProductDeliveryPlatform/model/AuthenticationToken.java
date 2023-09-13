package com.assignment.ProductDeliveryPlatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String tokenValue;
    private LocalDateTime tokenCreationDateTime;

    //mapping
    @OneToOne
    @JoinColumn(name = "fk_customer_Id")
    Customer customer;

    @OneToOne
    @JoinColumn(name = "fk_vendor_ID")
    Vendor vendor;


    //create a parameterized constructor which takes customer as an argument
    public AuthenticationToken(Customer customer){
        this.customer = customer;
        this.tokenValue = UUID.randomUUID().toString();
        this.tokenCreationDateTime = LocalDateTime.now();
    }

    public AuthenticationToken(Vendor vendor) {
        this.vendor = vendor;
        this.tokenValue = UUID.randomUUID().toString();
        this.tokenCreationDateTime = LocalDateTime.now();
    }
}