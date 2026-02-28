# Inventory

## Description

This project is an Inventory Management System developed in Java following an incremental development approach using sprints.

The current stage corresponds to Sprint 1, which focuses on implementing the core logic of the system and creating the Product model in memory.

During this sprint, the main objective is to establish a solid object-oriented foundation, including:

- Definition of the product entity
- Validation rules to ensure data integrity
- Encapsulation of attributes
- Proper implementation of equals() and hashCode()
- Custom toString() formatting
- Implementation of a product manager to handle core operations

The system currently operates entirely in memory and does not yet include persistence or a user interface.

---

## Architecture Overview (Sprint 1)

The system currently consists of two main components:

### Product (Entity Model)

Represents a product in the inventory system.

Attributes:
- name – Product name (cannot be null or blank)
- code – Unique identifier (cannot be negative)
- stock – Available quantity (cannot be negative)
- price – Product price (cannot be negative)

Key characteristics:
- Encapsulation with getters and setters
- Data validation enforced at object level
- equals() and hashCode() based on unique product code
- Custom formatted output using toString()

---

### ManagerProduct (Core Logic Layer)

Handles product management operations in memory.

Responsibilities:
- Add new products with validation
- Prevent duplicate product codes
- Search products by code
- Edit product information
- Remove products
- Sell products (with stock validation)
- Restock products
