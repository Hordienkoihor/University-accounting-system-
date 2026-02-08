package ui;

import Utilitys.Validator;
import domain.*;
import domain.enums.ScientificDegree;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.enums.UniversityPosition;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;
import repository.*;
import repository.interfaces.*;
import service.*;
import service.interfaces.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class View {
    private static UniversityServiceInt universityService;
    private static FacultyServiceInt facultyService;
    private static SpecialityServiceInt specialityService;
    private static GroupServiceInt groupService;
    private static StudentServiceInt studentService;
    private static StaffServiceInt staffService;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UniversityRepositoryInt universityRepository = new UniversityRepository();
        universityService = new UniversityService(universityRepository);

        FacultyRepositoryInt facultyRepository = new FacultyRepository(universityService);
        facultyService = new FacultyService(facultyRepository);

        SpecialityRepositoryInt specialityRepository = new SpecialityRepository(facultyService);
        specialityService = new SpecialityService(specialityRepository);

        GroupRepositoryInt groupRepository = new GroupRepository(specialityService);
        groupService = new GroupService(groupRepository, specialityRepository);

        StudentRepositoryInt studentRepository = new StudentRepository(universityService);
        studentService = new StudentService(studentRepository, groupService);

        StaffRepositoryInt staffRepository = new StaffRepository(universityService);
        staffService = new StaffService(staffRepository, facultyService);

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

    private static void manageStudentsMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage Students Menu --");
            System.out.println("1. Add students");
            System.out.println("2. Edit student");
            System.out.println("3. Link student to the group");
            System.out.println("4. Unlink student from the group");
            System.out.println("5. Transfer student to another group");
            System.out.println("6. exit");


            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    Student student = createStudentObject();
                    studentService.save(student);
                    break;
                }
                case "2": {
                    System.out.print("Enter student ID to edit: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    Student student = studentService.findById(id);
                    if (student != null) {
                        manageStudentProperties(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                }
                case "3": {
                    System.out.print("Enter student ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter group name: ");
                    String groupName = scanner.nextLine();

                    try {
                        studentService.registerToGroup(studentService.findById(id), groupName);
                        System.out.println("linked to group: " + groupName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.print("Enter student ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter group name: ");
                    String groupName = scanner.nextLine();

                    try {
                        studentService.unregisterFromGroup(studentService.findById(id), groupName);
                        System.out.println("unlinked from group: " + groupName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "5": {
                    System.out.print("Enter student ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter old group name: ");
                    String oldGroupName = scanner.nextLine();

                    System.out.print("Enter new group name: ");
                    String newGroupName = scanner.nextLine();

                    try {
                        studentService.transfer(studentService.findById(id), oldGroupName, newGroupName);
                        System.out.println("transfered from " + oldGroupName + " to group: " + newGroupName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
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

    private static Student createStudentObject() {
        System.out.println("\n    -- Creating New Student --");

        String name = getValidString("First Name");
        String surname = getValidString("Surname");
        String fatherName = getValidString("Father Name");

        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine());

        String email = getValidString("Email");
        String phone = getValidString("Phone Number");

        Date dob = new Date();

        System.out.println("Select Study Form: 1. TUITION_FREE, 2. TUITION");
        StudyForm form = scanner.nextLine().equals("1") ? StudyForm.TUITION_FREE : StudyForm.TUITION;

        System.out.println("Select Study Status: 1. STUDYING, 2. EXPELLED, 3. ACADEMIC_LEAVE, 4. PENDING");
        String statusChoice = scanner.nextLine();
        StudyStatus status = null;

        switch (statusChoice) {
            case "1": {
                status = StudyStatus.STUDYING;
                break;
            }
            case "2": {
                status = StudyStatus.EXPELLED;
                break;
            }
            case "3": {
                status = StudyStatus.ACADEMIC_LEAVE;
                break;
            }
            case "4": {
                status = StudyStatus.PENDING;
                break;
            }
            default: {
                System.out.println("Invalid option");
                status = StudyStatus.PENDING;
                break;
            }
        }

        System.out.print("Enter course (1-5): ");
        int course = Integer.parseInt(scanner.nextLine());

        return new Student(name, surname, fatherName, age, email, phone, dob, course, form, status);
    }

    private static void manageStudentProperties(Student student) {
        boolean running = true;
        while (running) {
            System.out.println("\n    -- Editing Student: " + student.getFullName() + " --");
            System.out.println("1. Change Course");
            System.out.println("2. Change Status");
            System.out.println("3. Back");

            switch (scanner.nextLine()) {
                case "1":
                    System.out.print("Enter new course (1-5): ");
                    student.setCourse(Integer.parseInt(scanner.nextLine()));
                    break;
                case "2":
                    System.out.println("Select Study Status: 1. STUDYING, 2. EXPELLED, 3. ACADEMIC_LEAVE, 4. PENDING");
                    String statusChoice = scanner.nextLine();
                    StudyStatus status = null;

                    switch (statusChoice) {
                        case "1": {
                            status = StudyStatus.STUDYING;
                            break;
                        }
                        case "2": {
                            status = StudyStatus.EXPELLED;
                            break;
                        }
                        case "3": {
                            status = StudyStatus.ACADEMIC_LEAVE;
                            break;
                        }
                        case "4": {
                            status = StudyStatus.PENDING;
                            break;
                        }
                        default: {
                            System.out.println("Invalid option");
                            status = StudyStatus.PENDING;
                            break;
                        }
                    }
                    student.setStudyStatus(status);
                    break;
                case "3": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void manageStaffMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage Staff Menu --");
            System.out.println("1. Register Staff member");
            System.out.println("2. Display all Staff");
            System.out.println("3. Transfer Staff to another Faculty");
            System.out.println("4. Remove Staff member");
            System.out.println("5. Link Staff to faculty");
            System.out.println("6. Un;ink Staff from faculty");
            System.out.println("7. Exit");
            System.out.print("Input: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    registerStaff();
                    break;
                }
                case "2": {
                    staffService.findAll().values().forEach(System.out::println);
                    break;
                }
                case "3": {
                    System.out.print("Enter Staff ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("From Faculty Code: ");
                    String from = scanner.nextLine();
                    System.out.print("To Faculty Code: ");
                    String to = scanner.nextLine();

                    try {
                        staffService.transfer(staffService.findById(id), from, to);
                        System.out.println("Transfer successful!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.print("Enter Staff ID to remove: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    staffService.delete(id);
                    System.out.println("Staff member removed from system.");
                    break;
                }
                case "5": {
                    System.out.print("Enter Staff ID to link: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter faculty code: ");
                    String facultyCode = scanner.nextLine();

                    staffService.registerToFaculty(staffService.findById(id), facultyCode);
                    break;
                }
                case "6": {
                    System.out.print("Enter Staff ID to unlink: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter faculty code: ");
                    String facultyCode = scanner.nextLine();

                    staffService.unregisterFromFaculty(staffService.findById(id), facultyCode);
                    break;
                }
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void registerStaff() {
        System.out.println("\n    -- Registering New Teacher --");

        String name = getValidString("First Name");
        String surname = getValidString("Surname");
        String fatherName = getValidString("Father Name");

        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine());

        String email = getValidString("Email");
        String phone = getValidString("Phone Number");
        Date dob = new Date();

        UniversityPosition pos = selectPosition();
        ScientificDegree degree = selectDegree();

        System.out.print("Enter weekly hours: ");
        double hours = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter hourly rate: ");
        double rate = Double.parseDouble(scanner.nextLine());

        try {
            Teacher teacher = new Teacher(name, surname, fatherName, age, email, phone, dob, pos, degree, hours, rate);
            staffService.save(teacher);

            System.out.println("Do you want to link teacher to faculty? (y/n)");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                System.out.print("Enter Faculty Code to link this teacher: ");
                String facultyCode = scanner.nextLine();

                staffService.registerToFaculty(teacher, facultyCode);
                System.out.println("Faculty link successful");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static UniversityPosition selectPosition() {
        System.out.println("\nSelect University Position:");
        UniversityPosition[] positions = UniversityPosition.values();

        for (int i = 0; i < positions.length; i++) {
            System.out.println((i + 1) + ". " + positions[i].getDisplayName());
        }

        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        return (choice >= 0 && choice < positions.length) ? positions[choice] : UniversityPosition.LECTURER;
    }

    private static ScientificDegree selectDegree() {
        System.out.println("\nSelect Scientific Degree:");
        ScientificDegree[] degrees = ScientificDegree.values();

        for (int i = 0; i < degrees.length; i++) {
            System.out.println((i + 1) + ". " + degrees[i].getDisplayName());
        }

        int choice = Integer.parseInt(scanner.nextLine()) - 1;
        return (choice >= 0 && choice < degrees.length) ? degrees[choice] : ScientificDegree.NONE;
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
            System.out.println("4. Manage Groups");
            System.out.println("5. Exit");

            switch (scanner.nextLine()) {
                case "1": {
                    manageFacultyPropertiesMenu(faculty);
                    break;
                }
                case "2": {
                    specialityService.register(faculty.getCode(), createSpecialty(faculty));
                    break;
                }
                case "3": {
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
                }
                case "4": {
                    manageGroupsMenu(faculty);
                    break;
                }
                case "5": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }


    }

    private static void manageGroupsMenu(Faculty faculty) {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage Groups in " + faculty.getName() + " --");
            System.out.println("1. Add Group to Specialty");
            System.out.println("2. Edit/Remove Group");
            System.out.println("3. Display all groups in Faculty");
            System.out.println("4. Exit");

            switch (scanner.nextLine()) {
                case "1": {
                    addGroupToSpecialty(faculty);
                    break;
                }
                case "2": {
                    System.out.print("Enter Group Name to manage: ");

                    String name = scanner.nextLine();
                    manageGroupPropertiesMenu(groupService.findByName(name));

                    break;
                }
                case "3": {
                    specialityService.findAllOnFaculty(faculty.getCode()).forEach(s -> {
                        System.out.println("Specialty: " + s.getName());
                        groupService.findAllBySpecialty(s.getTag()).forEach(System.out::println);
                    });

                    break;
                }
                default:
                    running = false;
                    break;
            }
        }
    }

    private static void addGroupToSpecialty(Faculty faculty) {
        System.out.println("Available specialties:");

        List<Specialty> s = specialityService.findAllOnFaculty(faculty.getCode());

        if (s.isEmpty()) {
            System.out.println("no specialities found");
            return;
        }

        s.forEach(System.out::println);

        String specTag = getValidString("Specialty Tag");
        String groupName = getValidString("Group Name");

        try {
            groupService.registerGroup(specTag, groupName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void manageGroupPropertiesMenu(Group group) {
        if (group == null) {
            System.out.println("Group not found");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n    -- Managing Group: " + group.getName() + " --");
            System.out.println("1. Change Name");
            System.out.println("2. Remove Group");
            System.out.println("3. Back");

            switch (scanner.nextLine()) {
                case "1": {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    try {
                        groupService.updateName(group.getName(), newName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }
                case "2": {
                    System.out.print("Are you sure? (y/n): ");

                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        groupService.deleteByName(group.getName());
                        System.out.println("Group deleted");
                        running = false;
                    }
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
