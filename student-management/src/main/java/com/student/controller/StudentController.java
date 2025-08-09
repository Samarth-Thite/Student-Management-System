package com.student.controller;

import com.student.dto.ApiResponse;
import com.student.dto.StudentDTO;
import com.student.model.Student;
import com.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService service;

    // Get all students with pagination
    @GetMapping
    public ResponseEntity<ApiResponse<Page<StudentDTO>>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        try {
            Page<StudentDTO> students = service.getAllStudents(page, size, sortBy, sortDir);
            return ResponseEntity.ok(ApiResponse.success(students, "Students retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving students: " + e.getMessage(), 500));
        }
    }

    // Get all students (without pagination)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudentsList() {
        try {
            List<StudentDTO> students = service.getAllStudents();
            return ResponseEntity.ok(ApiResponse.success(students, "All students retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving students: " + e.getMessage(), 500));
        }
    }

    // Create new student
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO createdStudent = service.saveStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdStudent, "Student created successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error creating student: " + e.getMessage(), 500));
        }
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudent(@PathVariable Long id) {
        try {
            StudentDTO student = service.getStudent(id);
            return ResponseEntity.ok(ApiResponse.success(student, "Student retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving student: " + e.getMessage(), 500));
        }
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updatedStudent = service.updateStudent(id, studentDTO);
            return ResponseEntity.ok(ApiResponse.success(updatedStudent, "Student updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error updating student: " + e.getMessage(), 500));
        }
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        try {
            service.deleteStudent(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Student deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting student: " + e.getMessage(), 500));
        }
    }

    // Search students by name
    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> searchByName(@RequestParam String name) {
        try {
            List<StudentDTO> students = service.searchByName(name);
            return ResponseEntity.ok(ApiResponse.success(students, "Search completed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error searching students: " + e.getMessage(), 500));
        }
    }

    // Get students by course
    @GetMapping("/course/{course}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getByCourse(@PathVariable String course) {
        try {
            List<StudentDTO> students = service.getByCourse(course);
            return ResponseEntity.ok(ApiResponse.success(students, "Students by course retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving students by course: " + e.getMessage(), 500));
        }
    }

    // Get students by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getByStatus(@PathVariable Student.StudentStatus status) {
        try {
            List<StudentDTO> students = service.getByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(students, "Students by status retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving students by status: " + e.getMessage(), 500));
        }
    }

    // Search with multiple criteria
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<StudentDTO>>> searchByCriteria(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) Student.StudentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        try {
            Page<StudentDTO> students = service.searchByCriteria(name, course, status, page, size);
            return ResponseEntity.ok(ApiResponse.success(students, "Search completed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error searching students: " + e.getMessage(), 500));
        }
    }

    // Get statistics
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<StudentService.StudentStatistics>> getStatistics() {
        try {
            StudentService.StudentStatistics stats = service.getStatistics();
            return ResponseEntity.ok(ApiResponse.success(stats, "Statistics retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving statistics: " + e.getMessage(), 500));
        }
    }
}
