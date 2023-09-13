# Product Delivery Platform  

- This project is a Product Delivery Platform that allows Vendors and Customers to perform various operations related to Ordering products. It provides RESTful APIs for managing vendor, customer, and products.
----
## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- JSON
- Maven
----
## Getting Started
- To get started with the Product Delivery Platform, follow these steps:

## Clone the repository:

- git clone
  ```
  <[repository_url](https://github.com/iamaniketg/Product-Delivery-Platform-.git)>
  ```
- Open the project in your preferred IDE.
- Configure the MySQL database connection in the application.properties file.
-----
* Build the project using Maven:

- mvn clean install
* Run the application:
- mvn spring-boot:run
----
* API Endpoints
1. CustomerController
- Post ```/customer/v1/api/signup```:
- Add a new Customer. The request body should contain the customer details in JSON format. Its signing up. 
- Post ```/customer/v1/api/signIn```:
- Doing signIn. Need to add `email` and `password` which is stored by using signUp. It sends token which is required.
- Put ```/customer/v1/api/update```:
- Update the customer, it needs `email` and `token` which is send to email during signIn. and update require things.
- Get ```/customer/v1/api/fetchAll```:
- Fetch all data.
- Delete ```/customer/v1/api/signOut```:
- End the session which is started by using the customer signIn, it needs `email` and `token` which is send to email during signIn.
- Post ```/customer/v1/api/schedule```:
- Order the products, here is also we need `email` and `token` which is send to email during signIn. and feel other details required.  
- Get ```/customer/v1/api/allOrders```:
- Fetch All orders.
- Delete ```/customer/v1/api/cancel```:
- Cancel the order, it needs `email` and `token` which is send to email during signIn.  

2. VendorController 
- POST ```/vendor/v1/api/signUp```:
- Add a new Vendor. The request body should contain the vendor details in JSON format. Its signing up.
- Post ```/vendor/v1/api/save```:
- Add product by using this Url.
- Post ```/vendor/v1/api/signIn```:
- Doing signIn. Need to add `email` and `password` which is stored by using signUp. It sends token which is required.
- Get ```/vendor/v1/api/fetch```:
- Fetch all products. 
- Delete ```/vendor/v1/api/signOut```:
- End the session which is started by using the Vendor signIn, it needs `email` and `token` which is send to email during signIn. 
- Delete ```/vendor/v1/api/remove/{id}```:
- Remove Product which is not required or which is out of stock. 
- Put ```/vendor/v1/api/update/{id}```:
- Update product details. 
---
## Data Models
1. Customer
- Represents a customer entity. 
```
- Id: Long
- Name: String
- Email: String
- Password: String
- address: String
- Phone: String
- RegistrationDate: LocalDate 
```
2.Vendor
- Represents an Vendor entity.
```
- Id: Integer
- Name: String
- Email: String
- Password: String
- address: String
- Phone: String
- RegistrationDate: LocalDate 
```
3. Product 
- Represents a Product entity.
```
- Id: Integer
- Name : String
- Description: String
- price: Double
- stockQuantity: int
- Vendor vendor: @Manytoone
```
4. Orders
- Represents the Order of a customer.
```
- Id: Long
- OrderDesc: String
- totalAmount: double
- orderStatus: Enum(Open, Close)
- OrderScheduleTime: LocalDateTime
- OrderCreationTime: LocalDateTime
- Customer customer: @Manytoone
- Vendor vendor: @Manytoone
- Product product: @Manytoone
```
5. AuthenticationToken
- Represents the AuthenticationToken Entity.
```
- tokenId: Long
- tokenValue : String
- tokenCreationDateTime: LocalDateTime
```
----
## Repository Interfaces

- AuthTokenRepository: Repository interface for AuthenticationToken entity.
- CustomerRepository: Repository interface for Customer entity.
- ProductRepository: Repository interface for Product entity.
- OrderRepository: Repository interface for Order entity.
- VendorRepository: Repository interface for Vendor entity.
----
## Services
1. CustomerService 
- It contans buisness logic code related to CRUD operations on customer.
2. VendorService
- It contans buisness logic code related to CRUD operations on vendor.
3. ProductService 
- It contans buisness logic code related to CRUD operations on product.
4. AuthTokenService
- It contans buisness logic code related to CRUD operations on AuthenticationToken.
5. OrderService
- It contans buisness logic code related to CRUD operations on Order.
       
----
## Usage
- Once the Product Delivery Platform is up and running, you can interact with it using various API endpoints. Here are some example requests using cURL:

- Add an Customer:
-  http://localhost:8080/customer/v1/api/signUp
```
  Content-Type: application/json" -d '{
  "id": 0,
  "name": "string",
  "email": "string",
  "password": "string",
  "address": "string",
  "phone": "string",
  "registrationDate": "2023-09-13"
}'
 ```
- Add a Vendor:
- http://localhost:8080/vendor/v1/api/signUp
```
 "Content-Type: application/json" -d '{
  "id": 0,
  "name": "string",
  "email": "string",
  "password": "string",
  "address": "string",
  "phone": "string",
  "registrationDate": "2023-09-13"
}' 
 ```
- Add Orders:
- http://localhost:8080/customer/v1/api/schedule 
```
EmailId
token
"Content-Type: application/json" -d {
  "totalAmount": 0,
  "orderStatus": "Open",
  "customer": {
    "id": 0,
    "name": "string",
    "email": "string",
    "password": "string",
    "address": "string",
    "phone": "string",
    "registrationDate": "2023-09-13"
  },
  "vendor": {
    "id": 0,
    "name": "string",
    "email": "string",
    "password": "string",
    "address": "string",
    "phone": "string",
    "registrationDate": "2023-09-13"
  },
  "product": {
    "id": 0,
    "name": "string",
    "description": "string",
    "price": 0,
    "stockQuantity": 0,
    "vendor": {
      "id": 0,
      "name": "string",
      "email": "string",
      "password": "string",
      "address": "string",
      "phone": "string",
      "registrationDate": "2023-09-13"
    }
  },
  "id": 0,
  "orderCreationTime": "2023-09-13T16:28:00.504Z",
  "orderScheduleTime": "2023-09-13T16:28:00.504Z",
  "orderDesc": "string"
}

``` 
- Add Product:
-  http://localhost:8080/vendor/v1/api/save
```
"Content-Type: application/json" -d {
  "id": 0,
  "name": "string",
  "description": "string",
  "price": 0,
  "stockQuantity": 0,
  "vendor": {
    "id": 0,
    "name": "string",
    "email": "string",
    "password": "string",
    "address": "string",
    "phone": "string",
    "registrationDate": "2023-09-13"
  }
}
``` 
-Feel free to explore and interact with other API endpoints as well.
----
## Contributing
- Contributions to the Product delivery platform project are welcome. If you find any issues or have suggestions for improvement, please open an issue or submit a

- Pull request on the GitHub repository.
----
## License
- This project is licensed under the MIT License.
----
## Acknowledgments
- OpenAPI Specification - The specification used for documenting the API endpoints.
- Spring Boot - The framework used for building the Music Management System.
- Hibernate - The ORM (Object-Relational Mapping) framework used for interacting with the database.
- MySQL - The relational database management system used for storing music and user data.
----
