package Utilitys;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getValidString(String prompt) {
        System.out.print("Please enter " + prompt + ": ");
        while (true) {
            String res = this.scanner.nextLine();
            if (Validator.isValidString(res)) {
                return res;
            } else {
                System.out.print("Invalid input. Please enter " + prompt + " again: ");
            }
        }
    }

    public String getValidString() {
        while (true) {
            String res = this.scanner.nextLine();
            if (Validator.isValidString(res)) {
                return res;
            }
            System.out.println("Please enter a valid string: ");
        }
    }

    public int getValidInt(String input) {
        while (true) {
            try {
                String res = getValidString(input);
                return Integer.parseInt(res);
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + input + "' must be a whole number.");
            }
        }
    }

    public int getValidInt(String input, int top) {
        while (true) {
            int res = getValidInt(input);
            if (checkOption(res, top)) {
                return res;
            }
            System.out.println("Please choose an option between 0 and " + top);
        }
    }

    public double getValidDouble(String input) {
        while (true) {
            try {
                String res = getValidString(input);
                return Double.parseDouble(res);
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + input + "' must be a numeric value.");
            }
        }
    }

    public LocalDate getValidDate() {
        while (true) {
            String dateStr = getValidString("date (YYYY-MM-DD)");
            try {
                return LocalDate.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid format. Use YYYY-MM-DD (e.g., 2026-03-27)");
            }
        }
    }

    public boolean checkOption(int option, int top) {
        return option >= 0 && option <= top;
    }


    public String getValidPhoneNumber() {
        while (true) {
            String phone = getValidString("phone number");
            if (Validator.isValidPhoneNumber(phone, "UA")) {
                return phone;
            }

            System.out.println("\nInvalid format\n");
        }
    }

    public String getValidEmail() {
        while (true) {
            String email = getValidString("email");
            if (Validator.isValidEmailAddress(email)) {
                return email;
            }

            System.out.println("\nInvalid format\n");

        }
    }
}