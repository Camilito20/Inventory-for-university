# Inventory Management System

## Description
This project is an Inventory Management System developed in Java following an incremental and iterative approach using sprints.

The system is being built across 7 sprints, where each sprint adds new functionality and improves the architecture.

---

## Current Stage — Sprint 2

### Sprint 1 Recap
In Sprint 1, the core logic of the system was implemented entirely in memory. This included:

- Product model creation
- Data validation rules
- CRUD operations (Create, Read, Update, Delete)
- Basic inventory management
- Console-based testing (Main class)

---

### Sprint 2 — Database Integration

In this sprint, the system was extended to support persistent storage using SQL.

### Implemented Features

- Database connection using JDBC
- Creation of `products` table
- Implementation of ProductRepository (DAO pattern)
- CRUD operations connected to the database
- Data persistence (data is saved and retrieved from SQL)
- Error handling for database operations

---
## Database

- SQL database (PostgreSQL / SQLite depending on configuration)
- Table: `products`

Example structure:

```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    price DOUBLE PRECISION NOT NULL,
    stock INT NOT NULL
);