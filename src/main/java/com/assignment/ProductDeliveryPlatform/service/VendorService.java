package com.assignment.ProductDeliveryPlatform.service;

import com.assignment.ProductDeliveryPlatform.model.AuthenticationToken;
import com.assignment.ProductDeliveryPlatform.model.Customer;
import com.assignment.ProductDeliveryPlatform.model.Vendor;
import com.assignment.ProductDeliveryPlatform.model.dto.SignInInput;
import com.assignment.ProductDeliveryPlatform.model.dto.SignUpOutput;
import com.assignment.ProductDeliveryPlatform.repository.AuthTokenRepository;
import com.assignment.ProductDeliveryPlatform.repository.VendorRepository;
import com.assignment.ProductDeliveryPlatform.service.emailUtilty.EmailHandler;
import com.assignment.ProductDeliveryPlatform.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
public class VendorService {
    @Autowired
    VendorRepository vendorRepository;


    @Autowired
    AuthTokenRepository authTokenRepository;

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }



    public SignUpOutput signUpVendor(Vendor vendor) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = vendor.getEmail();

        if(newEmail==null){
            signUpStatusMessage ="Invalid Email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        Vendor existingVendor = vendorRepository.findFirstByEmail(newEmail);

        if(existingVendor!=null){
            signUpStatusMessage="Email already registered";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(vendor.getPassword());
            vendor.setPassword(encryptedPassword);
            vendorRepository.save(vendor);

            return  new SignUpOutput(signUpStatus, "Vendor registered successfully");
        }
        catch (Exception e){
            signUpStatusMessage = "Internal error occurred during signup! ";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
    }

    public String signInVendor(SignInInput signInInput) {
        String signInStatusMessage = null;
        String signInEmail = signInInput.getEmail();

        if(signInEmail == null){
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;
        }
        //check if this customer email already exists ??
        Vendor existingVendor = vendorRepository.findFirstByEmail(signInEmail);

        if(existingVendor == null){
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;
        }
        //match passwords :
        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingVendor.getPassword().equals(encryptedPassword)){
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingVendor);
                authTokenRepository.save(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e){
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String signOutVendor(String email) {
        Vendor vendor = vendorRepository.findFirstByEmail(email);
        authTokenRepository.delete(authTokenRepository.findFirstByVendor(vendor));
        return "Vendor Signed out successfully";
    }
}
