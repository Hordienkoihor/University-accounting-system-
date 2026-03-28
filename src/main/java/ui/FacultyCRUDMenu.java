package ui;

import Utilitys.InputHandler;
import domain.Faculty;
import domain.Teacher;
import domain.abstractClasses.Staff;
import domain.enums.ScientificDegree;
import domain.enums.UniversityPosition;
import domain.records.StaffId;
import service.StaffService;
import service.interfaces.FacultyServiceInt;
import service.interfaces.StaffServiceInt;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class FacultyCRUDMenu {
    private final FacultyServiceInt facultyService;
    private final StaffServiceInt staffService;

    private final InputHandler inputHandler;

    public FacultyCRUDMenu(FacultyServiceInt facultyService, StaffServiceInt staffService) {
        this.facultyService = facultyService;
        this.staffService = staffService;

        this.inputHandler = new InputHandler(new Scanner(System.in));
    }

    public void handleFacultyCRUD() {
        String[] options = {
                "1. Create Faculty",
                "2. View All Faculties",
                "3. Update Faculty",
                "4. Delete Faculty",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Faculty Management ---");
            for (String opt : options) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("action", 4);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createFaculty();
                case 2 -> listFaculties();
                case 3 -> updateFaculty();
                case 4 -> deleteFaculty();
            }
        }
    }

    private void createFaculty() {
        System.out.println("--- New Faculty Registration ---");
        try {
            String name = this.inputHandler.getValidString("name");
            String code = this.inputHandler.getValidString("code");

           Faculty faculty = new Faculty(name, code);

            assignDean(faculty);

            facultyService.register(faculty);
            System.out.println("Faculty created. Code: " + faculty.getCode());
        } catch (Exception e) {
            System.out.println("Error creating faculty: " + e.getMessage());
        }
    }

    private void assignDean(Faculty faculty) {
        staffService.findAll().values().forEach(staff -> {
            System.out.println(staff.getName() + " id: " + staff.getStaffId() );
        });

        while (true) {
            String input = this.inputHandler.getValidString("staffId (q to quit)");

            if (input.equals("q")) {
                break;
            }

            Staff teacher = staffService.findById(new StaffId(input));

            if (teacher != null) {
                System.out.println("Staff  found");
                faculty.setDean(teacher);
                break;
            }

            System.out.println("Staff not found");
        }
    }



    private void updateFaculty() {
        listFaculties();
        String idInput = this.inputHandler.getValidString("Faculty code snippet to update");

        facultyService.getAllAsList().stream()
                .filter(f -> f.getCode().contains(idInput))
                .findFirst()
                .ifPresentOrElse(faculty -> {
                    updateFacultyFields(faculty);
                }, () -> System.out.println("Faculty not found"));
    }

    private void updateFacultyFields(Faculty faculty) {
        String[] updateOptions = {
                "1. Update Name",
                "2. Set Dean",
                "0. Cancel"
        };

        while (true) {
            System.out.println("\nUpdating: " + faculty.getName());
            for (String opt : updateOptions) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("option", 2);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> faculty.setName(this.inputHandler.getValidString("new name"));
                case 2 -> handleSetDean(faculty);
            }

            System.out.println("Field updated successfully");
        }
    }

    private void handleSetDean(Faculty faculty) {
        staffService.findAll().values().forEach(staff -> {
            System.out.println(staff.getName() + " id: " + staff.getStaffId() );
        });

        Staff teacher = staffService.findById(new StaffId(this.inputHandler.getValidString("staffId")));

        if (teacher == null) {
            System.out.println("Staff not found");
            return;
        }

        faculty.setDean(teacher);
        System.out.println("Faculty dean updated successfully");
    }

    private void deleteFaculty() {
        listFaculties();
        String idInput = this.inputHandler.getValidString("Faculty code to delete");

        try {
            facultyService.deleteByCode(idInput);
            System.out.println("Faculty deleted successfully");
        } catch (Exception e) {
            System.out.println("Faculty not found");
        }
    }

    private void listFaculties() {
        System.out.println("\n--- Current Faculties List ---");
        facultyService.getAllAsList().forEach(System.out::println);
    }
}
