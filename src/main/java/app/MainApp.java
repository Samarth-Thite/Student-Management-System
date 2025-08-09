package app;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter course: ");
                    String course = sc.nextLine();
                    System.out.print("Enter fee: ");
                    double fee = sc.nextDouble();
                    dao.addStudent(new Student(name, course, fee));
                    break;

                case 2:
                    List<Student> students = dao.getAllStudents();
                    System.out.println("ID\tName\tCourse\tFee");
                    for (Student s : students) {
                        System.out.println(s.getId() + "\t" + s.getName() + "\t" + s.getCourse() + "\t" + s.getFee());
                    }
                    break;

                case 3:
                    System.out.print("Enter ID to update: ");
                    int idu = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new course: ");
                    String newCourse = sc.nextLine();
                    System.out.print("Enter new fee: ");
                    double newFee = sc.nextDouble();
                    dao.updateStudent(idu, newName, newCourse, newFee);
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    int idd = sc.nextInt();
                    dao.deleteStudent(idd);
                    break;

                case 5:
                    dao.close();
                    HibernateUtil.shutDown();
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        sc.close();
    }
}
