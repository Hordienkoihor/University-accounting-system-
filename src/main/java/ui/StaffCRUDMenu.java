package ui;

import domain.Department;
import domain.Teacher;
import domain.enums.ScientificDegree;
import domain.enums.UniversityPosition;
import service.interfaces.DepartmentServiceInt;
import service.interfaces.StaffServiceInt;

import java.time.LocalDate;
import java.util.Arrays;

import static Utilitys.InputHandler.*;

public class StaffCRUDMenu {
    private final StaffServiceInt staffService;
    private final DepartmentServiceInt departmentService; // Потрібен для призначення викладача на кафедру

    public StaffCRUDMenu(StaffServiceInt staffService, DepartmentServiceInt departmentService) {
        this.staffService = staffService;
        this.departmentService = departmentService;
    }

    public void handleStaffCRUD() {
        String[] options = {
                "1. Create Teacher",
                "2. View All Staff",
                "3. Update Teacher",
                "4. Delete Teacher",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Teacher Management ---");
            for (String opt : options) System.out.println(opt);

            int choice = getValidInt("action", 4);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createTeacher();
                case 2 -> listStaff();
                case 3 -> updateTeacher();
                case 4 -> deleteTeacher();
            }
        }
    }

    private void createTeacher() {
        System.out.println("--- New Teacher Registration ---");
        try {
            String name = getValidString("name");
            String surname = getValidString("surname");
            String fatherName = getValidString("father name");
            String email = getValidString("email");
            String phone = getValidString("phone number");
            LocalDate dob = getValidDate();

            double hours = getValidDouble("weekly hours");
            double rate = getValidDouble("hourly rate");

            Teacher teacher = new Teacher(
                    name, surname, fatherName, email, phone, dob,
                    getUniversityPosition(),
                    getScientificDegree(),
                    hours, rate
            );

            assignDepartment(teacher);

            staffService.save(teacher);
            System.out.println("Teacher created. Staff ID: " + teacher.getStaffId());
        } catch (Exception e) {
            System.out.println("Error creating teacher: " + e.getMessage());
        }
    }

    private UniversityPosition getUniversityPosition() {
        int[] count = new int[1];
        count[0] = 0;
        Arrays.stream(UniversityPosition.values()).toList().forEach(universityPosition -> {
            System.out.println(count[0] + 1 + ". " + universityPosition);
            count[0]++;
        });

        int choice = getValidInt("position", UniversityPosition.values().length);

        return choice > 0 ? UniversityPosition.values()[choice - 1] : UniversityPosition.ASSISTANT_PROFESSOR;
    }

    private ScientificDegree getScientificDegree() {
        int[] count = new int[1];
        count[0] = 0;
        Arrays.stream(ScientificDegree.values()).toList().forEach(scientificDegree -> {
            System.out.println(count[0] + 1 + ". " + scientificDegree);
            count[0]++;
        });

        int choice = getValidInt("degree", ScientificDegree.values().length);

        return choice > 0 ? ScientificDegree.values()[choice - 1] : ScientificDegree.NONE;
    }

    private void updateTeacher() {
        listStaff();
        String idInput = getValidString("Staff ID snippet to update");

        staffService.findAll().values().stream()
                .filter(s -> s.getStaffId().toString().contains(idInput))
                .findFirst()
                .ifPresentOrElse(staff -> {
                    if (staff instanceof Teacher teacher) {
                        updateTeacherFields(teacher);
                    } else {
                        System.out.println("This staff member is not a teacher.");
                    }
                }, () -> System.out.println("Staff member not found"));
    }

    private void updateTeacherFields(Teacher teacher) {
        String[] updateOptions = {
                "1. Update Weekly Hours",
                "2. Update Hourly Rate",
                "3. Change Position",
                "4. Change Department",
                "0. Cancel"
        };

        while (true) {
            System.out.println("\nUpdating: " + teacher.getFullName());
            for (String opt : updateOptions) System.out.println(opt);

            int choice = getValidInt("option", 4);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> teacher.setWeeklyHours(getValidDouble("new hours"));
                case 2 -> teacher.setHourlyRate(getValidDouble("new rate"));
                case 3 -> teacher.setUniversityPosition(getUniversityPosition());
                case 4 -> assignDepartment(teacher);
            }

            staffService.save(teacher);
            System.out.println("Field updated successfully.");
        }
    }

    private void deleteTeacher() {
        listStaff();
        String idInput = getValidString("Staff ID to delete");

        staffService.findAll().values().stream()
                .filter(s -> s.getStaffId().toString().contains(idInput))
                .findFirst()
                .ifPresentOrElse(staff -> {
                    staffService.delete(staff);
                    System.out.println("Staff deleted successfully");
                }, () -> System.out.println("Staff not found"));
    }

    private void assignDepartment(Teacher teacher) {
        departmentService.getAllDepartments().forEach(d ->
                System.out.println(d.getCode() + ": " + d.getName()));

        String code = getValidString("department code");
        try {
            Department dept = departmentService.getByCode(code);
            teacher.setDepartment(dept);
        } catch (Exception e) {
            System.out.println("Department not found, teacher created without department reference");
        }
    }

    private void listStaff() {
        System.out.println("\n--- Current Staff List ---");
        staffService.findAll().values().forEach(System.out::println);
    }
}