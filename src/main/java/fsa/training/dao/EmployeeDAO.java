package fsa.training.dao;

import fsa.training.dto.DepartmentSummaryDTO;
import fsa.training.dto.EmployeeByLevelDTO;
import fsa.training.entity.Department;
import fsa.training.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllByDepartmentId(Integer departmentId);

    List<Employee> getAllByDepartmentIdByHQL(Integer departmentId);

    Long getTotalEmployeeById(Integer departmentId);

    List<DepartmentSummaryDTO> getSummaryDTO();

    List<DepartmentSummaryDTO> getSummaryDTOByHQL();
    List<DepartmentSummaryDTO> getSummaryDTOByCriteria();

    // lấy thông tin department theo employeeEmail
    Department getDepartmentByEmpEmail(String employeeEmail);

    List<Employee> getAll();

    List<Employee> getAll(String keyword, String orderFields);

//    void getAll(String keyword, String orderFields, int page, int size);

    List<EmployeeByLevelDTO> groupByEmpLevel();
}
