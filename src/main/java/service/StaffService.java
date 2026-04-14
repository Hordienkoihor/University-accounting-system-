package service;

import domain.Department;
import domain.Faculty;
import domain.Teacher;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import exceptions.FacultyDoesNotExistException;
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

        return staff.orElse(null);
    }

    @Override
    public List<Staff> findBySurname(String surname) {
        List<Staff> staff = staffRepository.findAll();

        return staff.stream()
                .filter(staff1 -> staff1.getSurname().toLowerCase().contains(surname.toLowerCase()))
                .toList();
    }

    @Override
    public List<Teacher> findTeacherBySurname(String surname) {
        return findAllTeachers().stream()
                .filter(teacher -> teacher.getSurname().toLowerCase().contains(surname.toLowerCase()))
                .toList();
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
