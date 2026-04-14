package ui;

import Utilitys.InputHandler;
import auth.entities.User;
import auth.enums.Right;
import auth.repository.UserRepository;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.interfaces.AuthenticationServiceInt;
import domain.*;
import domain.abstractClasses.Staff;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import service.interfaces.*;

import java.time.LocalDate;
import java.util.*;

public class RoleBasedMenu {
    private static final String SEPARATOR = "---------------------------------";
    private static final String DOUBLE_SEPARATOR = "---------------------------------";
    private static final Scanner scanner = new Scanner(System.in);
    /*uni services*/
    private final UniversityServiceInt universityService;
    private final FacultyServiceInt facultyService;
    private final DepartmentServiceInt departmentService;
    private final SpecialityServiceInt specialityService;
    private final GroupServiceInt groupService;
    private final StudentServiceInt studentService;
    private final StaffServiceInt staffService;
    /*auth service*/
    private final AuthenticationServiceInt authenticationService;
    private final UserRepositoryInt<User> userRepository;
    /*function n options storages*/
    private final Map<Right, Runnable> roleMenus = new LinkedHashMap<>();
    private final Map<Right, String> roleCall = new LinkedHashMap<>();
    private final Map<Integer, Runnable> reportActions = new HashMap<>();
    private final Map<Integer, Runnable> crudActions = new HashMap<>();

    private final StaffCRUDMenu staffCRUDMenu;
    private final FacultyCRUDMenu facultyCRUDMenu;
    private final DepartmentCRUDMenu departmentCRUDMenu;
    private final SpecialtyCRUDMenu specialtyCRUDMenu;
    private final GroupCRUDMenu groupCRUDMenu;

    private final InputHandler inputHandler;

    public RoleBasedMenu(
            UniversityServiceInt universityService,
            FacultyServiceInt facultyService,
            DepartmentServiceInt departmentService,
            SpecialityServiceInt specialityService,
            GroupServiceInt groupService,
            StudentServiceInt studentService,
            StaffServiceInt staffService,
            AuthenticationServiceInt authenticationService
    ) {
        roleMenus.put(Right.REPORTS, this::reportsMenu);
        roleMenus.put(Right.LOOK, this::lookUpMenu);
        roleMenus.put(Right.CRUD, this::crudMenu);
        roleMenus.put(Right.CRUD_ADMIN, this::crudAdminMenu);

        roleCall.put(Right.REPORTS, " reports menu");
        roleCall.put(Right.LOOK, " look up menu");
        roleCall.put(Right.CRUD, " CRUD menu");
        roleCall.put(Right.CRUD_ADMIN, " CRUD admin menu");

        this.universityService = universityService;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.specialityService = specialityService;
        this.groupService = groupService;
        this.studentService = studentService;
        this.staffService = staffService;
        this.authenticationService = authenticationService;


        InputHandler inputHandler = new InputHandler(new Scanner(System.in));

        this.staffCRUDMenu = new StaffCRUDMenu(staffService, departmentService, inputHandler);
        this.facultyCRUDMenu = new FacultyCRUDMenu(facultyService, staffService, inputHandler);
        this.departmentCRUDMenu = new DepartmentCRUDMenu(departmentService, facultyService, staffService, inputHandler);
        this.specialtyCRUDMenu = new SpecialtyCRUDMenu(specialityService, departmentService, inputHandler);
        this.groupCRUDMenu = new GroupCRUDMenu(groupService, specialityService, inputHandler);

        this.userRepository = new UserRepository();

        initReportActions();
        initCrudActions();

        this.inputHandler = new InputHandler(new Scanner(System.in));
    }


    public void printMenu(int rightsMask) {
        while (true) {
            System.out.println(DOUBLE_SEPARATOR);
            System.out.println("      MAIN MENU      ");
            System.out.println(DOUBLE_SEPARATOR);

            Map<Integer, Runnable> selectionMap = new HashMap<>();

            int[] counter = {1};
            roleCall.forEach((right, action) -> {
                if (Right.hasRight(rightsMask, right)) {
                    selectionMap.put(counter[0], roleMenus.get(right));

                    System.out.println(counter[0] + ". " + action);
                    counter[0]++;
                }
            });
            System.out.println("0. Exit");

            int selected = this.inputHandler.getValidInt("option");

            if (selected == 0) {
                break;
            }

            Runnable action = selectionMap.get(selected);
            if (action != null) {
                action.run();
            } else {
                System.out.println("Invalid selection");
            }
        }

    }

