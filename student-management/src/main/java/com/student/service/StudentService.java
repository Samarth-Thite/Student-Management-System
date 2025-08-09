package com.student.service;

import com.student.dto.StudentDTO;
import com.student.model.Student;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    // Get all students with pagination
    public Page<StudentDTO> getAllStudents(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = repo.findAll(pageable);
        
        return studentPage.map(StudentDTO::fromEntity);
    }

    // Get all students (without pagination)
    public List<StudentDTO> getAllStudents() {
        return repo.findAll().stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Save new student
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        try {
            System.out.println("Service: Attempting to save student: " + studentDTO.getName()); // Debug log
            
            // Check if email already exists
            if (studentDTO.getEmail() != null && repo.findByEmail(studentDTO.getEmail()).isPresent()) {
                System.out.println("Service: Email already exists: " + studentDTO.getEmail()); // Debug log
                throw new RuntimeException("Email already exists: " + studentDTO.getEmail());
            }
            
            // Check if phone already exists
            if (studentDTO.getPhone() != null && repo.findByPhone(studentDTO.getPhone()).isPresent()) {
                System.out.println("Service: Phone already exists: " + studentDTO.getPhone()); // Debug log
                throw new RuntimeException("Phone number already exists: " + studentDTO.getPhone());
            }
            
            Student student = studentDTO.toEntity();
            System.out.println("Service: Converting DTO to entity: " + student.getName()); // Debug log
            
            Student savedStudent = repo.save(student);
            System.out.println("Service: Student saved successfully with ID: " + savedStudent.getId()); // Debug log
            
            return StudentDTO.fromEntity(savedStudent);
        } catch (Exception e) {
            System.err.println("Service: Error saving student: " + e.getMessage()); // Debug log
            e.printStackTrace();
            throw e;
        }
    }

    // Delete student
    public void deleteStudent(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        repo.deleteById(id);
    }

    // Get student by ID
    public StudentDTO getStudent(Long id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return StudentDTO.fromEntity(student);
    }

    // Update student
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (studentDTO.getEmail() != null && !studentDTO.getEmail().equals(existing.getEmail())) {
            Optional<Student> existingWithEmail = repo.findByEmail(studentDTO.getEmail());
            if (existingWithEmail.isPresent()) {
                throw new RuntimeException("Email already exists: " + studentDTO.getEmail());
            }
        }
        
        // Check if phone is being changed and if it already exists
        if (studentDTO.getPhone() != null && !studentDTO.getPhone().equals(existing.getPhone())) {
            Optional<Student> existingWithPhone = repo.findByPhone(studentDTO.getPhone());
            if (existingWithPhone.isPresent()) {
                throw new RuntimeException("Phone number already exists: " + studentDTO.getPhone());
            }
        }
        
        // Update fields
        if (studentDTO.getName() != null) existing.setName(studentDTO.getName());
        if (studentDTO.getCourse() != null) existing.setCourse(studentDTO.getCourse());
        if (studentDTO.getFee() != null) existing.setFee(studentDTO.getFee());
        if (studentDTO.getEmail() != null) existing.setEmail(studentDTO.getEmail());
        if (studentDTO.getPhone() != null) existing.setPhone(studentDTO.getPhone());
        if (studentDTO.getAddress() != null) existing.setAddress(studentDTO.getAddress());
        if (studentDTO.getStatus() != null) existing.setStatus(studentDTO.getStatus());
        
        Student updatedStudent = repo.save(existing);
        return StudentDTO.fromEntity(updatedStudent);
    }

    // Search students by name
    public List<StudentDTO> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name).stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Get students by course
    public List<StudentDTO> getByCourse(String course) {
        return repo.findByCourse(course).stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Get students by status
    public List<StudentDTO> getByStatus(Student.StudentStatus status) {
        return repo.findByStatus(status).stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Search with multiple criteria
    public Page<StudentDTO> searchByCriteria(String name, String course, Student.StudentStatus status, 
                                           int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = repo.findByCriteria(name, course, status, pageable);
        return studentPage.map(StudentDTO::fromEntity);
    }

    // Get statistics
    public StudentStatistics getStatistics() {
        long totalStudents = repo.count();
        long activeStudents = repo.countByStatus(Student.StudentStatus.ACTIVE);
        Double totalFee = repo.getTotalFeeCollected();
        Double averageFee = repo.getAverageFee();
        
        return new StudentStatistics(totalStudents, activeStudents, totalFee, averageFee);
    }

    // Statistics class
    public static class StudentStatistics {
        private long totalStudents;
        private long activeStudents;
        private Double totalFee;
        private Double averageFee;

        public StudentStatistics(long totalStudents, long activeStudents, Double totalFee, Double averageFee) {
            this.totalStudents = totalStudents;
            this.activeStudents = activeStudents;
            this.totalFee = totalFee;
            this.averageFee = averageFee;
        }

        // Getters
        public long getTotalStudents() { return totalStudents; }
        public long getActiveStudents() { return activeStudents; }
        public Double getTotalFee() { return totalFee; }
        public Double getAverageFee() { return averageFee; }
    }
}
