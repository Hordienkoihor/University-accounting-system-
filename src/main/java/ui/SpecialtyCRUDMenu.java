package ui;

import Utilitys.InputHandler;
import domain.Department;
import domain.Specialty;
import service.interfaces.DepartmentServiceInt;
import service.interfaces.SpecialityServiceInt;

import java.util.Scanner;

public class SpecialtyCRUDMenu {
    private final SpecialityServiceInt specialityService;
    private final DepartmentServiceInt departmentService;
    private final InputHandler inputHandler;

    public SpecialtyCRUDMenu(SpecialityServiceInt specialityService, DepartmentServiceInt departmentService) {
        this.specialityService = specialityService;
        this.departmentService = departmentService;

        this.inputHandler = new InputHandler(new Scanner(System.in));
    }

    public void handleSpecialtyCRUD() {
        String[] options = {
                "1. Create Specialty",
                "2. View All Specialties",
                "3. Find Specialty by Tag",
                "4. Update Specialty",
                "5. Delete Specialty",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Specialty Management ---");
            for (String opt : options) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("action", 5);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createSpecialty();
                case 2 -> listSpecialties();
                case 3 -> findByTag();
                case 4 -> updateSpecialty();
                case 5 -> deleteSpecialty();
            }
        }
    }

    private void createSpecialty() {
        System.out.println("--- New Specialty Registration ---");
        try {
            String name = this.inputHandler.getValidString("specialty name");
            String tag = this.inputHandler.getValidString("specialty tag");

            Department dept = selectDepartment();
            if (dept == null) {
                System.out.println("Operation cancelled: Department is required");
                return;
            }

            Specialty specialty = new Specialty(name, tag, dept);
            specialityService.register(specialty);
            System.out.println("Specialty '" + name + "' registered successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateSpecialty() {
        String tag = this.inputHandler.getValidString("tag of the specialty to update");
        if (specialityService.existsByTag(tag)) {
            String newName = this.inputHandler.getValidString("new name");
            specialityService.update(newName, tag);
            System.out.println("Specialty updated");
        } else {
            System.out.println("Specialty with tag " + tag + " not found");
        }
    }

    private void findByTag() {
        String tag = this.inputHandler.getValidString("tag");
        try {
            Specialty s = specialityService.findByTag(tag);
            System.out.println(s);
        } catch (Exception e) {
            System.out.println("Not found");
        }
    }

    private void deleteSpecialty() {
        String tag = this.inputHandler.getValidString("tag to delete");
        specialityService.removeByTag(tag);
        System.out.println("Specialty removed (if it existed)");
    }

    private void listSpecialties() {
        System.out.println("\n--- Current Specialties ---");
        specialityService.getAllSpecialties().forEach(System.out::println);
    }

    private Department selectDepartment() {
        var departments = departmentService.getAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("No departments found in the system");
            return null;
        }

        System.out.println("Available Departments:");
        departments.forEach(d -> System.out.println(d.getCode() + " : " + d.getName()));

        while (true) {
            String code = this.inputHandler.getValidString("department code (q to cancel)");
            if (code.equalsIgnoreCase("q")) return null;

            try {
                return departmentService.getByCode(code);
            } catch (Exception e) {
                System.out.println("Invalid code try again");
            }
        }
    }
}