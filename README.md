# 💳 Banking System

A console-based **Banking System** project developed in **Java**, demonstrating core **Object-Oriented Programming (OOP)** concepts and exception handling. The system simulates operations for **Savings** and **Current** accounts, and performs basic transactions like deposit, withdrawal, and transfer.

---

## 🚀 Features

- Object-Oriented Design using:
  - **Interfaces**
  - **Abstract Classes**
  - **Inheritance**
  - **Polymorphism**
- Account types:
  - **Savings Account**
  - **Current Account**
- Validations:
  - Insufficient balance
  - Invalid amount
  - Authentication failure
- Transaction logging and account summaries

---

## 🧠 OOP Concepts Applied

- **Interface** for defining basic banking operations.
- **Abstract Class** to implement shared behavior across accounts.
- **Inheritance** to extend functionality for Savings and Current accounts.
- **Polymorphism** to handle account operations through a common interface.

---

## 🗂 Project Structure

BankingSystem/<br>
├── Account.java # Abstract class for common account behavior<br>
├── AuthenticationException.java # Custom exception for login/authentication issues<br>
├── BankAccount.java # Interface for banking operations<br>
├── BankingSystemApp.java # Main application logic<br>
├── CurrentAccount.java # Current account implementation<br>
├── InsufficientBalanceException.java # Exception for low balance<br>
├── InvalidAmountException.java # Exception for invalid input errors<br>
├── SavingsAccount.java # Savings account implementation<br>
├── Transaction.java # Represents transactions<br>
├── data.java # Abstract classes and interfaces<br>
├── .gitignore # Ignore class files and build artifacts<br>
└── README.md # Project documentation<br>

