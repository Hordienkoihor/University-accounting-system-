package service.interfaces;

import domain.Department;
import domain.Faculty;
import domain.Teacher;
import domain.abstractClasses.Staff;
import domain.records.StaffId;

import java.util.List;
import java.util.Map;

public interface StaffServiceInt {
    void registerToFaculty(Staff staff, String facultyCode);
    void unregisterFromFaculty(Staff staff, String facultyCode);

    void save(Staff staff);

    void delete(Staff staff);

    void delete(StaffId id);

    Staff findById(StaffId id);

    List<Staff> findBySurname(String surname);

    List<Teacher> findTeacherBySurname(String surname);

    boolean existsById(StaffId id);

    Map<StaffId, Staff> findAll();

    void transfer(Staff staff, String from, String to);

//    List<Staff> getAllCourseOrder();

    List<Teacher> findAllTeachers();

    List<Staff> findByFaculty(String facultyCode);

    List<Staff> getAllOnFacultyAlphabetical(Faculty faculty);

    List<Teacher> getAllOnTeacherOnDepartmentAlphabetical(Department department);



}
