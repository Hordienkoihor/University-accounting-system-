package service.interfaces;

import domain.Student;
import domain.abstractClasses.Staff;
import domain.records.StaffId;

import java.util.List;
import java.util.Map;

public interface StaffServiceInt {
    void registerToFaculty(Staff staff, String facultyCode);
    void unregisterFromFaculty(Staff staff, String facultyCode);

    void save(Staff staff);

    Staff delete(Staff staff);

    Staff delete(StaffId id);

    Staff findById(StaffId id);

    boolean existsById(StaffId id);

    Map<StaffId, Staff> findAll();

    void transfer(Staff staff, String from, String to);

//    List<Staff> getAllCourseOrder();


}
