package ui;

import Utilitys.Validator;
import domain.Faculty;
import domain.Specialty;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;
import repository.FacultyRepository;
import repository.SpecialityRepository;
import repository.UniversityRepository;
import repository.interfaces.FacultyRepositoryInt;
import repository.interfaces.SpecialityRepositoryInt;
import repository.interfaces.UniversityRepositoryInt;
import service.FacultyService;
import service.SpecialityService;
import service.UniversityService;
import service.interfaces.FacultyServiceInt;
import service.interfaces.SpecialityServiceInt;
import service.interfaces.UniversityServiceInt;

import java.util.Scanner;

public class View {
    private static UniversityServiceInt universityService;
    private static FacultyServiceInt facultyService;
    private static SpecialityServiceInt specialityService;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UniversityRepositoryInt universityRepository = new UniversityRepository();
        universityService = new UniversityService(universityRepository);

        FacultyRepositoryInt facultyRepository = new FacultyRepository(universityService);
        facultyService = new FacultyService(facultyRepository);

        SpecialityRepositoryInt specialityRepository = new SpecialityRepository(facultyService);
        specialityService = new SpecialityService(specialityRepository);

        boot();
    }

    private static void boot() {
        System.out.println();
        System.out.println("    -- Welcome to the University Management System --");
        System.out.println("1. Create University");
        System.out.println("2. Load University\n");
        System.out.println("3. Exit ");
        System.out.println("Input: ");

        String choice = scanner.nextLine();
        System.out.println();

        if (choice.equals("3")) {
            System.exit(0);
        } else if (choice.equals("2")) {
            System.out.print("Please enter path to the config: ");
            String path = scanner.nextLine();

            universityService.loadUniversity(path);

            if (!universityService.isUniversityLoaded()) {
                System.out.println("Помилка: Університет не завантажено. Повернення до стартового меню...");
                boot();
                return;
            }

        } else {
            createUniversity();
        }

        while (true) {
            mainMenu();
        }
    }

    private static void createUniversity() {
        System.out.println("Enter University name: ");
        String full = scanner.nextLine();

        System.out.println("Enter Short Name: ");
        String shortName = scanner.nextLine();

        System.out.println("Enter City: ");
        String city = scanner.nextLine();

        System.out.println("Enter Address: ");
        String address = scanner.nextLine();

        universityService.createUniversity(full, shortName, city, address);
        System.out.println("Університет створено!");
        System.out.println();
    }


    private static void mainMenu() {
        System.out.println();
        System.out.println("    -- Managing " + universityService.getUniversity().getFullName() + " --");
        System.out.println("1. display");
        System.out.println("2. manage");
        System.out.println("3. exit");
        System.out.print("Input: ");

        switch (scanner.nextLine()) {
            case "1":
                displayUniMenu();
                break;
            case "2":
                manageUniMenu();
                break;
            case "3":
                System.exit(0);
                return;
            default:
                break;
        }
    }

    private static void displayUniMenu() {
        System.out.println();
        boolean running = true;
        while (running) {
            System.out.println("    -- Display UniMenu --");
            System.out.println("1. Display University");
            System.out.println("2. Display Faculties");
            System.out.println("3. Display specialities");
            System.out.println("4. Display Students");
            System.out.println("5. Display Staff");
            System.out.println("6. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    System.out.println(universityService.getUniversity());
                    break;
                }
                case "2": {
                    facultyService.getAllAsList().forEach(System.out::println);
                    break;
                }
                case "3": {
                    facultyService.getAllAsList().
                            forEach(f -> specialityService.findAllOnFaculty(f.getCode())
                                    .forEach(System.out::println));
                }
                case "4": {
                    universityService.getUniversity().getStudentsAsList().forEach(System.out::println);
                    break;
                }
                case "5": {
                    universityService.getUniversity().getStaffAsList().forEach(System.out::println);
                    break;
                }
                case "6": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

    }

    private static void manageUniMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage UniMenu --");
            System.out.println("1. Manage University Properties");
            System.out.println("2. Manage Faculties");
            System.out.println("3. Manage Students");
            System.out.println("4. Manage Staff");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manageUniPropertiesMenu();
                    break;
                case "2":
                    manageFacultiesMenu();
                    break;
                case "3":
                    manageStudentsMenu();
                    break;
                case "4":
                    manageStaffMenu();
                    break;
                default:
                    running = false;
                    break;
            }
        }

    }

    // todo
    private static void manageStudentsMenu() {
        System.out.println("wip");
    }

    // todo
    private static void manageStaffMenu() {
        System.out.println("wip");
    }

    private static void manageUniPropertiesMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage UniProperties Menu --");
            System.out.println("1. Manage University Full Name");
            System.out.println("2. Manage University ShortName");
            System.out.println("3. Manage University City");
            System.out.println("4. Manage University Address");
            System.out.println("5. exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Please enter new University full name: ");
                    universityService.getUniversity().setFullName(scanner.nextLine());
                    break;
                case "2":
                    System.out.println("Please enter new University short name: ");
                    universityService.getUniversity().setShortName(scanner.nextLine());
                    break;
                case "3":
                    System.out.println("Please enter new University city: ");
                    universityService.getUniversity().setCity(scanner.nextLine());
                    break;
                case "4":
                    System.out.println("Please enter new University address: ");
                    universityService.getUniversity().setAddress(scanner.nextLine());
                    break;
                default:
                    running = false;
                    break;
            }
        }


    }

    private static void manageFacultiesMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage Faculties Menu --");
            System.out.println("1. Add Faculty");
            System.out.println("2. Edit Faculty");
            System.out.println("3. exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println();
                    facultyService.register(createFaculty());
                    break;
                case "2":
                    System.out.println();
                    universityService.getUniversity().getFacultyList().forEach(System.out::println);
                    System.out.println();

                    System.out.println("Please enter chosen faculty code");
                    System.out.println();

                    try {
                        manageFacultyMenu(facultyService.findByCode(scanner.nextLine()));
                    } catch (Exception e) {
                        System.out.println("please enter a valid tag");
                    }


                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }


    }

    private static void manageFacultyMenu(Faculty faculty) {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage Faculty --");
            System.out.println("1.Manage Faculty properties");
            System.out.println("2. Add Speciality");
            System.out.println("3. Edit Speciality");
            System.out.println("4. Exit");

            switch (scanner.nextLine()) {
                case "1":
                    manageFacultyPropertiesMenu(faculty);
                    break;
                case "2":
                    specialityService.register(faculty.getCode(), createSpecialty(faculty));
                    break;
                case "3":
                    System.out.println();
                    faculty.getSpecialtyList().forEach(System.out::println);
                    System.out.println();

                    System.out.println("Please enter chosen speciality tag");
                    System.out.println();

                    try {
                        manageSpecialtyMenu(specialityService.findByTag(getValidString("SpecialityTag")));
                    } catch (Exception e) {
                        System.out.println("please enter a valid tag");
                    }
                    break;
                default:
                    running = false;
                    break;
            }
        }


    }

    private static void manageFacultyPropertiesMenu(Faculty faculty) {
        boolean running = true;
        while (running) {
            System.out.println("\n    -- Manage Faculty: " + faculty.getName() + " --");
            System.out.println("1. Change Name");
            System.out.println("2. Back");

            switch (scanner.nextLine()) {
                case "1":
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    facultyService.update(faculty.getCode(), newName);
                    break;
                case "2":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static Specialty createSpecialty(Faculty faculty) {
        try {
            Specialty res = new Specialty(getValidString("Speciality Name"), getValidString("Speciality Tag"), faculty);
            return res;
        } catch (IllegalNameException | IllegalCodeException e) {
            System.out.println("Please provide valid information.");
            return createSpecialty(faculty);
        }
    }


    private static void manageSpecialtyMenu(Specialty specialty) {
        if (specialty == null) {
            System.out.println("Error: Specialty not found.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n    -- Managing Specialty: [" + specialty.getTag() + "] " + specialty.getName() + " --");
            System.out.println("1. Change Name");
            System.out.println("2. Remove Specialty");
            System.out.println("3. Back");
            System.out.print("Input: ");

            switch (scanner.nextLine()) {
                case "1":
                    System.out.print("Enter new name for specialty: ");
                    String newName = scanner.nextLine();
                    specialityService.update(newName, specialty.getTag());
                    break;
                case "2":
                    System.out.print("Are you sure you want to delete this specialty? (y/n): ");

                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        specialityService.removeByTag(specialty.getTag());
                        running = false;
                    }
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static Faculty createFaculty() {
        try {
            Faculty res = new Faculty(getValidString("Faculty Name"), getValidString("Faculty Code"));
            return res;
        } catch (IllegalNameException | IllegalCodeException e) {
            System.out.println("Please provide valid information.");
            return createFaculty();
        }

    }

    private static String getValidString(String input) {
        System.out.println("Please enter " + input + ": ");
        String res = scanner.nextLine();
        while (true) {

            if (Validator.isValidString(res)) {
                return res;
            } else {
                System.out.println("Please enter valid " + input + ": ");
                res = scanner.nextLine();
            }
        }

    }


}
