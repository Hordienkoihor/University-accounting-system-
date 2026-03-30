package repository;

import domain.Faculty;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import repository.interfaces.StaffRepositoryInt;
import repository.io.PersistenceService;

import java.util.*;

public class StaffRepository implements StaffRepositoryInt {
    Map<StaffId, Staff> staffMap = new HashMap<>();
    private final PersistenceService<Staff> persistence = new PersistenceService<>(Staff.class, "staff.json");

    public StaffRepository() {
        persistence.loadAll().forEach(this::save);
    }

    @Override
    public void save(Staff staff) {
        staffMap.put(staff.getStaffId(), staff);
        persistence.saveAll(findAll());
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
        persistence.saveAll(findAll());
    }

    @Override
    public Map<StaffId, Staff> getAll() {
        return new HashMap<>(staffMap);
    }
}
