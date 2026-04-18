package repository;

import domain.Faculty;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.StaffRepositoryInt;
import repository.io.PersistenceService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StaffRepository implements StaffRepositoryInt {
    Map<StaffId, Staff> staffMap = new ConcurrentHashMap<>();
    private final PersistenceService<Staff> persistence = new PersistenceService<>(Staff.class, "staff.json");

    private final Logger log = LoggerFactory.getLogger(StaffRepository.class);

    public StaffRepository() {
        log.info("Initializing StaffRepository");
        List<Staff> loadedData = persistence.loadAll();
        loadedData.forEach(s -> staffMap.put(s.getStaffId(), s));
        log.info("Initialized StaffRepository with {} staff", staffMap.size());
    }

    @Override
    public void save(Staff staff) {
        staffMap.put(staff.getStaffId(), staff);
        log.debug("Staff with staffId {} saved to memory", staff.getStaffId());
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
        if (staffMap.remove(id) != null) {
            log.debug("Staff with staffId {} removed from memory", id);
            persistence.saveAll(findAll());
        } else {
            log.debug("Attempted to delete non-existing staff with id: {}", id);
        }

    }

    @Override
    public Map<StaffId, Staff> getAll() {
        return new HashMap<>(staffMap);
    }
}
