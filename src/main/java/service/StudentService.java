package service;

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
import java.util.stream.Collectors;

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


        boolean alrInGroup = groupService.findAll()
                .stream().anyMatch(group -> group.getStudents().contains(student.getStudentId()));

        if (alrInGroup) {
            throw new StudentAddingError("Student already exists in group " + groupName);
        }

        Group group = groupService.findByName(groupName);

        if (group != null) {
            group.addStudent(student);
            System.out.println("Student added to group " + groupName);
        } else {
            throw new GroupDoesNotExistException("Group " + groupName + " does not exist");
        }
    }

    @Override
    public void unregisterFromGroup(Student student, String groupName) {
        Group group = groupService.findByName(groupName);

        if (group != null) {
            group.removeStudent(student);
            System.out.println("Student removed from group " + groupName);
        } else {
            throw new GroupDoesNotExistException("Group " + groupName + " does not exist");
        }
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
        groupService.findAll()
                .forEach(group -> group.removeStudent(student));

        this.studentRepository.deleteById(student.getStudentId());
    }

    @Override
    public Student findById(StudentId id) {
        return this.studentRepository.findById(id);
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
                .collect(Collectors.toList());
    }



}
