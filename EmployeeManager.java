import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple console application for managing employee records.
 * Compile with: javac EmployeeManager.java
 * Run with:     java EmployeeManager
 */
public class EmployeeManager {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Map<Integer, Employee> EMPLOYEES = new LinkedHashMap<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Employee Manager ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Exit");

            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
        SCANNER.close();
    }

    private static void addEmployee() {
        int id = readInt("Employee ID: ");
        if (EMPLOYEES.containsKey(id)) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readRequiredText("Name: ");
        String department = readRequiredText("Department: ");
        double salary = readSalary("Salary: ");

        EMPLOYEES.put(id, new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (EMPLOYEES.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.printf("%-12s %-24s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee employee : EMPLOYEES.values()) {
            employee.display();
        }
    }

    private static void searchEmployee() {
        int id = readInt("Enter employee ID to search: ");
        Employee employee = EMPLOYEES.get(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.printf("%-12s %-24s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
            employee.display();
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readSalary(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Show the same validation message for invalid numeric input.
            }
            System.out.println("Please enter a valid non-negative salary.");
        }
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private void display() {
            System.out.printf("%-12d %-24s %-20s %12.2f%n", id, name, department, salary);
        }
    }
}
