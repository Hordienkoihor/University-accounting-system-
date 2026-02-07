package service;

import domain.Group;
import domain.Student;
import exceptions.GroupDoesNotExistException;
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
        save(student);

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
        this.studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {
        this.studentRepository.deleteById(student.getId());
    }

    @Override
    public Student findById(int id) {
        return this.studentRepository.findById(id);
    }

    @Override
    public boolean existsById(int id) {
        return this.studentRepository.existsById(id);
    }

    @Override
    public Map<Integer, Student> findAll() {
        return this.studentRepository.getAll();
    }

    @Override
    public void transfer(Student student, String from, String to) {
        unregisterFromGroup(student, from);
        registerToGroup(student, to);
    }

    @Override
    public List<Student> getAllCourseOrder() {
        return studentRepository.getAll().values().stream()
                .sorted(Comparator.comparing(Student::getCourse))
                .collect(Collectors.toList());
    }



}
