package com.student.dto;

import com.student.model.Student;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 100, message = "Course must be between 2 and 100 characters")
    private String course;
    
    @NotNull(message = "Fee is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fee must be greater than 0")
    private Double fee;
    
    @Email(message = "Email should be valid")
    private String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
    private String phone;
    
    private String address;
    
    private Student.StudentStatus status;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Convert Student entity to DTO
    public static StudentDTO fromEntity(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setCourse(student.getCourse());
        dto.setFee(student.getFee());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setAddress(student.getAddress());
        dto.setStatus(student.getStatus());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        return dto;
    }
    
    // Convert DTO to Student entity
    public Student toEntity() {
        Student student = new Student();
        student.setId(this.id);
        student.setName(this.name);
        student.setCourse(this.course);
        student.setFee(this.fee);
        student.setEmail(this.email);
        student.setPhone(this.phone);
        student.setAddress(this.address);
        student.setStatus(this.status != null ? this.status : Student.StudentStatus.ACTIVE);
        return student;
    }
}
