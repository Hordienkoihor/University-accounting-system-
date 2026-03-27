package Utilitys;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getValidString(String input) {
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

    public static String getValidString() {
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

    public static int getValidInt() {
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

    public static int getValidInt(String input) {
        boolean validInt = true;
        int res = 0;

        while (validInt) {
            try {
                res = Integer.parseInt(getValidString());
                validInt = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid " + input + ": ");
            }
        }

        return res;

    }

    public static int getValidInt(String input, int top) {
        boolean validInt = true;
        int res = 0;

        while (validInt) {
            try {
                res = Integer.parseInt(getValidString());

                if (checkOption(res, top)) {
                    validInt = false;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter valid " + input + ": ");
            }
        }

        return res;

    }

    public static boolean checkOption(int option, int top) {
        return option >= 0 && option <= top;
    }

    public static LocalDate getValidDate() {
        while (true) {
            String date = getValidString();

            try {
                return LocalDate.parse(date);
            } catch (Exception e) {
                System.out.println("Please enter a valid date format such as 2007-12-03");
            }
        }
    }

    public static double getValidDouble(String input) {
        boolean validDouble = true;
        double res = 0;

        while (validDouble) {
            try {
                res = Double.parseDouble(getValidString());
                validDouble = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid double");
            }
        }

        return res;
    }
}
