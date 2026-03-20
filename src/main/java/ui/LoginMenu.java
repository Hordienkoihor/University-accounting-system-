package ui;

import auth.enums.Rights;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class LoginMenu {
    private final LoginMenuService loginMenuService;
    private final Scanner scanner;

    private static final String SEPARATOR = "---------------------------------";
    private static final String DOUBLE_SEPARATOR = "---------------------------------";

    public LoginMenu(LoginMenuService loginMenuService) {
        this.loginMenuService = loginMenuService;
        this.scanner = new Scanner(System.in);
    }

    public void login() {
        printHeader();

        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.println("\nAuthenticating...");
            Optional<Set<Rights>> rights = loginMenuService.login(username, password);

            if (rights.isPresent()) {
                printSuccess(username, rights.get());
                break;
            } else {
                printError();
                System.out.print("Try again? (y/n): ");
                if (!scanner.nextLine().equalsIgnoreCase("y")) {
                    System.out.println("Exiting system");
                    break;
                }
                System.out.println();
            }
        }
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
