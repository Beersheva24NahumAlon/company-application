package telran.employees;

import telran.io.Persistable;
import telran.view.*;

public class Main {
    public static void main(String[] args) {
        Company company = new CompanyImpl();
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile("employees.data");
        }
        Menu menu = new Menu("Company application", CompanyItems.getItems(company));
        menu.perform(new StandardInputOutput());
        if (company instanceof Persistable persistable) {
            persistable.saveToFile("employees.data");
        }
    }
}