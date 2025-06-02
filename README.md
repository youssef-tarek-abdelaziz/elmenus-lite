# Elmenus Lite - Order Management Module

##### **Elmenus Lite** is a Spring Boot backend project module responsible for managing customer orders in a food ordering application.

> This module handles order creation, updating order status, retrieving order details, and listing customer orders. It works alongside the cart module to provide a seamless ordering experience.

---

## Table of Contents

- [Features](#features)
- [Time Estimation vs. Actual](#time-estimation-vs-actual)
- [API Endpoints](#api-endpoints)
    - [1. Get Order Details](#1-get-order-details)
    - [2. Get All Orders](#2-get-all-orders-by-customer-id)
    - [3. Create a New Order](#3-create-a-new-order)
    - [4. Update the Status of an Existing Order](#4-update-the-status-of-an-existing-order)

---

## Features

1. [x] Get Order Details
2. [x] Get All Orders
3. [x] Create a new order
4. [x] Update the status of an existing order

---


## Technology Stack

* Java 17
* Spring Boot
* Maven
* PostgreSQL
* Liquibase
* Swagger
---


## API Endpoints

> **Base URL:** `/api/v1/orders/`

### 1. Get Order Details

**GET** `{orderId}`

**Description:**  
Retrieves the details of a specific order by its ID.

**Example Request:**  
`GET /api/v1/orders/123`


**Response:**

```json
{
  "statusMessage": "Order retrieved successfully",
  "data": {
    "customerId": 1,
    "promotionCode": "SUMMER15",
    "items": [
      {
        "menuItemId": 101,
        "quantity": 2
      },
      {
        "menuItemId": 205,
        "quantity": 1
      }
    ],
    "address": {
      "street": "12 Tahrir St.",
      "city": "Cairo",
      "floor": "3",
      "apartment": "12A",
      "country": "Egypt",
      "state": "Cairo",
      "location": {
        "latitude": 30.0444,
        "longitude": 31.2357
      },
      "default": true
    }
  },
  "page": null
}
```

---

### 2. Get all orders by customer id

**GET** `customers/{customerId}/orders`

**Description:**  
Retrieves a list of all orders placed by a specific customer using their customer ID.

**Example Request:**  
`GET /api/v1/orders/customers/456/orders`

**Response:**

```json
{
  "statusMessage": "Order retrieved successfully",
  "data": {
    "customerId": 1,
    "promotionCode": "SUMMER15",
    "items": [
      {
        "menuItemId": 101,
        "quantity": 2
      }
    ],
    "address": {
      "street": "12 Tahrir St.",
      "city": "Cairo",
      "floor": "3",
      "apartment": "12A",
      "country": "Egypt",
      "state": "Cairo",
      "location": {
        "latitude": 30.0444,
        "longitude": 31.2357
      },
      "default": true
    }
  },
  "page": {
    "pageSize": 2,
    "pageNumber": 0,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

---

### 3. Create a new order

**POST** `/api/v1/orders/`

**Description:**  
Create a new order for the customer.

**Request Body:**

````json
{
  
}
````
**Response:**
```json
{
  
  
}
````

---

### 4. Update the status of an existing order

**PUT** `{orderId}/status/{status}`

**Description:**  
Updates the status of an existing order identified by `orderId`.

**Path Parameters:**
- `orderId` (string): The unique ID of the order to update.
- `status` (string): The new status to set for the order.  


  Allowed values:
    - `PENDING`
    - `PREPARING`
    - `READY_FOR_PICKUP`
    - `OUT_FOR_DELIVERY`
    - `DELIVERED`
    - `CANCELLED`

**Example Request:**  
`PUT /api/v1/orders/123/status/DELIVERED`

**Response:**
```json
{
}
```


---

