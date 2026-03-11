package ui;

import Utilitys.Validator;
import domain.*;
import domain.abstractClasses.Staff;
import domain.enums.ScientificDegree;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.enums.UniversityPosition;
import domain.records.StaffId;
import domain.records.StudentId;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;
import repository.*;
import repository.interfaces.*;
import service.*;
import service.interfaces.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class View {
    private static final Scanner scanner = new Scanner(System.in);
    private static UniversityServiceInt universityService;
    private static FacultyServiceInt facultyService;
    private static SpecialityServiceInt specialityService;
    private static GroupServiceInt groupService;
    private static StudentServiceInt studentService;
    private static StaffServiceInt staffService;
    private static DepartmentServiceInt departmentService;

    public static void main(String[] args) {
        UniversityRepositoryInt universityRepository = new UniversityRepository();
        universityService = new UniversityService(universityRepository);

        FacultyRepositoryInt facultyRepository = new FacultyRepository(universityService);
        facultyService = new FacultyService(facultyRepository);

        SpecialityRepositoryInt specialityRepository = new SpecialityRepository(facultyService);
        specialityService = new SpecialityService(specialityRepository);

        GroupRepositoryInt groupRepository = new GroupRepository(specialityService);
        groupService = new GroupService(groupRepository, specialityService);

        StudentRepositoryInt studentRepository = new StudentRepository(universityService);
        studentService = new StudentService(studentRepository, groupService);

        StaffRepositoryInt staffRepository = new StaffRepository(universityService);
        staffService = new StaffService(staffRepository, facultyService);

        DepartmentRepositoryInt departmentRepository = new DepartmentRepository();
        departmentService = new DepartmentService(departmentRepository, facultyService);

        boot();
    }

    private static void boot() {
        System.out.println();
        System.out.println("    -- Welcome to the University Management System --");
        System.out.println("1. Create University");
        System.out.println("2. Load University\n");
        System.out.println("3. Exit ");
        System.out.println("Input: ");

        String choice = getValidString();
        System.out.println();

        if (choice.equals("3")) {
            System.exit(0);
        } else if (choice.equals("2")) {
            System.out.print("Please enter path to the config: ");
            String path = getValidString();

            universityService.loadUniversity(path);

            if (!universityService.isUniversityLoaded()) {
                System.out.println("Помилка: Університет не завантажено. Повернення до стартового меню");
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
        String full = getValidString();

        System.out.println("Enter Short Name: ");
        String shortName = getValidString();

        System.out.println("Enter City: ");
        String city = getValidString();

        System.out.println("Enter Address: ");
        String address = getValidString();

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

        switch (getValidString()) {
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

            String choice = getValidString();

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
                    break;
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
            System.out.println("3. Manage Departments");
            System.out.println("4. Manage Students");
            System.out.println("5. Manage Staff");
            System.out.println("5. Exit");

            String choice = getValidString();

            switch (choice) {
                case "1":
                    manageUniPropertiesMenu();
                    break;
                case "2":
                    manageFacultiesMenu();
                    break;
                case "3":
                    manageDepartmentsMenu();
                    break;
                case "4":
                    manageStudentsMenu();
                    break;
                case "5":
                    manageStaffMenu();
                    break;
                default:
                    running = false;
                    break;
            }
        }

    }

    private static void manageDepartmentsMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println("    -- Manage Departments Menu --");
            System.out.println("1. Add Department to Faculty");
            System.out.println("2. Display All Departments");
            System.out.println("3. Delete Department");
            System.out.println("4. Edit Department");
            System.out.println("5. Exit");

            switch (getValidString()) {
                case "1":
                    registerDepartment();
                    break;
                case "2":
                    departmentService.getAllDepartments().forEach(System.out::println);
                    break;
                case "3": {
                    System.out.print("Enter Department Code to remove: ");
                    String code = getValidString();
                    try {
                        departmentService.deleteDepartment(code);
                        System.out.println("Department removed");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.println("\n    -- Update Department --");

                    departmentService.getAllDepartments().forEach(d ->
                            System.out.println("[" + d.getCode() + "] " + d.getName()));

                    editDepartment();
                    break;

                }
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void registerDepartment() {
        System.out.println("\n    -- Registering New Department --");

        String name = getValidString("Department Name");
        String code = getValidString("Department Code");
        String location = getValidString("Location (Room)");

        System.out.println("Available Faculties:");
        facultyService.getAllAsList().forEach(f -> System.out.println(f.getCode() + " - " + f.getName()));

        String facultyCode = getValidString("Faculty Code");
        Optional<Faculty> faculty = facultyService.findByCode(facultyCode);

        if (faculty.isEmpty()) {
            System.out.println("Error: Faculty not found");
            return;
        }

        staffService.findAll().values().forEach(staff -> System.out.println(staff.getStaffId() + " - " + staff.getFullName()));
        StaffId id = new StaffId(getValidString("Staff Id"));

        Staff staff = staffService.findById(id);

        if (staff == null) {
            System.out.println("Error: staff not found");
            return;
        }

        Staff head = staff;

        try {
            Department dept = new Department(name, code, faculty.get(), head, location);
            departmentService.createDepartment(dept);
            System.out.println("Department created successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editDepartment() {
        System.out.print("Enter Department Code: ");
        String code = getValidString();

        Department dept;
        try {
            dept = departmentService.getByCode(code);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        boolean editing = true;
        while (editing) {
            System.out.println("\n    -- Editing Department: " + dept.getName() + " --");
            System.out.println("1. Change Name");
            System.out.println("2. Change Location");
            System.out.println("3. Assign Head of Department");
            System.out.println("4. Back & Save");

            switch (getValidString()) {
                case "1":
                    dept.setName(getValidString("New Name"));
                    break;
                case "2":
                    dept.setLocation(getValidString("New Location"));
                    break;
                case "3":
                    assignHeadToDepartment(dept);
                    break;
                case "4":
                    departmentService.updateDepartment(dept);
                    System.out.println("Changes saved!");
                    editing = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void assignHeadToDepartment(Department dept) {
        System.out.println("Available Staff:");
        staffService.findAll().values().forEach(s ->
                System.out.println(s.getStaffId() + " - " + s.getFullName())
        );

        System.out.print("Enter Staff ID: ");
        String staffId = getValidString();

        Staff head = staffService.findById(new StaffId(staffId));
        if (head != null) {
            dept.setHeadOfDepartment(head);
            System.out.println("Head of Department assigned: " + head.getFullName());
        } else {
            System.out.println("Staff member not found");
        }
    }

    private static void manageStudentsMenu() {
        System.out.println();
        boolean running = true;

        while (running) {
            System.out.println(" \n   -- Manage Students Menu --");
            System.out.println("1. Add students");
            System.out.println("2. Edit student");
            System.out.println("3. Link student to the group");
            System.out.println("4. Unlink student from the group");
            System.out.println("5. Transfer student to another group");
            System.out.println("6. exit");


            String choice = getValidString();

            switch (choice) {
                case "1": {
                    Student student = createStudentObject();
                    studentService.save(student);
                    break;
                }
                case "2": {
                    studentService.getAllCourseOrder()
                            .stream()
                            .map(student -> student.getStudentId() + " " + student.getFullName()).forEach(System.out::println);

                    System.out.print("Enter student ID to edit: ");
                    String id = getValidString();

                    Student student = studentService.findById(new StudentId(id));
                    if (student != null) {
                        manageStudentProperties(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                }
                case "3": {
                    System.out.print("Enter student ID: ");
                    String id = getValidString();

                    System.out.print("Enter group name: ");
                    String groupName = getValidString();

                    try {
                        studentService.registerToGroup(studentService.findById(new StudentId(id)), groupName);
                        System.out.println("linked to group: " + groupName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.print("Enter student ID: ");
                    String id = getValidString();

                    System.out.print("Enter group name: ");
                    String groupName = getValidString();

                    try {
                        studentService.unregisterFromGroup(studentService.findById(new StudentId(id)), groupName);
                        System.out.println("unlinked from group: " + groupName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "5": {
                    System.out.print("Enter student ID: ");
                    String id = getValidString();

                    System.out.print("Enter old group name: ");
                    String oldGroupName = getValidString();

                    System.out.print("Enter new group name: ");
                    String newGroupName = getValidString();

                    try {
                        studentService.transfer(studentService.findById(new StudentId(id)), oldGroupName, newGroupName);
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

        Student student;

        String name = getValidString("First Name");
        String surname = getValidString("Surname");
        String fatherName = getValidString("Father Name");

        System.out.print("Enter age: ");
        int age = getValidInt();

        String email = getValidString("Email");
        String phone = getValidString("Phone Number");

        LocalDate dob;
        try {
            int[] arr = Arrays.stream(getValidString("Please enter date").split("\\.")).mapToInt(Integer::parseInt).toArray();
            dob = LocalDate.of(arr[0], arr[1], arr[2]);
        } catch (Exception e) {
            dob = LocalDate.now();
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Select Study Form: 1. TUITION_FREE, 2. TUITION");
        StudyForm form = getValidString().equals("1") ? StudyForm.TUITION_FREE : StudyForm.TUITION;

        StudyStatus status = selectStudyStatus();

        int course = 1;

        System.out.print("Enter course (1-5): ");
        try {
            course = getValidInt();
        } catch (NumberFormatException e) {
            System.out.println("Invalid option, defaulting to 1");
        }

        try {
            student = new Student(name, surname, fatherName, age, email, phone, dob, course, form, status);
            return student;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    //todo maybe transfer somewhere
    private static StudyStatus selectStudyStatus() {
        System.out.println("Select Study Status: 1. STUDYING, 2. EXPELLED, 3. ACADEMIC_LEAVE, 4. PENDING");
        String statusChoice = getValidString();

        switch (statusChoice) {
            case "1": {
                return StudyStatus.STUDYING;
            }
            case "2": {
                return StudyStatus.EXPELLED;
            }
            case "3": {
                return StudyStatus.ACADEMIC_LEAVE;
            }
            case "4": {
                return StudyStatus.PENDING;
            }
            default: {
                System.out.println("Invalid option");
                return StudyStatus.PENDING;
            }
        }
    }

    private static void manageStudentProperties(Student student) {
        boolean running = true;
        while (running) {
            System.out.println("\n    -- Editing Student: " + student.getFullName() + " --");
            System.out.println("1. Change Course");
            System.out.println("2. Change Status");
            System.out.println("3. Change phone number");
            System.out.println("4. Change email");
            System.out.println("5. Change name");
            System.out.println("6. Change age");
            System.out.println("7. Change surname");
            System.out.println("8. Change fathername");
            System.out.println("9. Back");

            switch (getValidString()) {
                case "1": {
                    System.out.print("Enter new course (1-5): ");
                    student.setCourse(getValidInt());
                    break;
                }
                case "2": {
                    StudyStatus status = selectStudyStatus();
                    student.setStudyStatus(status);
                    break;
                }
                case "3": {
                    student.setPhoneNumber(getValidString("Phone Number"));
                    break;
                }
                case "4": {
                    student.setEmail(getValidString("Email"));
                    break;
                }
                case "5": {
                    student.setName(getValidString("Name"));
                    break;
                }
                case "6": {
                    student.setAge(getValidInt()); // todo make method with message for int input
                    break;
                }
                case "7": {
                    student.setSurname(getValidString("Surname"));
                    break;
                }
                case "8": {
                    student.setFatherName(getValidString("Fathername"));
                    break;
                }
                case "9": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option");
                    break;
            }

            studentService.save(student);
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
            System.out.println("7. Edit staff");
            System.out.println("8. Exit");
            System.out.print("Input: ");

            String choice = getValidString();

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
                    String id = getValidString();

                    System.out.print("From Faculty Code: ");
                    String from = getValidString();

                    System.out.print("To Faculty Code: ");
                    String to = getValidString();

                    try {
                        staffService.transfer(staffService.findById(new StaffId(id)), from, to);
                        System.out.println("Transfer successful!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.print("Enter Staff ID to remove: ");
                    String id = getValidString();
                    staffService.delete(new StaffId(id));
                    System.out.println("Staff member removed from system.");
                    break;
                }
                case "5": {
                    System.out.print("Enter Staff ID to link: ");
                    String id = getValidString();

                    System.out.println("Enter faculty code: ");
                    String facultyCode = getValidString();

                    staffService.registerToFaculty(staffService.findById(new StaffId(id)), facultyCode);
                    break;
                }
                case "6": {
                    System.out.print("Enter Staff ID to unlink: ");
                    String id = getValidString();

                    System.out.println("Enter faculty code: ");
                    String facultyCode = getValidString();

                    staffService.unregisterFromFaculty(staffService.findById(new StaffId(id)), facultyCode);
                    break;
                }
                case "7": {
                    staffService.findAll()
                            .values()
                            .stream()
                            .map(staff -> staff.getStaffId() + " " + staff.getFullName()).forEach(System.out::println);

                    String staffId = getValidString("Please enter staff ID: ");

                    Staff staff = staffService.findById(new StaffId(staffId));
                    if (staff != null) {
                        manageStaffProperties(staff);
                    } else {
                        System.out.println("Student not found.");
                    }


                }
                case "8":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void manageStaffProperties(Staff staff) {
        Teacher teacher = (Teacher) staff;
        boolean running = true;
        while (running) {
            System.out.println("\n    -- Editing Staff: " + staff.getFullName() + " --");
            System.out.println("1. Change hourly rate");
            System.out.println("2. Change weekly hourst");
            System.out.println("3. Change phone number");
            System.out.println("4. Change email");
            System.out.println("5. Change name");
            System.out.println("6. Change age");
            System.out.println("7. Change surname");
            System.out.println("8. Change fathername");
            System.out.println("9. Change university Position");
            System.out.println("10. Change scientific Degree");
            System.out.println("11. Back");

            switch (getValidString()) {
                case "1": {
                    System.out.print("Enter new hourly rate: ");
                    teacher.setHourlyRate(getValidInt());
                    break;
                }
                case "2": {
                    System.out.println("Enter new weekly hourst: ");
                    teacher.setWeeklyHours(getValidInt());
                    break;
                }
                case "3": {
                    teacher.setPhoneNumber(getValidString("Phone Number"));
                    break;
                }
                case "4": {
                    teacher.setEmail(getValidString("Email"));
                    break;
                }
                case "5": {
                    teacher.setName(getValidString("Name"));
                    break;
                }
                case "6": {
                    teacher.setAge(getValidInt()); // todo make method with message for int input
                    break;
                }
                case "7": {
                    teacher.setSurname(getValidString("Surname"));
                    break;
                }
                case "8": {
                    teacher.setFatherName(getValidString("Fathername"));
                    break;
                }
                case "9": {
                    teacher.setUniversityPosition(selectPosition());
                    break;
                }
                case "10": {
                    teacher.setScientificDegree(selectDegree());
                    break;
                }
                case "11": {
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option");
                    break;
            }

            staffService.save(teacher);
        }
    }

//    private static UniversityPosition selectUniversityPosition() {
//        boolean running = true;
//
//        System.out.println("Enter University Position: ");
//        System.out.println("1.  ASSISTANT_PROFESSOR");
//        System.out.println("2.  ASSOCIATE_PROFESSOR");
//        System.out.println("3.  FULL_PROFESSOR");
//        System.out.println("4. DISTINGUISHED_PROFESSOR");
//        System.out.println("5. LECTURER");
//        System.out.println("6. SENIOR_LECTURER");
//        System.out.println("7. INSTRUCTOR");
//        System.out.println("8. PROFESSOR_OF_PRACTICE");
//        System.out.println("9. VISITING_PROFESSOR");
//
//        while (running) {
//            switch (getValidInt()) {
//                case 1: {
//                   return UniversityPosition.ASSISTANT_PROFESSOR;
//                }
//                case 2: {
//                    return UniversityPosition.ASSOCIATE_PROFESSOR;
//                }
//                case 3: {
//                    return UniversityPosition.FULL_PROFESSOR;
//                }
//                case 4: {
//                    return UniversityPosition.DISTINGUISHED_PROFESSOR;
//                }
//                case 5: {
//                    return UniversityPosition.LECTURER;
//                }
//                case 6: {
//                    return UniversityPosition.SENIOR_LECTURER;
//                }
//                case 7: {
//                    return UniversityPosition.INSTRUCTOR;
//                }
//                case 8: {
//                    return UniversityPosition.PROFESSOR_OF_PRACTICE;
//                }
//                case 9: {
//                    return UniversityPosition.VISITING_PROFESSOR;
//                }
//                default: {
//                    System.out.println("Invalid option");
//                }
//            }
//        }
//    }


    private static void registerStaff() {
        System.out.println("\n    -- Registering New Teacher --");

        String name = getValidString("First Name");
        String surname = getValidString("Surname");
        String fatherName = getValidString("Father Name");

        System.out.print("Enter age: ");
        int age = getValidInt();

        String email = getValidString("Email");
        String phone = getValidString("Phone Number");
        LocalDate dob;
        try {
            int[] arr = Arrays.stream(getValidString("Please enter date").split("\\.")).mapToInt(Integer::parseInt).toArray();
            dob = LocalDate.of(arr[0], arr[1], arr[2]);
        } catch (Exception e) {
            dob = LocalDate.now();
            System.out.println("Error: " + e.getMessage());
        }

        UniversityPosition pos = selectPosition();
        ScientificDegree degree = selectDegree();

        System.out.print("Enter weekly hours: ");
        double hours = Double.parseDouble(getValidString());

        System.out.print("Enter hourly rate: ");
        double rate = Double.parseDouble(getValidString());

        try {
            Teacher teacher = new Teacher(name, surname, fatherName, age, email, phone, dob, pos, degree, hours, rate);
            staffService.save(teacher);

            System.out.println("Do you want to link teacher to faculty? (y/n)");
            String answer = getValidString();

            if (answer.equalsIgnoreCase("y")) {
                System.out.print("Enter Faculty Code to link this teacher: ");
                String facultyCode = getValidString();

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

        int choice = getValidInt() - 1;
        return (choice >= 0 && choice < positions.length) ? positions[choice] : UniversityPosition.LECTURER;
    }

    private static ScientificDegree selectDegree() {
        System.out.println("\nSelect Scientific Degree:");
        ScientificDegree[] degrees = ScientificDegree.values();

        for (int i = 0; i < degrees.length; i++) {
            System.out.println((i + 1) + ". " + degrees[i].getDisplayName());
        }

        int choice = getValidInt() - 1;
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

            String choice = getValidString();

            switch (choice) {
                case "1":
                    System.out.println("Please enter new University full name: ");
                    universityService.getUniversity().setFullName(getValidString());
                    break;
                case "2":
                    System.out.println("Please enter new University short name: ");
                    universityService.getUniversity().setShortName(getValidString());
                    break;
                case "3":
                    System.out.println("Please enter new University city: ");
                    universityService.getUniversity().setCity(getValidString());
                    break;
                case "4":
                    System.out.println("Please enter new University address: ");
                    universityService.getUniversity().setAddress(getValidString());
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

            String choice = getValidString();

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

                    Optional<Faculty> faculty = facultyService.findByCode(getValidString());

                    if (faculty.isEmpty()) {
                        System.out.println("Faculty not found");
                        break;
                    }

                    manageFacultyMenu(faculty.get());
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

            switch (getValidString()) {
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

            switch (getValidString()) {
                case "1": {
                    addGroupToSpecialty(faculty);
                    break;
                }
                case "2": {
                    System.out.print("Enter Group Name to manage: ");

                    String name = getValidString();
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

            switch (getValidString()) {
                case "1": {
                    System.out.print("Enter new name: ");
                    String newName = getValidString();

                    try {
                        groupService.updateName(group.getName(), newName);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }
                case "2": {
                    System.out.print("Are you sure? (y/n): ");

                    if (getValidString().equalsIgnoreCase("y")) {
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

            switch (getValidString()) {
                case "1":
                    System.out.print("Enter new name: ");
                    String newName = getValidString();

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

            switch (getValidString()) {
                case "1":
                    System.out.print("Enter new name for specialty: ");
                    String newName = getValidString();
                    specialityService.update(newName, specialty.getTag());
                    break;
                case "2":
                    System.out.print("Are you sure you want to delete this specialty? (y/n): ");

                    if (getValidString().equalsIgnoreCase("y")) {
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

    private static String getValidString() {
        String res = scanner.nextLine();
        while (true) {

            if (Validator.isValidString(res)) {
                return res;
            } else {
                System.out.println("Please enter valid string");
                res = scanner.nextLine();
            }
        }

    }


    private static int getValidInt() {
        boolean validInt = true;
        int res = 0;

        while (validInt) {
            try {
                res = Integer.parseInt(getValidString());
                validInt = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }
        }

        return res;
    }


}
