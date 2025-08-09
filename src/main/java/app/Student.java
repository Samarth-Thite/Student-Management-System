package app;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String course;
    private double fee;

    public Student() {}

    public Student(String name, String course, double fee) {
        this.name = name;
        this.course = course;
        this.fee = fee;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}
