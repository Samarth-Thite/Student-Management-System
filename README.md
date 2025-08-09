🎓 Student Management System

A full-featured web-based application designed to efficiently manage student records.
Built with Spring Boot and Hibernate (JPA), this system offers a responsive, modern UI and a RESTful API for easy integration.

✨ Features

➕ Add new student records with essential details.

📋 View all students in a clean, responsive table.

✏️ Update student information seamlessly.

❌ Delete student records with confirmation prompts.

🔍 Search functionality for quick filtering.

📱 Responsive Design – works on desktop, tablet, and mobile.

🌐 RESTful API endpoints for external integration.

💾 Persistent storage with MySQL database.


🖥️ Tech Stack

Backend

Spring Boot

Hibernate / JPA

MySQL Database

Maven

Frontend

HTML5

CSS3

JavaScript

Bootstrap

📦 Prerequisites

☕ Java 17 or higher

🛠 Maven 3.8.x

🗄 MySQL 8.x

💻 IDE (IntelliJ IDEA, Eclipse, or VS Code)


📂 Project Structure

pgsql

Copy

Edit

student-management-system/

├── src/


│   ├── main/

│   │   ├── java/

│   │   │   └── com.example.student/

│   │   │       ├── controller/

│   │   │       ├── service/

│   │   │       ├── model/

│   │   │       └── repository/

│   │   ├── resources/

│   │   │   ├── static/

│   │   │   │   ├── css/

│   │   │   │   ├── js/

│   │   │   │   └── index.html

│   │   │   └── application.properties

└── pom.xml

🔗 API Endpoints

Method	Endpoint	Description

GET	/api/students	Get all students

POST	/api/students	Add new student

PUT	/api/students/{id}	Update student

DELETE	/api/students/{id}	Delete student

📸 Screenshots

<img width="1366" height="768" alt="Screenshot (12)" src="https://github.com/user-attachments/assets/63c736df-9969-41b2-b3d0-9806025c133e" />

<img width="1366" height="768" alt="Screenshot (13)" src="https://github.com/user-attachments/assets/5811222a-958b-47c1-91a8-67793a0e544b" />
