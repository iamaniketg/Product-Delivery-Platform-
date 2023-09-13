package com.assignment.ProductDeliveryPlatform.service;

import com.assignment.ProductDeliveryPlatform.model.AuthenticationToken;
import com.assignment.ProductDeliveryPlatform.model.Customer;
import com.assignment.ProductDeliveryPlatform.model.Order;
import com.assignment.ProductDeliveryPlatform.model.dto.SignInInput;
import com.assignment.ProductDeliveryPlatform.model.dto.SignUpOutput;
import com.assignment.ProductDeliveryPlatform.repository.AuthTokenRepository;
import com.assignment.ProductDeliveryPlatform.repository.CustomerRepository;
import com.assignment.ProductDeliveryPlatform.repository.VendorRepository;
import com.assignment.ProductDeliveryPlatform.service.emailUtilty.EmailHandler;
import com.assignment.ProductDeliveryPlatform.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    AuthService authService;
    @Autowired
    OrderService orderService;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    AuthTokenRepository authTokenRepository;
    @Autowired
    CustomerRepository customerRepository;
    public SignUpOutput signUpCustomer(Customer customer) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = customer.getEmail();

        if(newEmail==null){
            signUpStatusMessage ="Invalid Email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        Customer existingCustomer = customerRepository.findFirstByEmail(newEmail);

        if(existingCustomer!=null){
            signUpStatusMessage="Email already registered";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(customer.getPassword());
            customer.setPassword(encryptedPassword);
            customerRepository.save(customer);

            return  new SignUpOutput(signUpStatus, "Customer registered successfully");
        }
        catch (Exception e){
            signUpStatusMessage = "Internal error occurred during signup! ";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
    }

    public String signInCustomer(SignInInput signInInput) {

            String signInStatusMessage = null;
            String signInEmail = signInInput.getEmail();

            if(signInEmail == null){
                signInStatusMessage = "Invalid email";
                return signInStatusMessage;
            }
            //check if this customer email already exists ??
            Customer existingCustomer = customerRepository.findFirstByEmail(signInEmail);

            if(existingCustomer == null){
                signInStatusMessage = "Email not registered!!!";
                return signInStatusMessage;
            }
            //match passwords :
            //hash the password: encrypt the password
            try {
                String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
                if(existingCustomer.getPassword().equals(encryptedPassword)){
                    //session should be created since password matched and user id is valid
                    AuthenticationToken authToken  = new AuthenticationToken(existingCustomer);
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

    public String signOutCustomer(String email) {
        Customer customer = customerRepository.findFirstByEmail(email);
        authTokenRepository.delete(authTokenRepository.findFirstByCustomer(customer));
        return "Customer Signed out successfully";
    }
    public boolean scheduleOrder(Order order) {
        //id of doctor
        Long vendorId = order.getVendor().getId();
        boolean isVendorValid = vendorRepository.existsById(vendorId);

        //id of patient
        Long customerId = order.getCustomer().getId();
        boolean isCustomerValid = customerRepository.existsById(customerId);

        if(isVendorValid && isCustomerValid)
        {
            orderService.saveOrder(order);
            return true;
        }
        else {
            return false;
        }
    }

    public void cancelOrder(String email) {
        //email -> Patient
        Customer customer = customerRepository.findFirstByEmail(email);

        Order order = orderService.getOrderForCustomer(customer);

        orderService.cancelOrder(order);
    }

    public String updateCustomer(String email, String token, Customer customer) {
        if (authService.authenticate(email, token)) {
            Customer customer1 = customerRepository.findFirstByEmail(customer.getEmail());
            if (customer1 != null) {
                if (!customer.getName().isEmpty()) {
                    customer1.setName(customer.getName());
                }
                if (!customer.getAddress().isEmpty()) {
                    customer1.setAddress(customer.getAddress());
                }
                if (!customer.getPhone().isEmpty()) {
                    customer1.setPhone(customer.getPhone());
                }
                customerRepository.save(customer1);
                return "Updated";
            } else {
                return "Customer not found";
            }
        } else {
            return "Error during update";
        }
    }
}
