package repository;

import domain.Faculty;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import repository.interfaces.StaffRepositoryInt;

import java.util.*;

public class StaffRepository implements StaffRepositoryInt {
    Map<StaffId, Staff> staffMap = new HashMap<>();

    public StaffRepository() {
    }

    @Override
    public void save(Staff staff) {
        staffMap.put(staff.getStaffId(), staff);
    }

    @Override
    public Optional<Staff> findById(StaffId id) {
        return Optional.ofNullable(staffMap.get(id));
    }

    @Override
    public boolean existsById(StaffId id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Staff> findAll() {
        return new ArrayList<>(staffMap.values());
    }

    @Override
    public void deleteById(StaffId id) {
        staffMap.remove(id);
    }

    @Override
    public Map<StaffId, Staff> getAll() {
        return new HashMap<>(staffMap);
    }
}
