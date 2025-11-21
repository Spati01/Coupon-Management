## Project Name :-
**Coupon-Management**

# Project Overview :-
This project implements a simple E-commerce Coupon Management System using Spring Boot.It provides REST APIs to:
1. Create and store coupons with eligibility rules
2. Evaluate & return the best coupon for a given user + cart input
3. All coupons are stored in an in-memory store, no database required.


# Tech Stack :- 
1. Java 17
2. Spring Boot 3.x
3. Maven
4. In-memory ConcurrentHashMap storage
5. IntelliJ


# ✨ Features
✔ Create Coupons with eligibility
✔ Rule-based coupon filtering
✔ Best Coupon Selection using:
Highest Discount → Earliest Expiry → Sorted Coupon Code
✔ Validity Date Check
✔ Usage limit per user check
✔ User & Cart-based validation
✔ Proper API Validation + Error Responses
✔ Sample Data Auto-Loaded at Startup



# How to Run :- Install before running: 
**1. Prerequisites :**
|-----------------------------------------------------------------------------------------|   
| Tool / Software                 | Required Version | Purpose                            |
| ------------------------------- | ---------------- | ---------------------------------- |
| **Java**                        | 21               | Run the Spring Boot application    |
| **Maven**                       | 3.9+             | Build and manage dependencies      |
| **Git**                         | Latest           | Clone repository & version control |
| **Postman**                     | Latest           | Test REST APIs                     |
| **IntelliJ IDEA**               | Latest           | Development environment            |
| **JSON & Validation**           | Latest           | Jackson, Hibernate Validator       |
|---------------------------------|------------------|------------------------------------|

**2. Setup steps :**
1. git clone <your-repo>
2. cd coupon-management
3. mvn clean package
4. mvn spring-boot:run
The service starts on http://localhost:8080

## API Endpoints
- POST `/api/coupons/create` — create coupon (JSON)
- GET `/api/coupons/all-coupons` — list coupons (JSON)
- POST `/api/coupons/find-best` — get best coupon for user+cart

**Commands to Start the Service :**
 mvn spring-boot:run


## AI Usage Note
This project used AI assistance only for:
- Writing the initial structure of the README.md file
- Formatting sample JSON input/output data used for API testing

### Prompts Used
1. "Create a structure for a README.md for a Simple Coupon Management System project using Spring Boot."
2. "Provide sample input JSON data for User, Cart, and Coupon creation for testing the Coupon APIs."

**No AI was used for writing the core business logic or implementing the coupon system functionality.**


## Duplicate policy
Creating a coupon with an existing `code` returns HTTP 409 Conflict. 



#### API Test Requests (Use in Postman) : 
1️⃣ Create Coupon API : POST `/api/coupons/create` — create coupon (JSON) : 

**Request Body (Example) :** 
   {
  "code": "WELCOME50",
  "description": "Flat ₹50 off for new users",
  "discountType": "FLAT",
  "discountValue": 50,
  "startDate": "2025-01-01T00:00:00",
  "endDate": "2025-12-31T23:59:59",
  "eligibility": {
    "firstOrderOnly": true
  }
}

**Success Response**
{
    "message": "Coupon created successfully!",
    "couponCode": "WELCOME50"
}

**Duplicate Response**
{
    "error": "Duplicate Coupon",
    "message": "Coupon code already exists: WELCOME50",
    "timestamp": "2025-11-21T12:45:35.3637992",
    "status": 409
}

2️⃣Get All Coupons API : GET `/api/coupons/all-coupons` — list coupons (JSON)

**Expected Response Sample**
   [
    {
        "code": "WELCOME50",
        "description": "Flat ₹50 off for new users",
        "discountType": "FLAT",
        "discountValue": 50,
        "maxDiscountAmount": null,
        "startDate": "2025-01-01T00:00:00",
        "endDate": "2025-12-31T23:59:59",
        "usageLimitPerUser": null,
        "eligibility": {
            "allowedUserTiers": null,
            "minLifetimeSpend": null,
            "minOrdersPlaced": null,
            "firstOrderOnly": true,
            "allowedCountries": null,
            "minCartValue": null,
            "applicableCategories": null,
            "excludedCategories": null,
            "minItemsCount": null
        },
        "usageByUser": {}
    },
    {
        "code": "FLAT100",
        "description": "Flat Rs 100 off for Gold users",
        "discountType": "FLAT",
        "discountValue": 100,
        "maxDiscountAmount": null,
        "startDate": "2025-11-20T12:45:08.4671645",
        "endDate": "2026-05-21T12:45:08.4671645",
        "usageLimitPerUser": null,
        "eligibility": {
            "allowedUserTiers": [
                "GOLD"
            ],
            "minLifetimeSpend": null,
            "minOrdersPlaced": null,
            "firstOrderOnly": null,
            "allowedCountries": null,
            "minCartValue": null,
            "applicableCategories": null,
            "excludedCategories": null,
            "minItemsCount": null
        },
        "usageByUser": {}
    },
    {
        "code": "MEGA2025",
        "description": "Mega Year Sale - Flat ₹150 off everyone!",
        "discountType": "FLAT",
        "discountValue": 150,
        "maxDiscountAmount": null,
        "startDate": "2025-11-20T12:45:08.4681677",
        "endDate": "2026-11-21T12:45:08.4681677",
        "usageLimitPerUser": null,
        "eligibility": {
            "allowedUserTiers": null,
            "minLifetimeSpend": null,
            "minOrdersPlaced": null,
            "firstOrderOnly": null,
            "allowedCountries": null,
            "minCartValue": 500,
            "applicableCategories": null,
            "excludedCategories": null,
            "minItemsCount": null
        },
        "usageByUser": {}
    },
    {
        "code": "FIRST200",
        "description": "Get ₹200 off on your first order",
        "discountType": "FLAT",
        "discountValue": 200,
        "maxDiscountAmount": null,
        "startDate": "2025-11-20T12:45:08.4681677",
        "endDate": "2026-11-21T12:45:08.4681677",
        "usageLimitPerUser": null,
        "eligibility": {
            "allowedUserTiers": null,
            "minLifetimeSpend": null,
            "minOrdersPlaced": null,
            "firstOrderOnly": true,
            "allowedCountries": null,
            "minCartValue": null,
            "applicableCategories": null,
            "excludedCategories": null,
            "minItemsCount": null
        },
        "usageByUser": {}
    }
]

