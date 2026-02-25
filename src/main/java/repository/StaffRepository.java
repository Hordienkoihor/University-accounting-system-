package repository;

import domain.abstractClasses.Staff;
import domain.records.StaffId;
import repository.interfaces.StaffRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.util.List;
import java.util.Map;

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
    public Staff findById(StaffId id) {
        return universityService.getUniversity().findStaffById(id);
    }

    @Override
    public boolean existsById(StaffId id) {
        return findById(id) != null;
    }

    @Override
    public List<Staff> findAll() {
        return universityService.getUniversity().getStaffAsList();
    }

    @Override
    public void deleteById(StaffId id) {
        Staff staff = findById(id);
        if (staff != null) {
            universityService.getUniversity().getFacultyList().forEach(f ->
                    f.getStaffMap().remove(id));

            universityService.getUniversity().removeStaff(staff);
        }
    }

    @Override
    public Map<StaffId, Staff> getAll() {
        return universityService.getUniversity().getStaffAsMap();
    }
}
