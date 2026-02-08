package service;

import domain.Faculty;
import domain.abstractClasses.Staff;
import exceptions.FacultyDoesNotExistException;
import repository.interfaces.StaffRepositoryInt;
import service.interfaces.FacultyServiceInt;
import service.interfaces.StaffServiceInt;

import java.util.Map;

public class StaffService implements StaffServiceInt {
    private final StaffRepositoryInt staffRepository;
    private final FacultyServiceInt facultyService;

    public StaffService(StaffRepositoryInt staffRepository, FacultyServiceInt facultyService) {
        this.staffRepository = staffRepository;
        this.facultyService = facultyService;
    }

    @Override
    public void registerToFaculty(Staff staff, String facultyCode) {
        Faculty faculty = facultyService.findByCode(facultyCode);

        if (faculty == null) {
            throw new FacultyDoesNotExistException("Faculty with code " + facultyCode + " does not exist");
        }

        if (!staffRepository.existsById(staff.getId())) {
            staffRepository.save(staff);
        }

        faculty.addStaff(staff);
    }

    @Override
    public void unregisterFromFaculty(Staff staff, String facultyCode) {
        Faculty faculty = facultyService.findByCode(facultyCode);

        if (faculty == null) {
            throw new FacultyDoesNotExistException("Faculty with code " + facultyCode + " does not exist");
        }

        if (!staffRepository.existsById(staff.getId())) {
            staffRepository.save(staff);
        }

        faculty.removeStaff(staff);
    }

    @Override
    public void save(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public Staff delete(Staff staff) {
        return staffRepository.deleteById(staff.getId());
    }

    @Override
    public Staff delete(int id) {
        return staffRepository.deleteById(id);
    }

    @Override
    public Staff findById(int id) {
        return staffRepository.findById(id);
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    @Override
    public Map<Integer, Staff> findAll() {
        return staffRepository.getAll();
    }

    @Override
    public void transfer(Staff staff, String from, String to) {
        unregisterFromFaculty(staff, from);
        registerToFaculty(staff, to);
    }
}
