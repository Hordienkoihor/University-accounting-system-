package repository.interfaces;

import domain.Student;
import domain.abstractClasses.Staff;
import domain.records.StaffId;

import java.util.Map;

public interface StaffRepositoryInt {
    void save(Staff staff);

    Staff findById(StaffId id);
    boolean existsById(StaffId id);

    Staff deleteById(StaffId id);

    Map<StaffId, Staff> getAll();
}
