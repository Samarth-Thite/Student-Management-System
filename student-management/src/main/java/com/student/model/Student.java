package com.student.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 100, message = "Course must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String course;

    @NotNull(message = "Fee is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fee must be greater than 0")
    @Column(nullable = false)
    private Double fee;

    @Email(message = "Email should be valid")
    @Column(unique = true, length = 100)
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
    @Column(length = 15)
    private String phone;

    @Column(length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StudentStatus status = StudentStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED, SUSPENDED
    }
}
