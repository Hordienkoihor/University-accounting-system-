package ui;

import Utilitys.ConfigLoader;
import auth.entities.LoginResponse;
import auth.entities.User;
import auth.repository.UserRepository;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;
import domain.Department;
import repository.*;
import repository.mapper.FacultyDepartmentPersonLinker;
import service.*;
import service.interfaces.*;

import java.util.Optional;

public class MainMenu {
    private static User currentUser;

    /*uni services*/
    private static UniversityServiceInt universityService;
    private static FacultyServiceInt facultyService;
    private static DepartmentServiceInt departmentService;
    private static SpecialityServiceInt specialityService;
    private static GroupServiceInt groupService;
    private static StudentServiceInt studentService;
    private static StaffServiceInt staffService;

    public static void main(String[] args) {
        UserRepositoryInt<User> repo = new UserRepository();
        AuthenticationService<User> authenticationService = new AuthenticationService(repo);
        AuthorizationService authorizationService = new AuthorizationService(authenticationService);

        FacultyRepository facultyRepo = new FacultyRepository();
        DepartmentRepository departmentRepo = new DepartmentRepository();
        StaffRepository staffRepo = new StaffRepository();

        SpecialityRepository specialityRepo = new SpecialityRepository();
        GroupRepository groupRepo = new GroupRepository();
        StudentRepository studentRepo = new StudentRepository();

        FacultyDepartmentPersonLinker facultyDepartmentPersonLinker = new FacultyDepartmentPersonLinker();
        facultyDepartmentPersonLinker.loadUniversityData(facultyRepo, departmentRepo, staffRepo, specialityRepo, groupRepo, studentRepo);



        universityService = new UniversityService(new UniversityRepository());
        facultyService = new FacultyService(facultyRepo);
        departmentService = new DepartmentService(departmentRepo, facultyService);
        specialityService = new SpecialityService(specialityRepo);
        groupService = new GroupService(groupRepo, specialityService);
        studentService = new StudentService(studentRepo, groupService);
        staffService = new StaffService(staffRepo, facultyService);

        LoginMenu loginMenu = new LoginMenu(authenticationService, authorizationService);

        Optional<LoginResponse> res = loginMenu.login();

        res.ifPresent(loginResponse -> currentUser = loginResponse.user());

        if (currentUser != null) {

            ConfigLoader loader = new ConfigLoader(universityService, studentService, facultyService);
            loader.load("configTest.csv");

            System.out.println("Main menu for: " + currentUser.getName() + "\n");

            RoleBasedMenu mainMenu = new RoleBasedMenu(
                    universityService,
                    facultyService,
                    departmentService,
                    specialityService,
                    groupService,
                    studentService,
                    staffService,
                    authenticationService
            );

            mainMenu.printMenu(currentUser.getRightsMask());
        }

    }
}
