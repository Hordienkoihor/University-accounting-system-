package ui;

import auth.repository.UserRepository;
import auth.repository.interfaces.UserRepositoryInt;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;

public class MainMenu {
    public static void main(String[] args) {
        UserRepositoryInt repo = new UserRepository();
        AuthenticationService authenticationService = new AuthenticationService(repo);
        AuthorizationService authorizationService = new AuthorizationService(authenticationService);

        LoginMenuService loginMenuService = new LoginMenuService(authenticationService, authorizationService);

        LoginMenu loginMenu = new LoginMenu(loginMenuService);

        loginMenu.login();
    }
}
