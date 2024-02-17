package fsa.training.dao;

import fsa.training.dto.DepartmentSummaryDTO;
import fsa.training.dto.EmployeeByLevelDTO;
import fsa.training.entity.Department;
import fsa.training.entity.Employee;
import fsa.training.util.HibernateUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAllByDepartmentId(Integer departmentId) {
        try (Session session = HibernateUtil.getSession()) {
            String sql = "SELECT * FROM employees WHERE department_id = :deptId";
            Query<Employee> query = session.createNativeQuery(sql, Employee.class);
//            query.setParameter(2, departmentId);
            query.setParameter("deptId", departmentId);
            return query.list();
        }
    }

    @Override
    public List<Employee> getAllByDepartmentIdByHQL(Integer departmentId) {
        try (Session session = HibernateUtil.getSession()) {
            Query<Employee> query = session.createQuery("select e from employee e where e.department.id = :deptId", Employee.class);
            query.setParameter("deptId", departmentId);
            return query.list();
        }
    }

    @Override
    public Long getTotalEmployeeById(Integer departmentId) {
        try (Session session = HibernateUtil.getSession()) {
            String sql = "SELECT COUNT(*) FROM employees WHERE department_id = :deptId";
            Query<Long> query = session.createNativeQuery(sql, Long.class);
            query.setParameter("deptId", departmentId);
            return query.getSingleResult();
        }
    }

    @Override
    public List<DepartmentSummaryDTO> getSummaryDTO() {
        try (Session session = HibernateUtil.getSession()) {
            String sql = "SELECT d.department_name, COUNT(1) AS total_employee FROM departments d \n" + "LEFT JOIN employees e ON d.department_id = e.department_id\n" + "GROUP BY d.department_name";
            Query<DepartmentSummaryDTO> query = session.createNativeQuery(sql, Tuple.class)
                    .setTupleTransformer((t, a) ->
                            new DepartmentSummaryDTO((String) t[0], Long.valueOf(t[1].toString())));
            return query.list();
        }
    }

    @Override
    public List<DepartmentSummaryDTO> getSummaryDTOByHQL() {
        try (Session session = HibernateUtil.getSession()) {
            Query<DepartmentSummaryDTO> query = session.createQuery("select new fsa.training.dto.DepartmentSummaryDTO(d.departmentName, " +
                    "COUNT(e))" + " from department d left join d.employees e group by" +
                    " d.departmentName", DepartmentSummaryDTO.class);
            return query.list();
        }
    }

    @Override
    public List<DepartmentSummaryDTO> getSummaryDTOByCriteria() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<DepartmentSummaryDTO> criteriaQuery = criteriaBuilder.createQuery(DepartmentSummaryDTO.class);
            Root<Department> root = criteriaQuery.from(Department.class);
            root.join("employees");
            Join<Department, Employee> employeeJoin = root.join("employees", JoinType.INNER);

            criteriaQuery.select(
                    criteriaBuilder.construct(DepartmentSummaryDTO.class,
                            root.get("departmentName"),
                            criteriaBuilder.count(root.get("id"))
                    ));
            criteriaQuery.groupBy(root.get("departmentName"), root.get("id"));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public Department getDepartmentByEmpEmail(String employeeEmail) {
        try (Session session = HibernateUtil.getSession()) {
            Query<Department> query = session.createQuery("select d from employee e " + "inner join e.department d WHERE e.email = :email", Department.class);
            query.setParameter("email", employeeEmail);
            return query.getSingleResult();
        }
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

//    @Override
//    public List<Employee> getAll() {
//        try (Session session = HibernateUtil.getSession()) {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
//            Root<Employee> root = criteriaQuery.from(Employee.class);
//            criteriaQuery.select(root);
//
////            where
//            criteriaQuery.where(
//                    criteriaBuilder.or(
//                            criteriaBuilder.equal(root.get("level"), EmployeeLevel.DEV1),
//                            criteriaBuilder.equal(root.get("level"), EmployeeLevel.DEV2),
//                            criteriaBuilder.equal(root.get("level"), EmployeeLevel.DEV3))
//            );
//
////            sorting
//            criteriaQuery.orderBy(criteriaBuilder.asc(
//                    root.get("id")
//            ));
//
//            return session.createQuery(criteriaQuery).getResultList();
//        }
//    }

//    @Override
//    public List<Employee> getAll(String keyword, String orderFields) {
//        try (Session session = HibernateUtil.getSession()) {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
//            Root<Employee> root = criteriaQuery.from(Employee.class);
//            criteriaQuery.select(root);
//
//
////            keyword - desc, not -  asc
////         if has orderFields null , search contain   fullname  or email
////            orderfields, nullable , [-]{fieldname}
//            // Handle ordering based on orderFields parameter
//            if (orderFields != null) {
//                if (orderFields.contains("-")) {
//                    String fieldName = orderFields.substring(1); // Remove the "-" sign
//                    handleDescendingOrder(criteriaBuilder, criteriaQuery, root, fieldName);
//                } else {
//                    handleAscendingOrder(criteriaBuilder, criteriaQuery, root, orderFields);
//                }
//            }
//
//            // Handle keyword search in fullName and email fields
//            if (keyword != null) {
//                Predicate keywordPredicate = criteriaBuilder.or(
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + keyword.toLowerCase() + "%"),
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keyword.toLowerCase() + "%")
//                );
//                criteriaQuery.where(keywordPredicate);
//            }
//
//            return session.createQuery(criteriaQuery).getResultList();
//
//        }


