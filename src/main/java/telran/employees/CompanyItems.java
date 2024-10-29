package telran.employees;

import telran.view.*;
import java.util.*;

public class CompanyItems {
    private static Company company;

    public static Item[] getItems(Company company) {
        CompanyItems.company = company;
        Item[] res = {
            Item.of("Add employee", CompanyItems::addEmployee),
            Item.of("Display employee data", CompanyItems::displayEmployee),
            Item.of("Fire employee", CompanyItems::fireEmployee),
            Item.of("Department salary budget", CompanyItems::displayDepartmentSalaryBudget),
            Item.of("List of departments", CompanyItems::displayListOfDepartments),
            Item.of("Display managers with most factor", CompanyItems::displayMostFactorManager),
            Item.ofExit()
        };
        return res;
    }

    public static void addEmployee(InputOutput io) {
        Menu menu = new Menu("Adding employee", EmployeeItems.getItems(company));
        menu.perform(io);
    }

    public static void displayEmployee(InputOutput io) {
        int id = io.readNumberRange("Enter ID of employee", "Wrong ID", EmployeeItems.MIN_ID, EmployeeItems.MAX_ID).intValue();
        Employee employee = company.getEmployee(id);
        EmployeeItems.displayEmployee(io, employee);
    }

    public static void fireEmployee(InputOutput io) {
        int id = io.readNumberRange("Enter ID of employee", "Wrong ID", EmployeeItems.MIN_ID, EmployeeItems.MAX_ID).intValue();
        try {
            company.removeEmployee(id);
        } catch (NoSuchElementException e) {
            io.writeString("Employee with given ID isn't found");
        }
    }
    
    public static void displayDepartmentSalaryBudget(InputOutput io) {
        HashSet<String> departmentsSet = new HashSet<>(Arrays.asList(company.getDepartments()));
        String department = io.readStringOptions("Enter department", "Department is not in the list", departmentsSet);
        io.writeString(String.format("Salary budget of department %s is %d",department, company.getDepartmentBudget(department)));
    }

    public static void displayListOfDepartments(InputOutput io) {
        String[] departments = company.getDepartments();
        for (String department : departments) {
            io.writeString(department);
        }
    }

    public static void displayMostFactorManager(InputOutput io) {
        Manager[] managers = company.getManagersWithMostFactor();
        for (Manager manager : managers) {
            EmployeeItems.displayEmployee(io, manager);
        }
    }
}
