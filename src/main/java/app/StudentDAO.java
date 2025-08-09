package app;

import jakarta.persistence.*;
import java.util.List;


public class StudentDAO {

    private EntityManager em;

    public StudentDAO() {
        em = HibernateUtil.getEntityManagerFactory().createEntityManager();
    }

    public void addStudent(Student student) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(student);
        tx.commit();
        System.out.println("Student added successfully.");
    }

    public List<Student> getAllStudents() {
        return em.createQuery("from Student", Student.class).getResultList();
    }

    public void updateStudent(int id, String name, String course, double fee) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student student = em.find(Student.class, id);
        if (student != null) {
            student.setName(name);
            student.setCourse(course);
            student.setFee(fee);
            em.merge(student);
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found with ID: " + id);
        }
        tx.commit();
    }

    public void deleteStudent(int id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found with ID: " + id);
        }
        tx.commit();
    }

    public void close() {
        if (em != null) em.close();
    }
}
