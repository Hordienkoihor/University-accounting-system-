package service;

import domain.Department;
import domain.Faculty;
import domain.Group;
import domain.Student;
import domain.records.StudentId;
import exceptions.GroupDoesNotExistException;
import exceptions.StudentAddingError;
import exceptions.StudentRegisterToGroupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.StudentRepositoryInt;
import service.interfaces.GroupServiceInt;
import service.interfaces.StudentServiceInt;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentService implements StudentServiceInt {
    private final StudentRepositoryInt studentRepository;
    private final GroupServiceInt groupService;

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepositoryInt studentRepository, GroupServiceInt groupService) {
        this.studentRepository = studentRepository;
        this.groupService = groupService;
    }

    @Override
    public void registerToGroup(Student student, String groupName) {
        log.info("Attempting to register student {} to group '{}'", student.getStudentId(), groupName);
        try {
            save(student);
        } catch (StudentAddingError e) {
            log.error("Failed to save student during group registration: {}", e.getMessage());
            throw new StudentRegisterToGroupException(e.getMessage());
        }


        boolean alrInGroup = student.getGroup() != null;

        if (alrInGroup) {
            log.warn("Registration blocked: Student {} already in group '{}'", student.getStudentId(), student.getGroup().getName());
            throw new StudentAddingError("Student already exists in group " + groupName);
        }

        Group group = groupService.findByName(groupName);

        if (group != null) {
            student.setGroup(group);
            log.info("Student {} successfully added to group '{}'", student.getStudentId(), groupName);
        } else {
            throw new GroupDoesNotExistException("Group " + groupName + " does not exist");
        }
    }

    @Override
    public void unregisterFromGroup(Student student, String groupName) {
        log.info("Unregistering student {} from group '{}'", student.getStudentId(), groupName);
        Group group = groupService.findByName(groupName);

        if (group != null) {
            student.setGroup(null);
            log.info("Student {} removed from group '{}'", student.getStudentId(), groupName);
        } else {
            log.error("Unregister failed: Group '{}' not found", groupName);
            throw new GroupDoesNotExistException("Group " + groupName + " does not exist");
        }
    }

    @Override
    public void add(Student student) {
        if (studentRepository.existsById(student.getStudentId())) {
            throw new StudentAddingError("Error: Student with id" + student.getStudentId() + " already exists");
        }
        studentRepository.save(student);
    }

    @Override
    public void save(Student student) {
        if (student == null) {
            throw new StudentAddingError("Student cannot be null");
        }

        this.studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        this.studentRepository.deleteById(student.getStudentId());
    }

    @Override
    public Student findById(StudentId id) {
        Optional<Student> student = studentRepository.findById(id);

        return student.orElse(null);
    }

    @Override
    public List<Student> findBySurname(String surname) {
        List<Student> students = this.studentRepository.findAll();

        return students.stream()
                .filter(student -> student.getSurname().toLowerCase().contains(surname.toLowerCase()))
                .toList();
    }

    @Override
    public boolean existsById(StudentId id) {
        return this.studentRepository.existsById(id);
    }

    @Override
    public Map<StudentId, Student> findAll() {
        return this.studentRepository.getAll();
    }

    @Override
    public void transfer(Student student, String from, String to) {
        unregisterFromGroup(student, from);
        registerToGroup(student, to);
    }

    @Override
    public List<Student> getAllCourseOrder() {
        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getCourse))
                .toList();
    }

    @Override
    public List<Student> findByFaculty(String facultyCode) {
        return studentRepository.getAll()
                .values()
                .stream()
                .filter(student -> {
                    if (student.getGroup() == null) {
                        return false;
                    }

                    return student.getGroup().getSpecialty().getDepartment().getFaculty().getCode().equals(facultyCode);
                })
                .toList();

    }


    @Override
    public List<Student> getAllOnFacultyAlphabetical(Faculty faculty) {
        return studentRepository.getAll()
                .values()
                .stream()
                .filter(student -> student.getGroup().getSpecialty().getDepartment().getFaculty().getCode().equals(faculty.getCode()))
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    @Override
    public List<Student> getAllOnDepartmentAlphabetical(Department department) {
        return findAll().values().stream()
                .filter(student -> student.getGroup().getSpecialty().getDepartment().getCode().equals(department.getCode()))
                .sorted(Comparator.comparing(Student::getFullName))
                .toList();
    }


}
