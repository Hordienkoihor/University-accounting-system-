package ui;

import auth.entities.LoginResponse;
import auth.entities.User;
import auth.repository.UserRepository;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;

import java.util.Optional;

public class MainMenu {
    private static User currentUser;

    public static void main(String[] args) {
        UserRepositoryInt<User> repo = new UserRepository();
        AuthenticationService<User> authenticationService = new AuthenticationService(repo);
        AuthorizationService authorizationService = new AuthorizationService(authenticationService);

        LoginMenu loginMenu = new LoginMenu(authenticationService, authorizationService);

        Optional<LoginResponse> res = loginMenu.login();

        res.ifPresent(loginResponse -> currentUser = loginResponse.user());

        if (currentUser != null) {
            System.out.println("Main menu for: " + currentUser.getName() + "\n");

            RoleBasedMenu mainMenu = new RoleBasedMenu();

            mainMenu.printMenu(authorizationService.provideAuthority(currentUser));
        }

    }
}
