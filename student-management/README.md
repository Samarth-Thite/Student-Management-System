# 🎓 Student Management System

A modern, responsive Student Management System built with **Spring Boot REST API** and a beautiful **HTML/CSS/JavaScript** frontend. This application demonstrates proper **MVC architecture** and integration between frontend and backend.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🚀 Live Demo

- **Frontend**: http://localhost:8081
- **API Documentation**: http://localhost:8081/api/students

## ✨ Features

- **🎯 RESTful API**: Complete CRUD operations for student management
- **📱 Responsive UI**: Modern, mobile-friendly interface built with Bootstrap 5
- **🔍 Real-time Search**: Instant search functionality with debouncing
- **🎛️ Advanced Filtering**: Filter by course, status, and other criteria
- **📄 Pagination**: Efficient data loading with customizable page sizes
- **📊 Statistics Dashboard**: Real-time system statistics
- **✅ Form Validation**: Client-side and server-side validation
- **🛡️ Error Handling**: Comprehensive error handling and user feedback
- **🗄️ Database Integration**: PostgreSQL with JPA/Hibernate
- **🔒 Security**: Basic security configuration with CORS support

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.5.4** - Main framework
- **Spring Data JPA** - Database operations
- **Spring Security** - Security configuration
- **PostgreSQL** - Database
- **Hibernate** - ORM
- **Lombok** - Code reduction
- **Validation** - Bean validation

### Frontend
- **HTML5** - Semantic markup
- **CSS3** - Modern styling with gradients and animations
- **JavaScript (ES6+)** - Modern JavaScript with async/await
- **Bootstrap 5** - Responsive UI framework
- **Font Awesome** - Icons

## 📋 Prerequisites

- Java 17 or higher
- PostgreSQL 12 or higher
- Gradle 7.0 or higher
- Modern web browser

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/student-management-system.git
cd student-management-system
```

### 2. Database Setup

```bash
# Create database and user
sudo -u postgres psql -c "CREATE DATABASE airodb; CREATE USER airouser WITH PASSWORD 'airopass'; GRANT ALL PRIVILEGES ON DATABASE airodb TO airouser;"

# Run database setup script
psql -U airouser -d airodb -f database-setup.sql
```

### 3. Configure Application

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=airouser
spring.datasource.password=airopass
```

### 4. Build and Run

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

### 5. Access the Application

- **Frontend**: http://localhost:8081
- **API**: http://localhost:8081/api/students

## 📚 API Documentation

### Base URL
```
http://localhost:8081/api/students
```

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Get all students (with pagination) |
| GET | `/api/students/all` | Get all students (without pagination) |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create new student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/api/students/search` | Search students with criteria |
| GET | `/api/students/statistics` | Get system statistics |

### Example Request

```bash
# Create a new student
curl -X POST http://localhost:8081/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "course": "Computer Science",
    "fee": 1500.00,
    "email": "john.doe@email.com",
    "phone": "+1234567890",
    "status": "ACTIVE"
  }'
```

## 🏗️ Project Structure

```
student-management/
├── src/
│   ├── main/
│   │   ├── java/com/student/
│   │   │   ├── controller/
│   │   │   │   └── StudentController.java
│   │   │   ├── service/
│   │   │   │   └── StudentService.java
│   │   │   ├── repository/
│   │   │   │   └── StudentRepository.java
│   │   │   ├── model/
│   │   │   │   └── Student.java
│   │   │   ├── dto/
│   │   │   │   ├── StudentDTO.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   └── StudentManagementApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── app.js
│   │       └── application.properties
│   └── test/
├── build.gradle
├── database-setup.sql
└── README.md
```

## 🎨 Screenshots

### Main Dashboard
![Dashboard](screenshots/dashboard.png)

### Add Student Form
![Add Student](screenshots/add-student.png)

### Statistics View
![Statistics](screenshots/statistics.png)

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

### Manual Testing
1. Start the application
2. Open http://localhost:8081 in your browser
3. Test all CRUD operations
4. Test search and filtering
5. Test responsive design on different screen sizes

## 🚀 Deployment

### Production Build
```bash
./gradlew build -x test
```

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter any issues:

1. Check the application logs
2. Verify database connectivity
3. Ensure all dependencies are installed
4. Check browser console for JavaScript errors

## 🔮 Future Enhancements

- [ ] User authentication and authorization
- [ ] File upload for student photos
- [ ] Email notifications
- [ ] Advanced reporting
- [ ] Mobile app
- [ ] API rate limiting
- [ ] Caching layer
- [ ] Unit and integration tests
- [ ] CI/CD pipeline

## 📊 Project Statistics

![GitHub stars](https://img.shields.io/github/stars/yourusername/student-management-system)
![GitHub forks](https://img.shields.io/github/forks/yourusername/student-management-system)
![GitHub issues](https://img.shields.io/github/issues/yourusername/student-management-system)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/student-management-system)

---

**Made with ❤️ by [Your Name]**

**Happy Coding! 🎉**
