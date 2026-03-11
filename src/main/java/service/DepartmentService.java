package service;

import domain.Department;
import domain.Faculty;
import exceptions.DepartmentAlreadyExistsException;
import exceptions.DepartmentNotFoundException; // Бажано додати цей клас винятків
import exceptions.FacultyDoesNotExistException;
import repository.interfaces.DepartmentRepositoryInt;
import service.interfaces.DepartmentServiceInt;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Optional;

public class DepartmentService implements DepartmentServiceInt {

    private final DepartmentRepositoryInt departmentRepository;
    private final FacultyServiceInt facultyService;

    public DepartmentService(DepartmentRepositoryInt departmentRepository, FacultyServiceInt facultyService) {
        this.departmentRepository = departmentRepository;
        this.facultyService = facultyService;

    }

    @Override
    public void createDepartment(Department department) {
        if (departmentRepository.existsById(department.getCode())) {
            throw new DepartmentAlreadyExistsException("Department with such code already exists");
        }

        Optional<Faculty> faculty = facultyService.findByCode(department.getFaculty().getCode());

        if (faculty.isEmpty()) {
            throw new FacultyDoesNotExistException("Faculty with code " + department.getFaculty().getCode() + " not found");
        }

        faculty.get().add(department);
    }

    @Override
    public Department getByCode(String code) {
        return departmentRepository.findById(code)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with code " + code + " not found"));
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public void deleteDepartment(String code) {
        if (!departmentRepository.existsById(code)) {
            throw new DepartmentNotFoundException("Cannot delete: Department with code " + code + " does not exist");
        }
        departmentRepository.deleteById(code);
    }

    @Override
    public void updateDepartment(Department department) {
        if (!departmentRepository.existsById(department.getCode())) {
            throw new DepartmentNotFoundException("Cannot update: Department with code " + department.getCode() + " not found");
        }

        departmentRepository.save(department);
    }
}