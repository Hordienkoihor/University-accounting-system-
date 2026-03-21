package ui;

import Utilitys.Validator;
import auth.enums.Rights;
import com.sun.jdi.Value;

import java.security.Key;
import java.util.*;

public class RoleBasedMenu {
    private static final String SEPARATOR = "---------------------------------";
    private static final String DOUBLE_SEPARATOR = "---------------------------------";

    private final Map<Rights, Runnable> roleMenus = new LinkedHashMap<>();
    private final Map<Rights, String> roleCall = new LinkedHashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    public RoleBasedMenu() {
        roleMenus.put(Rights.REPORTS, this::reportsMenu);
        roleMenus.put(Rights.LOOK, this::lookUpMenu);
        roleMenus.put(Rights.CRUD, this::crudMenu);
        roleMenus.put(Rights.CRUD_ADMIN, this::crudAdminMenu);

        roleCall.put(Rights.REPORTS, " reports menu");
        roleCall.put(Rights.LOOK, " look up menu");
        roleCall.put(Rights.CRUD, " CRUD menu");
        roleCall.put(Rights.CRUD_ADMIN, " CRUD admin menu");
    }

    public void printMenu(Set<Rights> rights) {
        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("      MAIN MENU      ");
        System.out.println(DOUBLE_SEPARATOR);

        Map<Integer, Runnable> selectionMap = new HashMap<>();

        int[] counter = {1};
        roleCall.forEach((right, action) -> {
            if (rights.contains(right)) {
                selectionMap.put(counter[0], roleMenus.get(right));

                System.out.println(counter[0] + ". " + action);
                counter[0]++;
            }
        });

        int selected = getValidInt();

        selectionMap.forEach((option, action) -> {
            if (option == selected) {
                action.run();
            }
        });

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
        };

        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("    REPORTS MENU    ");
        System.out.println(DOUBLE_SEPARATOR);

        for (String option : options) {
            System.out.println(option);
        }
    }

    private void lookUpMenu() {
        String[] options = {
                "1. Find student by ID",
                "2. Find student by full name",
                "3. Find staff by ID",
                "4. Find staff by full name",
        };


        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("    FIND MENU    ");
        System.out.println(DOUBLE_SEPARATOR);


        for (String option : options) {
            System.out.println(option);
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


        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("    CRUD MENU    ");
        System.out.println(DOUBLE_SEPARATOR);


        for (String option : options) {
            System.out.println(option);
        }
    }

    private void crudAdminMenu() {
        String[] options = {
                "1. Create user",
                "2. Delete user",
                "3. Update user",
                "4. Find user by ID",
        };


        System.out.println(DOUBLE_SEPARATOR);
        System.out.println("    CRUD ADMIN MENU    ");
        System.out.println(DOUBLE_SEPARATOR);


        for (String option : options) {
            System.out.println(option);
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
