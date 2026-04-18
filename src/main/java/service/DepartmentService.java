package service;

import domain.Department;
import domain.Faculty;
import exceptions.DepartmentAlreadyExistsException;
import exceptions.DepartmentNotFoundException; // Бажано додати цей клас винятків
import exceptions.FacultyDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.DepartmentRepositoryInt;
import service.interfaces.DepartmentServiceInt;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Optional;

public class DepartmentService implements DepartmentServiceInt {

    private final DepartmentRepositoryInt departmentRepository;
    private final FacultyServiceInt facultyService;

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    public DepartmentService(DepartmentRepositoryInt departmentRepository, FacultyServiceInt facultyService) {
        this.departmentRepository = departmentRepository;
        this.facultyService = facultyService;

    }

    @Override
    public void register(Department department) {
        log.info("Attempting to register new department: {}", department.getCode());

        if (departmentRepository.existsById(department.getCode())) {
            log.error("Failed registration: Department with code {} already exists", department.getCode());
            throw new DepartmentAlreadyExistsException("Department with such code already exists");
        }

        Optional<Faculty> faculty = facultyService.findByCode(department.getFaculty().getCode());

        if (faculty.isEmpty()) {
            log.warn("Registration failed: Faculty {} not found for department {}",
                    department.getFaculty().getCode(), department.getCode());
            throw new FacultyDoesNotExistException("Faculty with code " + department.getFaculty().getCode() + " not found");
        }

        this.departmentRepository.save(department);
        log.info("Department {} successfully registered.", department.getCode());
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
        log.info("Request to delete department: {}", code);
        if (!departmentRepository.existsById(code)) {
            log.warn("Delete aborted: Department {} not found", code);
            throw new DepartmentNotFoundException("Cannot delete: Department with code " + code + " does not exist");
        }
        departmentRepository.deleteById(code);
        log.info("Department {} successfully deleted", code);
    }

    @Override
    public void updateDepartment(Department department) {
        log.info("Updating department: {}", department.getCode());

        if (!departmentRepository.existsById(department.getCode())) {
            log.error("Update failed: Department {} does not exist", department.getCode());
            throw new DepartmentNotFoundException("Cannot update: Department with code " + department.getCode() + " not found");
        }

        departmentRepository.save(department);
        log.debug("Department {} updated in repository", department.getCode());
    }
}