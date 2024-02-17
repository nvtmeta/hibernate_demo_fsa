package fsa.training;

import fsa.training.dao.EmployeeDAO;
import fsa.training.dao.EmployeeDAOImpl;
import fsa.training.entity.Employee;
import fsa.training.util.HibernateUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
//        List<Employee> employeeList = employeeDAO.getAllByDepartmentId(1);
//        employeeList.forEach(System.out::println);
//        System.out.println(employeeDAO.getTotalEmployeeById(1));
        employeeDAO.getSummaryDTOByHQL().forEach(System.out::println);
//        System.out.println(
//                employeeDAO.getDepartmentByEmpEmail("nguyenvand@gmail.com"));
    }
}