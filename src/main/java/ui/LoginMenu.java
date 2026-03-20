package ui;

import auth.entities.LoginResponse;
import auth.enums.Rights;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class LoginMenu {
    private static final String SEPARATOR = "---------------------------------";
    private static final String DOUBLE_SEPARATOR = "---------------------------------";
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final Scanner scanner;

    public LoginMenu(AuthenticationService authenticationService, AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.scanner = new Scanner(System.in);
    }

    public Optional<LoginResponse> login() {
        printHeader();

        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.println("\nAuthenticating...");

            LoginResponse response;

            try {
                response = authenticationService.login(username, password);

                Set<Rights> rights = authorizationService.provideAuthority(response.user());
                printSuccess(username, rights);
                return Optional.of(response);
            } catch (Exception e) {
                System.out.println(e.getMessage());

                printError();
                System.out.print("Try again? (y/n): ");
                if (!scanner.nextLine().equalsIgnoreCase("y")) {
                    System.out.println("Exiting system");
                    break;
                }
                System.out.println();
            }

        }

        return Optional.empty();
    }

    private void printHeader() {
        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("    SYSTEM SECURE LOGIN         ");
        System.out.println(DOUBLE_SEPARATOR);
    }

    private void printSuccess(String user, Set<Rights> rights) {
        System.out.println(SEPARATOR);
        System.out.println("SUCCESS: Welcome, " + user);
        System.out.println("Your Permissions: " + rights);
        System.out.println(DOUBLE_SEPARATOR + "\n");
    }

    private void printError() {
        System.out.println(SEPARATOR);
        System.out.println("ERROR: Invalid username or password");
    }
}
