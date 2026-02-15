package repository;

import domain.Student;
import domain.abstractClasses.Staff;
import repository.interfaces.StaffRepositoryInt;
import service.interfaces.UniversityServiceInt;

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
    public Staff findById(int id) {
        return universityService.getUniversity().findStaffById(id);
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    @Override
    public Staff deleteById(int id) {
        Staff staff = findById(id);
        if (staff != null) {
            universityService.getUniversity().getFacultyList().forEach(f ->
                    f.getStaffMap().remove(id));

            return universityService.getUniversity().removeStaff(staff);
        }
        return null;
    }

    @Override
    public Map<Integer, Staff> getAll() {
        return universityService.getUniversity().getStaffAsMap();
    }
}
