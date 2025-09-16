/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package schoolmanagementsystem;

import java.util.Scanner;

class Student {
    int rollNo;
    String name;
    int[] marks = new int[3];

    int total() {
        return marks[0] + marks[1] + marks[2];
    }

    double average() {
        return total() / 3.0;
    }
}

public class SchoolManagementSystem {

    // Global variables
    static final int MAX_STUDENTS = 50;
    static Student[] students = new Student[MAX_STUDENTS];
    static int studentCount = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Assume console width is 10 characters
        int consoleWidth = 10;

        String line = "--------------------------------------------";
        String message = "Welcome to Student Grade Management System";

        // Calculate spaces to center text
        int linePadding = (consoleWidth - line.length()) / 2;
        int messagePadding = (consoleWidth - message.length()) / 2;

        // Print centered welcome message
        System.out.printf("%" + (linePadding + line.length()) + "s\n", line);
        System.out.printf("%" + (messagePadding + message.length()) + "s\n", message);
        System.out.printf("%" + (linePadding + line.length()) + "s\n", line);

        int choice;
        do {
            showMenu();
            choice = getIntInput("Choose an option: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateMarks();
                case 3 -> removeStudent();
                case 4 -> viewAllStudents();
                case 5 -> searchStudent();
                case 6 -> highestScorer();
                case 7 -> classAverage();
                case 8 -> exitProgram();
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 8);
    }

    static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Student");
        System.out.println("2. Update Marks");
        System.out.println("3. Remove Student");
        System.out.println("4. View All Students");
        System.out.println("5. Search Student");
        System.out.println("6. Highest Scorer");
        System.out.println("7. Class Average");
        System.out.println("8. Exit");
    }

    static void addStudent() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Cannot add more students! Maximum reached.");
            return;
        }

        int roll = getIntInput("Enter Roll No: ");
        if (findStudent(roll) != -1) {
            System.out.println("Roll number already exists! Cannot add student.");
            return;
        }

        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            marks[i] = getValidatedMarks("Enter Marks in Subject " + (i + 1) + ": ");
        }

        Student s = new Student();
        s.rollNo = roll;
        s.name = name;
        s.marks = marks;

        students[studentCount++] = s;
        System.out.println("Student added successfully!");
    }

    static void updateMarks() {
        int roll = getIntInput("Enter Roll No to update: ");
        int index = findStudent(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = 0; i < 3; i++) {
            students[index].marks[i] = getValidatedMarks("Enter new marks for Subject " + (i + 1) + ": ");
        }
        System.out.println("Marks updated successfully!");
    }

    static void removeStudent() {
        int roll = getIntInput("Enter Roll No to remove: ");
        int index = findStudent(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = index; i < studentCount - 1; i++) {
            students[i] = students[i + 1];
        }
        students[--studentCount] = null;
        System.out.println("Student removed successfully!");
    }

    static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students to display!");
            return;
        }

        System.out.printf("%-10s %-15s %-8s %-8s %-8s %-8s %-8s%n",
                "Roll No", "Name", "Sub1", "Sub2", "Sub3", "Total", "Average");
        System.out.println("-------------------------------------------------------------------");

        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null) {
                Student s = students[i];
                System.out.printf("%-10d %-15s %-8d %-8d %-8d %-8d %-8.2f%n",
                        s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total(), s.average());
            }
        }
    }

    static void searchStudent() {
        int roll = getIntInput("Enter Roll No to search: ");
        int index = findStudent(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }
        Student s = students[index];
        System.out.printf("Roll No: %d | Name: %s | Marks: %d, %d, %d | Total: %d | Average: %.2f%n",
                s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total(), s.average());
    }

    static void highestScorer() {
        if (studentCount == 0) {
            System.out.println("No students available!");
            return;
        }

        int topIndex = 0;
        for (int i = 1; i < studentCount; i++) {
            if (students[i] != null && students[i].total() > students[topIndex].total()) {
                topIndex = i;
            }
        }
        Student s = students[topIndex];
        System.out.println("Highest Scorer:");
        System.out.printf("%s (Roll No: %d) with Total: %d and Average: %.2f%n",
                s.name, s.rollNo, s.total(), s.average());
    }

    static void classAverage() {
        if (studentCount == 0) {
            System.out.println("No students available!");
            return;
        }

        int sum = 0;
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null) {
                sum += students[i].total();
            }
        }
        double avg = sum / (double) (studentCount * 3);
        System.out.printf("Class Average: %.2f%n", avg);
    }

    static void exitProgram() {
        System.out.println("\nExiting Program...");
        System.out.println("Total Students: " + studentCount);
        classAverage();
        System.out.println("Goodbye!");
    }

    // Utility methods
    static int findStudent(int roll) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null && students[i].rollNo == roll) {
                return i;
            }
        }
        return -1;
    }

    static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine(); // clear invalid input
            }
        }
    }

    static int getValidatedMarks(String prompt) {
        int m;
        while (true) {
            m = getIntInput(prompt);
            if (m >= 0 && m <= 100) return m;
            System.out.println("Marks must be between 0 and 100!");
        }
    }
}