    private void initCrudActions() {
        crudActions.put(1, this::handleStudentCRUD);
        crudActions.put(2, staffCRUDMenu::handleStaffCRUD);
        crudActions.put(3, facultyCRUDMenu::handleFacultyCRUD);
        crudActions.put(4, departmentCRUDMenu::handleDepartmentCRUD);
        crudActions.put(5, specialtyCRUDMenu::handleSpecialtyCRUD);
        crudActions.put(6, groupCRUDMenu::handleGroupCRUD);
    }

    private void initReportActions() {
        reportActions.put(1, () -> listStudents(studentService.getAllCourseOrder()));
        reportActions.put(2, this::handleStudentsByFacultyAlphabetical);
        reportActions.put(3, this::handleStaffByFacultyAlphabetical);
        reportActions.put(4, this::handleStudentsByDepartmentAlphabetical);
        reportActions.put(5, this::handleStaffByDepartmentAlphabetical);
        reportActions.put(6, this::handleStudentsByDepartmentAlphabeticalSpecificCourse);
        reportActions.put(7, this::handleStudentsByDepartmentAlphabeticalSpecificCourse);
    }

    private void reportsMenu() {
        String[] options = {

                "1. List all students course order",
                "2. List all students on faculty alphabet order",
                "3. List all staff on faculty alphabet order",
                "4. List all students on department alphabet order",
                "5. List all staff on department alphabet order",
                "6. List all students on department with specific course alphabet order",
                "7. List all students on department with specific course any order",
                "0. Back to Main Menu"
        };

        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("    REPORTS MENU    ");
        System.out.println(DOUBLE_SEPARATOR);

        while (true) {
            for (String option : options) {
                System.out.println(option);
            }

            int choice = this.inputHandler.getValidInt("option", options.length);

            if (choice == 0) {
                break;
            }

            aggregateReportOption(choice);
        }
    }

    private void aggregateReportOption(int option) {
        Runnable action = reportActions.get(option);
        if (action != null) {
            action.run();
        } else {
            System.out.println("Invalid report option");
        }
    }

    private void handleStudentsByFacultyAlphabetical() {
        listFaculties();
        String facultyCode = this.inputHandler.getValidString("faculty code");

        facultyService.findByCode(facultyCode).ifPresent(faculty -> {
                    List<Student> students = studentService.getAllOnFacultyAlphabetical(faculty);

                    if (!students.isEmpty()) {
                        students.forEach(student -> {
                            System.out.println(student.getFullName() + " " + student.getStudentId());
                        });

                    } else {
                        System.out.println("No students on faculty: " + faculty.getName());
                    }
                }


        );
    }

    private void handleStaffByFacultyAlphabetical() {
        listFaculties();
        String facultyCode = this.inputHandler.getValidString("faculty code");

        facultyService.findByCode(facultyCode).ifPresent(faculty -> {
            List<Staff> staff = staffService.getAllOnFacultyAlphabetical(faculty);

            if (!staff.isEmpty()) {
                listStaffs(staff);
            } else {
                System.out.println("No staff found");
            }
        });
    }

    private void handleStaffByDepartmentAlphabetical() {
        requestDepartment().ifPresent(dept -> {
            List<Teacher> teachers = staffService.getAllOnTeacherOnDepartmentAlphabetical(dept);
            if (teachers.isEmpty()) {
                System.out.println("No teachers found");
            } else listTeachers(teachers);
        });
    }

    private void handleStudentsByDepartmentAlphabetical() {
        requestDepartment().ifPresent(dept -> {
            List<Student> students = studentService.getAllOnDepartmentAlphabetical(dept);
            if (students.isEmpty()) {
                System.out.println("No students found");
            } else listStudents(students);
        });
    }


