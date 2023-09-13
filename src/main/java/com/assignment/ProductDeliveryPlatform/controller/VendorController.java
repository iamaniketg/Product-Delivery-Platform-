package com.assignment.ProductDeliveryPlatform.controller;

import com.assignment.ProductDeliveryPlatform.model.Product;
import com.assignment.ProductDeliveryPlatform.model.Vendor;
import com.assignment.ProductDeliveryPlatform.model.dto.SignInInput;
import com.assignment.ProductDeliveryPlatform.model.dto.SignUpOutput;
import com.assignment.ProductDeliveryPlatform.service.AuthService;
import com.assignment.ProductDeliveryPlatform.service.ProductService;
import com.assignment.ProductDeliveryPlatform.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/vendor/v1/api/")
@RestController
@Validated
public class VendorController {
    @Autowired
    ProductService productService;
    @Autowired
    VendorService vendorService;
    @Autowired
    AuthService authService;
//Add vendor
    @PostMapping("/signup")
    public SignUpOutput signUpVendor(@Valid @RequestBody Vendor vendor){
        return vendorService.signUpVendor(vendor);
    }
// Add product
    @PostMapping("/save")
    public Product addProduct(@Valid @RequestBody Product product){
        return productService.addProduct(product);
    }

// add vendor again for signing in
    @PostMapping("/signIn")
    public String signInVendor(@RequestBody @Valid SignInInput signInInput){
        return vendorService.signInVendor(signInInput);
    }

// fetch All products
    @GetMapping("/fetch")
    public List<Product> allProducts(){
        return productService.allProducts();
    }

// remove vendor
    @DeleteMapping("/signOut")
    public String signOutVendor(String email, String token){
        if(authService.authenticate(email,token)) {
            return vendorService.signOutVendor(email);
        }
        else {
            return "Sign out not allowed for non authenticated customer.";
        }
    }

// remove product
    @DeleteMapping("/remove/{id}")
    public void removeProduct(@PathVariable Long id){
        productService.removeProduct(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@Valid @PathVariable Long id, @RequestBody Product product){
        return productService.updateProduct(id,product);
    }

}
