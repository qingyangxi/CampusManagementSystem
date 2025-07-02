# 🚀 Campus Management System

<p align="left"> 
  <svg xmlns="http://www.w3.org/2000/svg" width="112" height="28" role="img"><title>Spring Boot</title><rect width="112" height="28" rx="4" fill="#6DB33F"/><text x="56" y="18.5" fill="#fff" font-family="Verdana, Dejavu Sans, sans-serif" font-size="14" text-anchor="middle">Spring Boot</text></svg>
  <svg xmlns="http://www.w3.org/2000/svg" width="76" height="28" role="img"><title>Vue.js</title><rect width="76" height="28" rx="4" fill="#4FC08D"/><text x="38" y="18.5" fill="#fff" font-family="Verdana, Dejavu Sans, sans-serif" font-size="14" text-anchor="middle">Vue.js</text></svg>
</p>

Welcome! This is a full-stack campus management system designed to handle core functionalities for managing employees, departments, classes, and students.

The system is built with a decoupled front-end and back-end architecture. The front end manages user interaction and data display, while the back end provides RESTful APIs and handles business logic. User authentication is secured using JSON Web Tokens (JWT).

## ✨ Key Features

The system includes the following major modules:

* **🔑 Login & Registration**
    * [cite_start]**Employee Login**: Users can log in with a username and password to receive a JWT token from the server. [cite: 50, 63]
    * [cite_start]**Employee Registration**: Allows new employees to register, after which they are redirected to the login page. [cite: 171, 179]

* **🏢 Department Management**
    * [cite_start]View a list of all departments. [cite: 272]
    * [cite_start]Add, edit, and delete department information. [cite: 396, 524, 622]

* **👥 Employee Management**
    * [cite_start]Search and filter the employee list by name, gender, and hire date, with pagination support. [cite: 893, 894, 895]
    * [cite_start]Add, edit, and delete employee records (with support for bulk deletion). [cite: 990, 1135, 1295]

* **🏫 Class Management**
    * [cite_start]Search and filter the class list by name and date range, with pagination. [cite: 1581, 1582]
    * [cite_start]Add, edit, and delete class records. [cite: 1676, 1834, 2036]

* **🎓 Student Management**
    * [cite_start]Perform multi-criteria searches for students by name, student ID, education level, and class. [cite: 2364, 2365, 2366, 2367]
    * [cite_start]Add, edit, and delete student records (with support for bulk deletion). [cite: 2544, 2739, 2865]
    * [cite_start]Handle student disciplinary actions by adding demerit points and tracking infractions. [cite: 3146, 3256, 3257]

## 📸 Screenshots

Here are some screenshots of the application in action.

**Login Page**
![Login Page](./hook/login.jpg)

**Department Management**
![Department Management](./hook/dept.jpg)

**Employee Management**
![Employee Management](./hook/employee.jpg)

## 📂 Project Structure

```bash
.
├── backend/         -- Backend Spring Boot Project
├── database/        -- Database SQL Files
├── frontend/        -- Frontend Vue Project
├── hook/            -- Project Images Files
└── README.md        -- Project README File
```

## ⚙️ Quick Start

### 1. 💾 Database Setup

1.  Make sure you have MySQL installed and running.
2.  Create a new database.
3.  Import the SQL file from the `database/` directory into your newly created database to initialize the tables and data.

### 2. 🖥️ Backend Startup

1.  Open the `backend/` directory in your preferred Java IDE (like IntelliJ IDEA).
2.  Navigate to `src/main/resources/application.yml` to configure the project:
    * **Database**: Update the `datasource` section with your MySQL connection details (URL, username, and password).
    * **Alibaba Cloud OSS**: This project uses Alibaba Cloud OSS for file storage. You need to register an account and replace the placeholder values under the `aliyun.oss` section with your own `endpoint`, `accessKeyId`, `accessKeySecret`, and `bucketName`.
3.  Run the Spring Boot application.

### 3. 🌐 Frontend Startup

1.  Open the `frontend/` directory in your code editor (like VS Code).
2.  Run `npm install` in the terminal to install all the necessary dependencies.
3.  Run `npm run serve` to start the frontend development server.
4.  Open your browser and navigate to `http://localhost:8080` (or the address shown in your terminal).