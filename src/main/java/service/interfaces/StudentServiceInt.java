package service.interfaces;

import domain.Department;
import domain.Faculty;
import domain.Student;
import domain.Teacher;
import domain.abstractClasses.Staff;
import domain.records.StudentId;

import java.util.List;
import java.util.Map;

public interface StudentServiceInt {
    void registerToGroup(Student student, String groupName);
    void unregisterFromGroup(Student student, String groupName);

    void add(Student student);

    void save(Student student);

    void delete(Student student);

    Student findById(StudentId id);

    boolean existsById(StudentId id);

    Map<StudentId,Student> findAll();

    void transfer(Student student, String from, String to);

    List<Student> getAllCourseOrder();

    List<Student> findByFaculty(String facultyCode);

    List<Student> getAllOnFacultyAlphabetical(Faculty faculty);

    List<Student> getAllOnDepartmentAlphabetical(Department department);




}
