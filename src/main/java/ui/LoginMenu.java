package ui;

import auth.entities.LoginResponse;
import auth.enums.Right;
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

            LoginResponse<?> response;

            try {
                response = authenticationService.login(username, password);

                int rightsMask = authorizationService.provideAuthorityMask(response.user());
                printSuccess(username, rightsMask);
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

    private void printSuccess(String user, int rightsMask) {
        System.out.println(SEPARATOR);
        System.out.println("SUCCESS: Welcome, " + user);
        System.out.println("Your Permissions: " + formatRights(rightsMask));
        System.out.println(DOUBLE_SEPARATOR + "\n");
    }

    private void printError() {
        System.out.println(SEPARATOR);
        System.out.println("ERROR: Invalid username or password");
    }

    private String formatRights(int rightsMask) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Right right : Right.values()) {
            if (Right.hasRight(rightsMask, right)) {
                builder.append(right);

                builder.append(", ");
            }
        }

        if (builder.length() > 1) {
            builder.delete(builder.length() - 2, builder.length());
        }

        builder.append("]");

        return builder.toString().length() == 2 ? "No rights?" : builder.toString();
    }
}
