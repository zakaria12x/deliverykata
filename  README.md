# Customer Delivery API Guide

## **Project Overview**
This project provides an API for managing deliveries, customers, and delivery modes. It allows you to:
- Create delivery modes
- Create customers
- Create deliveries using a customer ID and delivery mode ID
- Retrieve a delivery by its ID and customer ID
- Get all deliveries for a specific customer

---

## **Prerequisites**
Make sure you have the following installed before running the application:
- **Java 21**
- **Spring Boot 3.2.x**
- **Maven**
- **Docker**

---

## **Installation & Setup**

### **1. Clone the Repository**
```sh
 git clone https://github.com/zakaria12x/deliverykata.git
 cd deliverykata
```


### **2. Build the Application**
```sh
mvn clean install -DskipTests
```

Run Docker Compose this will start the project and db:
```sh
docker-compose up --build -d
```

---

## **API Endpoints**

### **1. Create Delivery Modes DRIVE, DELIVERY, DELIVERY_TODAY and DELIVERY_ASAP**
```http
POST http://127.0.0.1:8080/deliveries
```
#### **Request Body**
```json
{
  "name": "DELIVERY_ASAP",
  "active": true
}
```

### **2. Create a Customer**
```http
POST http://127.0.0.1:8080/customer
```
#### **Request Body**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```


### **3. Create a Delivery**
```http
POST http://127.0.0.1:8080/deliveries
```
#### **Request Body**
```json
{
  "customerId": "customer_id_from_previous_call",
  "modeId": "delivery_id_from_previous_call",
  "deliveryTimeSlot": "2025-02-10T14:00:00"
}
```


### **4. Get a Delivery by ID and Customer ID**
```http
GET http://127.0.0.1:8080/deliveries/{deliveryId}/customer/{customerId}
```


### **5. Get All Deliveries for a Customer**
```http
GET http://127.0.0.1:8080/deliveries/all/customer/{customerId}
```


---

## **Running Tests**
To run unit and e2e tests:
```sh
mvn test
```

---

## **Troubleshooting**
- **MongoDB Connection Issues**: Ensure MongoDB is running and accessible.
- **Port Conflict**: If port 8080 is in use, change it in `application.yml` and in the docker-compose.yml.
- **Docker Issues**: Ensure Docker is installed and running.

---
