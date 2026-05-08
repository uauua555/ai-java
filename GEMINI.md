# Diary Application Project Guidelines

This project is a Java Swing-based Diary Application with MySQL integration. It follows a clean architecture pattern to ensure high cohesion and maintainability.

## Architectural Layers

- **DTO (Data Transfer Object)**: `DiaryDTO.java` - A simple "bag" of data used to pass diary entries between layers.
- **DAO (Data Access Object)**: `DiaryDAO.java` - Handles all database-related logic (Connection, CRUD operations).
- **UI (User Interface)**: `DiaryUI.java` - Manages the Java Swing components and user interactions.

## Design Principles

- **Separation of Concerns**: Keep UI logic strictly separate from DB logic.
- **Aesthetics**: Use vibrant colors and icons/images to create a modern and pleasant user experience.
- **Simplicity**: Keep code concise and easy to understand.

## Prerequisites

- **MySQL Connector/J**: Ensure the MySQL JDBC driver is added to the project's libraries.
- **Database Schema**:
  ```sql
  CREATE DATABASE diary_db;
  USE diary_db;
  CREATE TABLE entries (
      id INT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(255),
      content TEXT,
      date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  );
  ```