3️⃣ Find Best Coupon API : POST `/api/coupons/find-best` — get best coupon for user+cart

# A) New GOLD User → High Cart Value (MEGA2025 gives higher discount than FLAT100 → Best)
**Request Body:**
  {
  "user": {
    "userId": "U101",
    "userTier": "GOLD",
    "country": "IN",
    "lifetimeSpend": 5000,
    "ordersPlaced": 2
  },
  "cart": {
    "items": [
      {
        "productId": "P123",
        "category": "electronics",
        "unitPrice": 1000,
        "quantity": 2
      }
    ]
  }
}

**Response**

{
    "couponCode": "MEGA2025",
    "couponDescription": "Mega Year Sale - Flat ₹150 off everyone!",
    "discountAmount": 150.00,
    "cartValue": 2000,
    "finalCartValue": 1850.00
}



# B) New User → First Order (FIRST200 applies → Best)

**Request Body:**

{
  "user": {
    "userId": "U001",
    "userTier": "NEW",
    "country": "IN",
    "lifetimeSpend": 0,
    "ordersPlaced": 0
  },
  "cart": {
    "items": [
      {
        "productId": "A1",
        "category": "fashion",
        "unitPrice": 400,
        "quantity": 1
      }
    ]
  }
}


**Response:**

{
    "couponCode": "FIRST200",
    "couponDescription": "Get ₹200 off on your first order",
    "discountAmount": 200.00,
    "cartValue": 400,
    "finalCartValue": 200.00
}


# C) Regular User → Small Cart (Cart value < ₹500 → No coupon applies)

**Request Body:**

{
  "user": {
    "userId": "REG01",
    "userTier": "REGULAR",
    "country": "IN",
    "lifetimeSpend": 2000,
    "ordersPlaced": 4
  },
  "cart": {
    "items": [
      {
        "productId": "X12",
        "category": "grocery",
        "unitPrice": 200,
        "quantity": 1
      }
    ]
  }
}


**Response:**

{
    "message": "No coupons available for this user/cart"
}

## Demo Login (Hard-Coded Seed User)
**To allow reviewers to test the application without registration, the following demo user exists in the seeded data:**



# Approach:
**1. Project Structure & Design**
Followed a clean layered architecture using Spring Boot:
controller → Handles REST API requests.
service → Contains business logic for coupon evaluation and creation.
repository → Stores coupons and demo user in-memory (no database required).
model → Contains domain models (Coupon, Eligibility, User, Cart, CartItem).
dto → Defines request/response objects for APIs.
config → Loads test coupons and hard-coded demo user at application startup.
Code structured to be modular, readable, and maintainable.

**2. Coupon Creation:**
Implemented POST /api/coupons/create API.
Supports all eligibility rules:
User tiers, first order only, min lifetime spend, min orders.
Cart-based rules: min cart value, applicable/excluded categories, min items.
Ensures unique coupon codes; duplicates are rejected with proper error response.

**3. Best Coupon Evaluation:**
Implemented POST /api/coupons/find-best API.

**4. Steps followed:**
Filter coupons by validity dates and per-user usage limits.
Validate all eligibility rules against given user + cart input.
Calculate discount:
FLAT → Fixed amount.
PERCENT → Percentage of cart value, capped by maxDiscountAmount.
Select best coupon using deterministic rules:
Highest discount → Earliest expiry → Lexicographically smallest code.
Returns either the best coupon with discount or null if no coupon applies.

**5. Demo User Requirement:**
Created a hard-coded demo user in memory:
Email: hire-me@anshumat.org
Password: HireMe@2025!
Ensures reviewers can test the application without registration.
Seeded in DataLoader alongside test coupons.

**6. In-Memory Storage:**
Used ConcurrentHashMap for coupons and users to simplify storage.
Avoided database setup since persistence was optional.
Ensures quick testing and reproducibility.
Error Handling
Invalid inputs are handled with clear messages.
Duplicate coupon creation returns HTTP 409 Conflict.



