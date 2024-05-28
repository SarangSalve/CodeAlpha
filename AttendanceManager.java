package Projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AttendanceManager {

    private static Connection connection = null;

    public static void main(String[] args) {
        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance_management", "root", "root");

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("**** Welcome To Attendance Management System ****");
                System.out.println("Enter 1 :- Add Student Information");
                System.out.println("Enter 2 :- View Student Information");
                System.out.println("Enter 3 :- Edit Student Attendance Sheet");
                System.out.println("Enter 4 :- Exit");

                int choice = sc.nextInt();
                sc.nextLine(); // 

                switch (choice) {
                    case 1:
                        addStudentInformation(sc);
                        break;
                    case 2:
                        viewStudentInformation();
                        break;
                    case 3:
                        editStudentAttendance(sc);
                        break;
                    case 4:
                        sc.close();
                        connection.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudentInformation(Scanner sc) {
        try {
            String sql = "INSERT INTO student_attendance (name, standard, lectures_attended, total_lectures) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("Enter Student Name:");
            String name = sc.nextLine();
            System.out.println("Enter Student Standard:");
            String std = sc.nextLine();
            System.out.println("Enter Student Lecture Attendance:");
            int attend = sc.nextInt();
            System.out.println("Enter Total Lectures:");
            int total = sc.nextInt();
            sc.nextLine(); 

            ps.setString(1, name);
            ps.setString(2, std);
            ps.setInt(3, attend);
            ps.setInt(4, total);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new student attendance record was inserted successfully!");
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewStudentInformation() {
        try {
            String sql = "SELECT * FROM student_attendance";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String standard = rs.getString("standard");
                int lecturesAttended = rs.getInt("lectures_attended");
                int totalLectures = rs.getInt("total_lectures");
                double percentage = rs.getDouble("percentage");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Standard: " + standard);
                System.out.println("Lectures Attended: " + lecturesAttended);
                System.out.println("Total Lectures: " + totalLectures);
                System.out.println("Attendance Percentage: " + percentage);
                System.out.println("------------------------");
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editStudentAttendance(Scanner sc) {
        try {
            System.out.println("Enter Student ID to update:");
            int id = sc.nextInt();
            sc.nextLine(); // Consume newline

            String sql = "UPDATE student_attendance SET lectures_attended = ?, total_lectures = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println("Enter Updated Lectures Attended:");
            int attend = sc.nextInt();
            System.out.println("Enter Updated Total Lectures:");
            int total = sc.nextInt();
            sc.nextLine(); 

            ps.setInt(1, attend);
            ps.setInt(2, total);
            ps.setInt(3, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student attendance record was updated successfully!");
            } else {
                System.out.println("Student ID not found.");
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}