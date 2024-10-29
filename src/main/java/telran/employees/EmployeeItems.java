package telran.employees;

import telran.view.*;

public class EmployeeItems {
    public static final double MIN_ID = 100000;
    public static final double MAX_ID = 900000;
    private static final double MIN_BASIC_SALARY = 7000;
    private static final double MAX_BASIC_SALARY = 30000;
    private static final double MIN_FACTOR = 1;
    private static final double MAX_FACTOR = 2;
    private static final double MIN_WAGE = 0;
    private static final double MAX_WAGE = 0;
    private static final double MIN_HOURS = 0;
    private static final double MAX_HOURS = 0;
    private static final double MIN_PERCENT = 0;
    private static final double MAX_PERCENT = 0;
    private static final double MIN_SALES = 0;
    private static final double MAX_SALES = 0;
    private static Company company;

    public static Item[] getItems(Company company) {
        EmployeeItems.company = company;
        Item[] res = {
            Item.of("Hire employee", EmployeeItems::hireEmployee),
            Item.of("Hire wage employee", EmployeeItems::hireWageEmployee),
            Item.of("Hire sales person", EmployeeItems::hireSalesPerson),
            Item.of("Hire manager", EmployeeItems::hireManager),
            Item.ofExit()
        };
        return res;
    }

    public static void hireEmployee(InputOutput io) {
        company.addEmployee(hireSomeEmployee(io, "telran.employees.Employee"));
    }

    public static void hireWageEmployee(InputOutput io) {
        company.addEmployee(hireSomeEmployee(io, "telran.employees.WageEmployee"));
    }

    public static void hireSalesPerson(InputOutput io) {
        company.addEmployee(hireSomeEmployee(io, "telran.employees.SalesPerson"));
    }

    public static void hireManager(InputOutput io) {
        company.addEmployee(hireSomeEmployee(io, "telran.employees.Manager"));
    }

    public static void displayEmployee(InputOutput io, Employee employee) {
        io.writeString(String.format("ID: %d", employee.getId()));
        io.writeString(String.format("Basic salary: %d", employee.computeSalary()));
        io.writeString(String.format("Department: %s", employee.getDepartment()));
        if (employee instanceof Manager manager) {
            io.writeString(String.format("Factor: %f", manager.getFactor()));
        }
        if (employee instanceof WageEmployee wageEmployee) {
            io.writeString(String.format("Wage: %d", wageEmployee.getWage()));
            io.writeString(String.format("Hours: %d", wageEmployee.getHours()));
            if (wageEmployee instanceof SalesPerson salesPerson) {
                io.writeString(String.format("Percent: %f", salesPerson.getPercent()));
                io.writeString(String.format("Sales: %d", salesPerson.getSales()));
            }
        }
    }

    private static Employee hireSomeEmployee(InputOutput io, String className) {
        Employee res = null;
        int id = io.readNumberRange("Enter ID of employee", "Wrong ID", MIN_ID, MAX_ID).intValue();
        int basicSalary = io.readNumberRange("Enter basic salary of employee", "Wrong salary", MIN_BASIC_SALARY, MAX_BASIC_SALARY).intValue();
        String department = io.readString("Enter department");
        if (className.equals("telran.employees.Employee")) {
            res = new Employee(id, basicSalary, department);
        } else if (className.equals("telran.employees.Manager")) {
            float factor = io.readNumberRange("Enter factor of manager", "Wrong factor", MIN_FACTOR, MAX_FACTOR).floatValue();
            res = new Manager(id, basicSalary, department, factor);
        } else {
            int wage = io.readNumberRange("Enter wage of wage employee", "Wrong wage", MIN_WAGE, MAX_WAGE).intValue();
            int hours = io.readNumberRange("Enter hours of wage employee", "Wrong hours", MIN_HOURS, MAX_HOURS).intValue();
            if (className.equals("telran.employees.WageEmployee")) {
                res = new WageEmployee(id, basicSalary, department, wage, hours);
            } else {
                float percent = io.readNumberRange("Enter parcent of sales person", "Wrong percent", MIN_PERCENT, MAX_PERCENT).floatValue();
                long sales = io.readNumberRange("Enter sales of sales person", "Wrong sales", MIN_SALES, MAX_SALES).intValue();
                res = new SalesPerson(id, basicSalary, department, wage, hours, percent, sales);
            }
        }
        return res;
    }

}