//    }

//    public List<Employee> getAll(String keyword, String orderFields) {
//        try (Session session = HibernateUtil.getSession()) {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
//            Root<Employee> root = criteriaQuery.from(Employee.class);
//            criteriaQuery.select(root);
//
//
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                Predicate keywordPredicate = criteriaBuilder.or(
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + keyword.toLowerCase() + "%"),
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keyword.toLowerCase() + "%")
//                );
//                criteriaQuery.where(keywordPredicate);
//            }
//
//
//            if (orderFields != null && !orderFields.trim().isEmpty()) {
//                String[] orderFieldsArray = orderFields.split(",");
//                List<Order> orderList = new ArrayList<>();
//
//                for (String orderField : orderFieldsArray) {
//                    orderList.add(orderField.startsWith("-") ?
//                            criteriaBuilder.desc(root.get(orderField.substring(1))) :
//                            criteriaBuilder.asc(root.get(orderField)));
//                }
//                criteriaQuery.orderBy(orderList);
//            }
//            return session.createQuery(criteriaQuery).getResultList();
//        }
//    }

    public List<Employee> getAll(String keyword, String orderFields, int page, int size) {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root);


            if (keyword != null && !keyword.trim().isEmpty()) {
                Predicate keywordPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + keyword.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keyword.toLowerCase() + "%")
                );
                criteriaQuery.where(keywordPredicate);
            }


            if (orderFields != null && !orderFields.trim().isEmpty()) {
                String[] orderFieldsArray = orderFields.split(",");
                List<Order> orderList = new ArrayList<>();

                for (String orderField : orderFieldsArray) {
                    orderList.add(orderField.startsWith("-") ?
                            criteriaBuilder.desc(root.get(orderField.substring(1))) :
                            criteriaBuilder.asc(root.get(orderField)));
                }
                criteriaQuery.orderBy(orderList);
            }

//            paging


            return session.createQuery(criteriaQuery)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        }


    }

    public List<Employee> getAll(String keyword, String orderFields) {
        // Reuse the existing getAll method with default values for page and size
        return getAll(keyword, orderFields, 1, 5);
    }

    @Override
    public List<EmployeeByLevelDTO> groupByEmpLevel() {
        try (Session session = HibernateUtil.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeByLevelDTO> criteriaQuery = criteriaBuilder.createQuery(EmployeeByLevelDTO.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            criteriaQuery.select(
                    criteriaBuilder.construct(EmployeeByLevelDTO.class,
                            root.get("level"),
                            criteriaBuilder.count(root.get("id"))
                    )
            );

//            having totalEmployee > 1
            criteriaQuery
                    .groupBy(root.get("id"))
                    .groupBy(root.get("level"))
                    .having(criteriaBuilder.greaterThan(criteriaBuilder.count(root.get("id")), 1L));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

//    @Override
//    public void getAll(String keyword, String orderFields, int page, int size) {
//        getAll();
//    }

//    private void handleAscendingOrder(CriteriaBuilder, CriteriaQuery<Employee> criteriaQuery,
//                                      Root<Employee> root, String fieldName) {
//        if (fieldName.equals("fullName")) {
//            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("fullName")));
//        } else if (fieldName.equals("email")) {
//            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("email")));
//        } else {
//            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
//        }
//    }
//
//    private void handleDescendingOrder(CriteriaBuilder, CriteriaQuery<Employee> criteriaQuery,
//                                       Root<Employee> root, String fieldName) {
//        if (fieldName.equals("fullName")) {
//            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("fullName")));
//        } else if (fieldName.equals("email")) {
//            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("email")));
//        } else {
//            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
//        }
//    }


}


