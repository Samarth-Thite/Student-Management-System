# Student Management System

A comprehensive web-based application for managing student records built with Spring Boot and Hibernate.

## Features

- Add new student records
- View all student records
- Update existing student information
- Delete student records
- Search functionality
- Modern and responsive UI
- RESTful API endpoints

## Tech Stack

- Backend:
  - Spring Boot
  - Hibernate/JPA
  - MySQL Database
  - Maven

- Frontend:
  - HTML5
  - CSS3
  - JavaScript
  - Bootstrap

## Prerequisites

- Java 17 or higher
- Maven 3.8.x
- MySQL 8.x
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Getting Started

1. Clone the repository:
```bash
git clone [repository-url]
cd student-management-system
```

2. Build the project:
```bash
mvn clean install
```

3. Configure database:
- Open `src/main/resources/application.properties`
- Update database connection settings:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Run the application:
```bash
mvn spring-boot:run
```

5. Access the application:
- Open your web browser
- Navigate to: `http://localhost:8080`

## Project Structure

```
student-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.example.student/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── model/
│   │   │   │   └── repository/
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   ├── js/
│   │   │   │   └── index.html
│   │   │   └── application.properties
└── pom.xml
```

## API Endpoints

- GET `/api/students` - Get all students
- POST `/api/students` - Add new student
- PUT `/api/students/{id}` - Update student
- DELETE `/api/students/{id}` - Delete student

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request


![Student Management System Output](https://github.com/Samarth-Thite/Student-Management-System/screenshots/screenshot(12)png)
