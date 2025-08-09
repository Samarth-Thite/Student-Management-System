package com.student.repository;

import com.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Find by name containing (case-insensitive)
    List<Student> findByNameContainingIgnoreCase(String name);
    
    // Find by course
    List<Student> findByCourse(String course);
    
    // Find by status
    List<Student> findByStatus(Student.StudentStatus status);
    
    // Find by email
    Optional<Student> findByEmail(String email);
    
    // Find by phone
    Optional<Student> findByPhone(String phone);
    
    // Find students with fee greater than specified amount
    List<Student> findByFeeGreaterThan(Double fee);
    
    // Find students with fee between min and max
    List<Student> findByFeeBetween(Double minFee, Double maxFee);
    
    // Custom query to find students by multiple criteria
    @Query("SELECT s FROM Student s WHERE " +
           "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:course IS NULL OR s.course = :course) AND " +
           "(:status IS NULL OR s.status = :status)")
    Page<Student> findByCriteria(
            @Param("name") String name,
            @Param("course") String course,
            @Param("status") Student.StudentStatus status,
            Pageable pageable
    );
    
    // Count students by status
    long countByStatus(Student.StudentStatus status);
    
    // Get total fee collected
    @Query("SELECT SUM(s.fee) FROM Student s WHERE s.status = 'ACTIVE'")
    Double getTotalFeeCollected();
    
    // Get average fee
    @Query("SELECT AVG(s.fee) FROM Student s")
    Double getAverageFee();
}
