package repository;

import domain.abstractClasses.Staff;
import domain.records.StaffId;
import repository.interfaces.StaffRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StaffRepository implements StaffRepositoryInt {
    private final UniversityServiceInt universityService;

    public StaffRepository(UniversityServiceInt universityServiceInt) {
        this.universityService = universityServiceInt;
    }

    @Override
    public void save(Staff staff) {
        universityService.getUniversity().addPerson(staff);
    }

    @Override
    public Optional<Staff> findById(StaffId id) {
        return universityService.getUniversity().findStaffById(id);
    }

    @Override
    public boolean existsById(StaffId id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Staff> findAll() {
        return universityService.getUniversity().getStaffAsList();
    }

    @Override
    public void deleteById(StaffId id) {
        Optional<Staff> staff = findById(id);
        if (staff.isPresent()) {
            universityService.getUniversity().getFacultyList().forEach(f ->
                    f.removeStaff(staff.get()));

            universityService.getUniversity().removeStaff(staff.get());
        }
    }

    @Override
    public Map<StaffId, Staff> getAll() {
        return universityService.getUniversity().getStaffAsMap();
    }
}
