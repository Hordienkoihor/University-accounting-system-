package service;

import domain.Department;
import domain.Faculty;
import domain.Group;
import domain.Student;
import domain.records.StudentId;
import exceptions.GroupDoesNotExistException;
import exceptions.StudentAddingError;
import exceptions.StudentRegisterToGroupException;
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

    public StudentService(StudentRepositoryInt studentRepository, GroupServiceInt groupService) {
        this.studentRepository = studentRepository;
        this.groupService = groupService;
    }

    @Override
    public void registerToGroup(Student student, String groupName) {
        try {
            save(student);
        } catch (StudentAddingError e) {
            throw new StudentRegisterToGroupException(e.getMessage());
        }


        boolean alrInGroup = student.getGroup() != null;

        if (alrInGroup) {
            throw new StudentAddingError("Student already exists in group " + groupName);
        }

        Group group = groupService.findByName(groupName);

        if (group != null) {
            student.setGroup(group);
            System.out.println("Student added to group " + groupName);
        } else {
            throw new GroupDoesNotExistException("Group " + groupName + " does not exist");
        }
    }

    @Override
    public void unregisterFromGroup(Student student, String groupName) {
        Group group = groupService.findByName(groupName);

        if (group != null) {
            student.setGroup(null);
            System.out.println("Student removed from group " + groupName);
        } else {
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
                .filter(student -> student.getFaculty().getCode().equals(facultyCode))
                .toList();

    }

    @Override
    public List<Student> getAllOnFacultyAlphabetical(Faculty faculty) {
        return studentRepository.getAll()
                .values()
                .stream()
                .filter(student -> student.getFaculty().equals(faculty))
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    @Override
    public List<Student> getAllOnDepartmentAlphabetical(Department department) {
        return findAll().values().stream()
                .filter(student -> student.getGroup().getDepartment().equals(department))
                .sorted(Comparator.comparing(Student::getFullName))
                .toList();
    }


}
