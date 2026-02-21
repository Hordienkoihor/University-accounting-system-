package service.interfaces;

import domain.Student;
import domain.records.StudentId;

import java.util.List;
import java.util.Map;

public interface StudentServiceInt {
    void registerToGroup(Student student, String groupName);
    void unregisterFromGroup(Student student, String groupName);

    void save(Student student);

    void delete(Student student);

    Student findById(StudentId id);

    boolean existsById(StudentId id);

    Map<StudentId,Student> findAll();

    void transfer(Student student, String from, String to);

    List<Student> getAllCourseOrder();


}
