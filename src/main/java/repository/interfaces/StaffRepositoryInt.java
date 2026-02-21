package repository.interfaces;

import domain.Student;
import domain.abstractClasses.Staff;
import domain.records.StaffId;

import java.util.Map;

public interface StaffRepositoryInt {
    void save(Staff staff);

    Staff findById(int id);
    boolean existsById(int id);

    Staff deleteById(int id);

    Map<StaffId, Staff> getAll();
}
