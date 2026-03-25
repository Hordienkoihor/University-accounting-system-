package ui;

import Utilitys.ConfigLoader;
import auth.entities.LoginResponse;
import auth.entities.User;
import auth.repository.UserRepository;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;
import repository.*;
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

        universityService = new UniversityService(new UniversityRepository());
        facultyService = new FacultyService(new FacultyRepository());
        departmentService = new DepartmentService(new DepartmentRepository(), facultyService);
        specialityService = new SpecialityService(new SpecialityRepository());
        groupService = new GroupService(new GroupRepository(), specialityService);
        studentService = new StudentService(new StudentRepository(), groupService);
        staffService = new StaffService(new StaffRepository(), facultyService);

        LoginMenu loginMenu = new LoginMenu(authenticationService, authorizationService);

        Optional<LoginResponse> res = loginMenu.login();

        res.ifPresent(loginResponse -> currentUser = loginResponse.user());

        if (currentUser != null) {

            ConfigLoader loader = new ConfigLoader(universityService, studentService, facultyService);
            loader.load("config.csv");



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

            mainMenu.printMenu(authorizationService.provideAuthority(currentUser));
        }

    }
}
