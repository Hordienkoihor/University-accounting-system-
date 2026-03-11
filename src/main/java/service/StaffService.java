package service;

import domain.Faculty;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import exceptions.FacultyDoesNotExistException;
import repository.interfaces.StaffRepositoryInt;
import service.interfaces.FacultyServiceInt;
import service.interfaces.StaffServiceInt;

import java.util.Map;
import java.util.Optional;

public class StaffService implements StaffServiceInt {
    private final StaffRepositoryInt staffRepository;
    private final FacultyServiceInt facultyService;

    public StaffService(StaffRepositoryInt staffRepository, FacultyServiceInt facultyService) {
        this.staffRepository = staffRepository;
        this.facultyService = facultyService;
    }

    @Override
    public void registerToFaculty(Staff staff, String facultyCode) {
        Optional <Faculty> faculty = facultyService.findByCode(facultyCode);

        if (faculty.isEmpty()) {
            throw new FacultyDoesNotExistException("Faculty with code " + facultyCode + " does not exist");
        }

        if (!staffRepository.existsById(staff.getStaffId())) {
            staffRepository.save(staff);
        }

        faculty.get().addStaff(staff);
    }

    @Override
    public void unregisterFromFaculty(Staff staff, String facultyCode) {
        Optional <Faculty> faculty = facultyService.findByCode(facultyCode);

        if (faculty.isEmpty()) {
            throw new FacultyDoesNotExistException("Faculty with code " + facultyCode + " does not exist");
        }

        if (!staffRepository.existsById(staff.getStaffId())) {
            staffRepository.save(staff);
        }

        faculty.get().removeStaff(staff);
    }

    @Override
    public void save(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public void delete(Staff staff) {
        staffRepository.deleteById(staff.getStaffId());
    }

    @Override
    public void delete(StaffId id) {
        staffRepository.deleteById(id);
    }

    @Override
    public Staff findById(StaffId id) {
        Optional<Staff> staff = staffRepository.findById(id);

        if (staff.isEmpty()) {
            return null;
        }
        return staff.get();
    }

    @Override
    public boolean existsById(StaffId id) {
        return findById(id) != null;
    }

    @Override
    public Map<StaffId, Staff> findAll() {
        return staffRepository.getAll();
    }

    @Override
    public void transfer(Staff staff, String from, String to) {
        unregisterFromFaculty(staff, from);
        registerToFaculty(staff, to);
    }
}
