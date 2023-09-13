package com.assignment.ProductDeliveryPlatform.repository;

import com.assignment.ProductDeliveryPlatform.model.AuthenticationToken;
import com.assignment.ProductDeliveryPlatform.model.Customer;
import com.assignment.ProductDeliveryPlatform.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByCustomer(Customer customer);

    AuthenticationToken findFirstByVendor(Vendor vendor);

}