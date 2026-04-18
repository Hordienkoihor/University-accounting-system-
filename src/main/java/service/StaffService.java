package service;

import domain.Department;
import domain.Faculty;
import domain.Teacher;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import exceptions.FacultyDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.StaffRepositoryInt;
import service.interfaces.FacultyServiceInt;
import service.interfaces.StaffServiceInt;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StaffService implements StaffServiceInt {
    private final StaffRepositoryInt staffRepository;
    private final FacultyServiceInt facultyService;

    private static final Logger log = LoggerFactory.getLogger(StaffService.class);

    public StaffService(StaffRepositoryInt staffRepository, FacultyServiceInt facultyService) {
        this.staffRepository = staffRepository;
        this.facultyService = facultyService;
    }

    /*rudiment*/
    @Override
    public void registerToFaculty(Staff staff, String facultyCode) {
        Optional<Faculty> faculty = facultyService.findByCode(facultyCode);

        if (faculty.isEmpty()) {
            throw new FacultyDoesNotExistException("Faculty with code " + facultyCode + " does not exist");
        }

        if (!staffRepository.existsById(staff.getStaffId())) {
            staffRepository.save(staff);
        }

//        staff.setFaculty(faculty.get());
    }

    /*rudiment*/
    @Override
    public void unregisterFromFaculty(Staff staff, String facultyCode) {
        Optional<Faculty> faculty = facultyService.findByCode(facultyCode);

        if (faculty.isEmpty()) {
            throw new FacultyDoesNotExistException("Faculty with code " + facultyCode + " does not exist");
        }

        if (!staffRepository.existsById(staff.getStaffId())) {
            staffRepository.save(staff);
        }

//        staff.setFaculty(null);
    }

    @Override
    public void save(Staff staff) {
        log.info("Saving staff member: {} {} (ID: {})", staff.getName(), staff.getSurname(), staff.getStaffId());
        staffRepository.save(staff);
    }

    @Override
    public void delete(Staff staff) {
        staffRepository.deleteById(staff.getStaffId());
    }

    @Override
    public void delete(StaffId id) {
        log.info("Attempting to delete staff member with ID: {}", id);
        if (!staffRepository.existsById(id)) {
            log.warn("Delete failed: Staff member with ID {} not found", id);
        } else {
            staffRepository.deleteById(id);
            log.info("Staff member with ID {} deleted successfully", id);
        }
    }

    @Override
    public Staff findById(StaffId id) {
        log.debug("Searching for staff by ID: {}", id);

        return staffRepository.findById(id).orElseGet(() -> {
            log.warn("Staff member with ID {} not found", id);
            return null;
        });
    }

    @Override
    public List<Staff> findBySurname(String surname) {
        log.debug("Searching for staff by surname contains: '{}'", surname);
        List<Staff> staff = staffRepository.findAll();

        List<Staff> result = staff.stream()
                .filter(staff1 -> staff1.getSurname().toLowerCase().contains(surname.toLowerCase()))
                .toList();

        log.info("Found {} staff members matching surname '{}'", result.size(), surname);
        return result;
    }

    @Override
    public List<Teacher> findTeacherBySurname(String surname) {
        log.debug("Searching for teacher by surname contains: '{}'", surname);

        List<Teacher> result = findAllTeachers().stream()
                .filter(teacher -> teacher.getSurname().toLowerCase().contains(surname.toLowerCase()))
                .toList();

        log.info("Found {} teachers members matching surname '{}'", result.size(), surname);
        return result;
    }

    @Override
    public boolean existsById(StaffId id) {
        return findById(id) != null;
    }

    @Override
    public Map<StaffId, Staff> findAll() {
        return staffRepository.getAll();
    }

    /*rudiment*/
    @Override
    public void transfer(Staff staff, String from, String to) {
        unregisterFromFaculty(staff, from);
        registerToFaculty(staff, to);
    }

    @Override
    public List<Teacher> findAllTeachers() {
        log.debug("Fetching all teachers from staff list");
        return staffRepository.findAll().stream()
                .filter(staff -> staff instanceof Teacher)
                .map(staff -> (Teacher) staff)
                .toList();
    }

    @Override
    public List<Staff> findByFaculty(String facultyCode) {
        return staffRepository.getAll()
                .values()
                .stream()
                .filter(staff -> staff instanceof Teacher)
                .filter(staff -> {
                    Teacher teacher = (Teacher) staff;

                    if (teacher.getDepartment() == null) {
                        return false;
                    }

                    return teacher.getDepartment().getFaculty().getCode().equals(facultyCode);

                })
                .toList();

    }

    @Override
    public List<Staff> getAllOnFacultyAlphabetical(Faculty faculty) {
        return findByFaculty(faculty.getCode())
                .stream()
                .sorted(Comparator.comparing(Staff::getName))
                .toList();
    }

    @Override
    public List<Teacher> getAllOnTeacherOnDepartmentAlphabetical(Department department) {
        return findAll().values().stream()
                .filter(staff -> staff instanceof Teacher)
                .map(staff -> (Teacher) staff)
                .filter(teacher -> teacher.getDepartment() != null && teacher.getDepartment().equals(department))
                .sorted(Comparator.comparing(Staff::getName))
                .toList();
    }
}