    private void handleStudentsByDepartmentAlphabeticalSpecificCourse() {
        requestDepartment().ifPresent(dept -> {
            List<Student> students = studentService.getAllOnDepartmentAlphabetical(dept);

            if (students.isEmpty()) {
                System.out.println("No students found on department");
            } else {
                int courseId = this.inputHandler.getValidInt("course ", 6);

                students = students.stream()
                        .filter(student -> student.getCourse() == courseId).toList();
                listStudents(students);
            }
        });
    }

    private void lookUpMenu() {
        String[] options = {
                "1. Find student by ID",
                "2. Find student by full name",
                "3. Find students by surname",
                "4. Find staff by ID",
                "5. Find teacher by surname",
                "6. Find staff by full name",
                "0. Back"
        };

        while (true) {
            System.out.println(DOUBLE_SEPARATOR);
            System.out.println("    FIND MENU    ");
            System.out.println(DOUBLE_SEPARATOR);

            for (String option : options) {
                System.out.println(option);
            }

            int choice = this.inputHandler.getValidInt("action", 6);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> findStudentById();
                case 2 -> findStudentByFullName();
                case 3 -> findStudentsBySurname();
                case 4 -> findStaffById();
                case 5 -> findTeacherBySurname();
                case 6 -> findStaffByName();
            }
        }
    }

    private void findStudentsBySurname() {
        String surname = this.inputHandler.getValidString("surname");
        List<Student> students = studentService.findBySurname(surname);

        if (students.isEmpty()) {
            System.out.println("No students found");
            return;
        }

        listStudents(students);
    }

    private void findTeacherBySurname() {
        String surname = this.inputHandler.getValidString("surname");
        List<Teacher> staff = staffService.findTeacherBySurname(surname);

        if (staff.isEmpty()) {
            System.out.println("No teachers found");
            return;
        }

        listTeachers(staff);
    }

    private void findStudentById() {
        String id = this.inputHandler.getValidString("Student ID");
        var student = studentService.findById(new domain.records.StudentId(id));
        if (student != null) {
            System.out.println("Found: " + student);
        } else {
            System.out.println("Student with ID " + id + " not found");
        }
    }

    private void findStudentByFullName() {
        String name = this.inputHandler.getValidString("full name snippet");
        var students = studentService.findAll().values().stream()
                .filter(s -> s.getFullName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        if (students.isEmpty()) {
            System.out.println("No students found matching: " + name);
        } else {
            students.forEach(System.out::println);
        }
    }

    private void findStaffById() {
        String id = this.inputHandler.getValidString("Staff ID");
        var staff = staffService.findById(new domain.records.StaffId(id));
        if (staff != null) {
            System.out.println("Found: " + staff);
        } else {
            System.out.println("Staff member with ID " + id + " not found");
        }
    }

    private void findStaffByName() {
        String name = this.inputHandler.getValidString("full name snippet");
        var staffList = staffService.findAll().values().stream()
                .filter(s -> s.getFullName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        if (staffList.isEmpty()) {
            System.out.println("No staff found matching: " + name);
        } else {
            staffList.forEach(System.out::println);
        }
    }

    private void crudMenu() {

        String[] options = {
                "1. Student CRUD",
                "2. Staff CRUD",
                "3. Faculty CRUD",
                "4. Department CRUD",
                "5. Specialty CRUD",
                "6. Group CRUD",
        };


        while (true) {
            System.out.println(DOUBLE_SEPARATOR);
            System.out.println("    CRUD MENU    ");
            System.out.println(DOUBLE_SEPARATOR);

            for (String option : options) {
                System.out.println(option);
            }

            int choice = this.inputHandler.getValidInt("option", options.length);

            if (choice == 0) break;

            Runnable action = crudActions.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    private void handleStudentCRUD() {
        String[] options = {
                "1. Create Student",
                "2. View All Students",
                "3. Update Student",
                "4. Delete Student",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Student Management ---");
            for (String opt : options) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("action", 4);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createStudent();
                case 2 -> listStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
            }
        }
    }

    private void createStudent() {
        System.out.println("--- New Student Registration ---");
        try {
            String name = this.inputHandler.getValidString("name");
            String surname = this.inputHandler.getValidString("surname");
            String fatherName = this.inputHandler.getValidString("father name");
            String email = this.inputHandler.getValidEmail();
            String phone = this.inputHandler.getValidPhoneNumber();

            LocalDate dob = this.inputHandler.getValidDate();

            Student newStudent = new Student(name, surname, fatherName, email, phone, dob,
                    StudyForm.TUITION_FREE,
                    StudyStatus.PENDING
            );

            studentService.add(newStudent);
            System.out.println("Student created. ID: " + newStudent.getStudentId());
        } catch (Exception e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        listStudents();
        System.out.println("Enter exact Student ID code to delete:");
        String idInput = this.inputHandler.getValidString("Student ID");

        Optional<Student> student = studentService.findAll().values().stream()
                .filter(s -> s.getStudentId().toString().contains(idInput))
                .findFirst();

        student.ifPresentOrElse(
                s -> {
                    studentService.delete(s);
                    System.out.println("Student deleted successfully");
                },
                () -> System.out.println("Student not found")
        );
    }

    private void updateStudent() {
        listStudents();
        System.out.println("Enter Student ID code to update:");
        String idInput = this.inputHandler.getValidString("Student ID snippet");

        Optional<Student> studentOpt = studentService.findAll().values().stream()
                .filter(s -> s.getStudentId().toString().contains(idInput))
                .findFirst();

        if (studentOpt.isEmpty()) {
            System.out.println("Student not found");
            return;
        }

        Student student = studentOpt.get();
        String[] updateOptions = {
                "1. Change Course (current: " + student.getCourse() + ")",
                "2. Change Study Form (current: " + student.getStudyForm() + ")",
                "3. Change Study Status (current: " + student.getStudyStatus() + ")",
                "4. Change Group (current: " + student.getGroup() + ")",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Update Student: " + student.getFullName() + " ---");
            for (String opt : updateOptions) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("option", 4);
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1 -> {
                        int newCourse = this.inputHandler.getValidInt("new course (1-6)", 6);
                        student.setCourse(newCourse);
                        System.out.println("Course updated");
                    }
                    case 2 -> {
                        System.out.println("1. Tuition-free, 2. Paid");
                        int formChoice = this.inputHandler.getValidInt("form", 2);
                        student.setStudyForm(formChoice == 1 ? StudyForm.TUITION_FREE : StudyForm.TUITION);
                        System.out.println("Study form updated");
                    }
                    case 3 -> {
                        System.out.println("1. Studying, 2. Pending, 3. Academic leave, 4. Expelled");
                        int statusChoice = this.inputHandler.getValidInt("status", 3);
                        StudyStatus newStatus = switch (statusChoice) {
                            case 1 -> StudyStatus.STUDYING;
                            case 2 -> StudyStatus.PENDING;
                            case 4 -> StudyStatus.EXPELLED;
                            default -> StudyStatus.ACADEMIC_LEAVE;
                        };
                        student.setStudyStatus(newStatus);
                        System.out.println("Status updated");
                    }
                    case 4 -> {
                        String group = this.inputHandler.getValidString("group name");

                        Group group1 = groupService.findByName(group);

                        if (group1 == null) {
                            System.out.println("Group not found");
                        } else {
                            student.setGroup(group1);
                        }
                    }
                }
                studentService.save(student);
            } catch (Exception e) {
                System.out.println("Update failed: " + e.getMessage());
            }
        }
    }

    private void registerStudentToGroupAction() {
        listStudents();
        String studentIdStr = this.inputHandler.getValidString("Student ID snippet");

        Optional<Student> studentOpt = studentService.findAll().values().stream()
                .filter(s -> s.getStudentId().toString().contains(studentIdStr))
                .findFirst();

        if (studentOpt.isPresent()) {
            String groupName = this.inputHandler.getValidString("Group Name");
            try {
                studentService.registerToGroup(studentOpt.get(), groupName);
            } catch (Exception e) {
                System.out.println("Registration failed: " + e.getMessage());
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private void crudAdminMenu() {
        String[] options = {
                "1. Create user",
                "2. Delete user",
                "3. Update user",
                "4. Find user by ID",
        };


        while (true) {
            System.out.println(DOUBLE_SEPARATOR);
            System.out.println("    CRUD ADMIN MENU    ");
            System.out.println(DOUBLE_SEPARATOR);

            for (String option : options) {
                System.out.println(option);
            }

            int choice = this.inputHandler.getValidInt("action", 4);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createUser();
                case 2 -> deleteUser();
                case 3 -> updateUser();
                case 4 -> findUserById();
            }
        }
    }

    private void createUser() {
        System.out.println("--- Create New User ---");
        String name = this.inputHandler.getValidString("username");

        if (userRepository.existsById(name)) {
            System.out.println("Error: User with this name already exists");
            return;
        }

        String password = this.inputHandler.getValidString("password");

        System.out.println("Select type: 1. User, 2. Manager");
        int typeChoice = this.inputHandler.getValidInt("type", 2);
        String type = (typeChoice == 2) ? "ManagerRole" : "UserRole";

        User newUser = auth.UserFactory.createUser(type, name, password);
        userRepository.save(newUser);
        System.out.println("User created and saved to " + "user_config.csv");
    }

    private void deleteUser() {
        String name = this.inputHandler.getValidString("username to delete");
        if (userRepository.existsById(name)) {
            userRepository.deleteById(name);
            System.out.println("User '" + name + "' deleted successfully");
        } else {
            System.out.println("User not found");
        }
    }

    private void updateUser() {
        String name = this.inputHandler.getValidString("username to update");
        userRepository.findById(name).ifPresentOrElse(user -> {
            System.out.println("Updating user: " + user.getName());
            String newPassword = this.inputHandler.getValidString("new password");

            User updatedUser = auth.UserFactory.createUser(
                    user.getClass().getSimpleName(),
                    user.getName(),
                    newPassword
            );

            userRepository.save(updatedUser);
            System.out.println("Password updated");
        }, () -> System.out.println("User not found"));
    }

    private void findUserById() {
        String name = this.inputHandler.getValidString("username");
        userRepository.findById(name).ifPresentOrElse(
                user -> System.out.println("Found: " + user.getClass().getSimpleName() + " [Name: " + user.getName() + "]"),
                () -> System.out.println("User not found")
        );
    }

    private void listFaculties() {
        System.out.println(SEPARATOR);
        System.out.println("    FACULTIES    ");
        List<Faculty> faculties = facultyService.getAllAsList();

        faculties.forEach(faculty -> System.out.println(faculty.getName() + " " + faculty.getCode()));
    }

    private void listDepartments() {
        System.out.println(SEPARATOR);
        System.out.println("    DEPARTMENTS    ");
        List<Department> departments = departmentService.getAllDepartments();

        departments.forEach(department -> System.out.println(department.getName() + " " + department.getCode()));
    }

    private void listStudents() {
        System.out.println(SEPARATOR);
        System.out.println("    STUDENTS    ");
        List<Student> students = studentService.findAll().values().stream().toList();

        students.forEach(student -> System.out.println(student.getFullName() + " " + student.getStudentId()));
    }

    private void listTeachers() {
        System.out.println(SEPARATOR);
        System.out.println("    Teachers    ");
        List<Teacher> staff = staffService.findAllTeachers();

        staff.forEach(teacher -> teacher.compactToString());
    }

    private void listStudents(List<Student> students) {
        System.out.println("\n " + SEPARATOR);
        students.forEach(
//                student -> System.out.println(student.getFullName() + ", " + student.getStudentId() + ", " + student.getSurname())
                student -> System.out.println(student.compactToString())
        );
        System.out.println(SEPARATOR + "\n");
    }

    private void listTeachers(List<Teacher> teachers) {
        System.out.println("\n " + SEPARATOR);
        teachers.forEach(teacher -> System.out.println(teacher.compactToString()));
        System.out.println(SEPARATOR + "\n");
    }

    private void listStaffs(List<Staff> staffs) {
        staffs.forEach(staff -> System.out.println(staff.getFullName() + " " + staff.getStaffId()));
    }


    private Optional<Department> requestDepartment() {
        listDepartments();
        String deptCode = this.inputHandler.getValidString("department code");
        try {
            return Optional.of(departmentService.getByCode(deptCode));
        } catch (Exception e) {
            System.out.println("Department with code '" + deptCode + "' not found!");
            return Optional.empty();
        }
    }
}
