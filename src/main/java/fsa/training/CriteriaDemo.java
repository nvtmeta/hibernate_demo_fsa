package fsa.training;

import fsa.training.dao.EmployeeDAO;
import fsa.training.dao.EmployeeDAOImpl;

public class CriteriaDemo {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

//        employeeDAO.getAll().forEach(System.out::println);


//        employeeDAO.getAll(null, null).forEach(System.out::println);

//        employeeDAO.getAll(null, "").forEach(System.out::println);
//        employeeDAO.getAll(null, "").forEach(System.out::println);
//        employeeDAO.getAll("jac", "").forEach(System.out::println);
//        employeeDAO.getAll("thinh", "-fullName, email").forEach(System.out::println);
//        employeeDAO.getAll("bill", "fullName,email").forEach(System.out::println);
//        employeeDAO.getAll("", "").forEach(System.out::println)
//        employeeDAO.getAll("van a", "-fullName").forEach(System.out::println);
//        employeeDAO.groupByEmpLevel().forEach(System.out::println);
        employeeDAO.getSummaryDTOByCriteria().forEach(System.out::println);
    }
}
