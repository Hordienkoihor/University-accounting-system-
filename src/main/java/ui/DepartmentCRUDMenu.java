package ui;

import Utilitys.InputHandler;
import domain.Department;
import domain.Faculty;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import service.DepartmentService;
import service.interfaces.DepartmentServiceInt;
import service.interfaces.FacultyServiceInt;
import service.interfaces.StaffServiceInt;

import java.util.Scanner;

public class DepartmentCRUDMenu {
    private final DepartmentServiceInt departmentService;
    private final FacultyServiceInt facultyService;
    private final StaffServiceInt staffService;

    private final InputHandler inputHandler;

    public DepartmentCRUDMenu(DepartmentServiceInt departmentService, FacultyServiceInt facultyService, StaffServiceInt staffService) {
        this.departmentService = departmentService;
        this.facultyService = facultyService;
        this.staffService = staffService;

        this.inputHandler = new InputHandler(new Scanner(System.in));
    }


    public void handleDepartmentCRUD() {
        String[] options = {
                "1. Create Department",
                "2. View All Departments",
                "3. Update Department",
                "4. Delete Department",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Department Management ---");
            for (String opt : options) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("action", 4);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createDepartment();
                case 2 -> listDepartments();
                case 3 -> updateDepartment();
                case 4 -> deleteDepartment();
            }
        }
    }

    private void createDepartment() {
        System.out.println("--- New Department Registration ---");
        try {
            String name = this.inputHandler.getValidString("name");
            String code = this.inputHandler.getValidString("code");
            String location = this.inputHandler.getValidString("location");

            Faculty faculty = handleFacultyInput();
            if (faculty == null) {
                System.out.println("Department creation cancelled: Faculty is required");
                return;
            }

            Department department = new Department(name, code, faculty, null, location);

            assignHead(department);

            departmentService.register(department);
            System.out.println("Department created successfully Code: " + department.getCode());
        } catch (Exception e) {
            System.out.println("Error creating department: " + e.getMessage());
        }
    }

    private Faculty handleFacultyInput() {
        System.out.println("--- Select Faculty ---");
        var faculties = facultyService.getAllAsList();
        if (faculties.isEmpty()) {
            System.out.println("No faculties available. Please create a faculty first");
            return null;
        }

        faculties.forEach(f -> System.out.println(f.getCode() + " : " + f.getName()));

        while (true) {
            String code = this.inputHandler.getValidString("faculty code (or 'q' to cancel)");
            if (code.equalsIgnoreCase("q")) return null;

            return faculties.stream()
                    .filter(f -> f.getCode().equalsIgnoreCase(code))
                    .findFirst()
                    .orElseGet(() -> {
                        System.out.println("Faculty with this code not found");
                        return null;
                    });
        }
    }

    private void assignHead(Department department) {
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
                department.setHeadOfDepartment(teacher);
                break;
            }

            System.out.println("Staff not found");
        }
    }



    private void updateDepartment() {
        listDepartments();
        String idInput = this.inputHandler.getValidString("Department code snippet to update");

        departmentService.getAllDepartments().stream()
                .filter(d -> d.getCode().contains(idInput))
                .findFirst()
                .ifPresentOrElse(department -> {
                    updateDepartmentFields(department);
                }, () -> System.out.println("Department not found"));
    }

    private void updateDepartmentFields(Department department) {
        String[] updateOptions = {
                "1. Update Name",
                "2. Set Dean",
                "0. Cancel"
        };

        while (true) {
            System.out.println("\nUpdating: " + department.getName());
            for (String opt : updateOptions) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("option", 2);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> department.setName(this.inputHandler.getValidString("new name"));
                case 2 -> handleSetHead(department);
            }

            System.out.println("Field updated successfully");
        }
    }

    private void handleSetHead(Department department) {
        staffService.findAll().values().forEach(staff -> {
            System.out.println(staff.getName() + " id: " + staff.getStaffId() );
        });

        Staff teacher = staffService.findById(new StaffId(this.inputHandler.getValidString("staffId")));

        if (teacher == null) {
            System.out.println("Staff not found");
            return;
        }

        department.setHeadOfDepartment(teacher);
        System.out.println("Department head updated successfully");
    }

    private void deleteDepartment() {
        listDepartments();
        String idInput = this.inputHandler.getValidString("Department code to delete");

        try {
            departmentService.deleteDepartment(idInput);
            System.out.println("Department deleted successfully");
        } catch (Exception e) {
            System.out.println("Department not found");
        }
    }

    private void listDepartments() {
        System.out.println("\n--- Current Departments List ---");
        departmentService.getAllDepartments().forEach(d -> System.out.println(d.getName() + " : " + d.getCode()));
    }
}
