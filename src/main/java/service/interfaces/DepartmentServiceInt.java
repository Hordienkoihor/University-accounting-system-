package service.interfaces;

import domain.Department;

import java.util.List;

public interface DepartmentServiceInt {
    void createDepartment(Department department);
    Department getByCode(String code);
    List<Department> getAllDepartments();
    void deleteDepartment(String code);
    void updateDepartment(Department department);

}
