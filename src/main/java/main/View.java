package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class View {
    private static University university;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boot();


    }

    private static void boot() {
        System.out.println("-- Welcome to the University Management System --");
        System.out.println("1. Create University");
        System.out.println("2. Load University\n");
        System.out.println("3. Exit ");
        System.out.println("Input: ");

        String choice = scanner.nextLine();

        if (choice.equals("3")) {
            System.exit(0);
        } else if (choice.equals("2")) {
            System.out.print("Please enter path to the config: ");
            String path = scanner.nextLine();

            university = loadUniversity(path);
        } else {
            createUniversity();
        }

        while (true) {
            mainMenu();
        }
    }

    private static void createUniversity() {
        System.out.println("Enter University name: ");
        String full = scanner.nextLine();

        System.out.println("Enter Short Name: ");
        String shortName = scanner.nextLine();

        System.out.println("Enter City: ");
        String city = scanner.nextLine();

        System.out.println("Enter Address: ");
        String address = scanner.nextLine();

        university = new University(full, shortName, city, address);
        System.out.println("Університет створено!");
    }

    private static University loadUniversity(String name) {
        University university = null;

        try {
            List<String> lines = Files.readAllLines(Paths.get(name));
            for (String line : lines) {
                String[] parts = line.split(",");

                switch (parts[0]) {
                    case "UNIVERSITY":
                        university = new University(parts[1], parts[2], parts[3], parts[4]);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return university;
    }

    private static void mainMenu() {
        System.out.println("-- Managing " + university.getFullName() + " --");
        System.out.println("1. display");
        System.out.println("2. manage");
        System.out.println("3. exit");
        System.out.print("Input: ");

        switch (scanner.nextLine()) {
            case "1":
                displayUniMenu();
                break;
            case "2":
                manageUniMenu();
                break;
            case "3":
                return;
            default:
                break;
        }
    }

    private static void displayUniMenu() {
        boolean running = true;
        while (running) {
            System.out.println("-- Display UniMenu --");
            System.out.println("1. Display University");
            System.out.println("2. Display Faculties");
            System.out.println("3. Display Students");
            System.out.println("4. Display Staff");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println(university);
                    break;
                case "2":
                    university.getFacultyList().forEach(System.out::println);
                    break;
                case "3":
                    university.getStudentsAsList().forEach(System.out::println);
                    break;
                case "4":
                    university.getStaffAsList().forEach(System.out::println);
                    break;
                default:
                    running = false;
                    break;
            }
        }

    }

    private static void manageUniMenu() {
        boolean running = true;

        while (running) {
            System.out.println("-- Manage UniMenu --");
            System.out.println("1. Manage University Properties");
            System.out.println("2. Manage Faculties");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manageUniPropertiesMenu();
                    break;
                case "2":
                    university.getFacultyList().forEach(System.out::println);
                    break;
                default:
                    running = false;
                    break;
            }
        }

    }

    private static void manageUniPropertiesMenu() {
        boolean running = true;

        while (running) {
            System.out.println("-- Manage UniProperties Menu --");
            System.out.println("1. Manage University Full Name");
            System.out.println("2. Manage University ShortName");
            System.out.println("3. Manage University City");
            System.out.println("4. Manage University Address");
            System.out.println("5. exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Please enter new University full name: ");
                    university.setFullName(scanner.nextLine());
                    break;
                case "2":
                    System.out.println("Please enter new University short name: ");
                    university.setShortName(scanner.nextLine());
                    break;
                case "3":
                    System.out.println("Please enter new University city: ");
                    university.setCity(scanner.nextLine());
                    break;
                case "4":
                    System.out.println("Please enter new University address: ");
                    university.setAddress(scanner.nextLine());
                    break;
                default:
                    running = false;
                    break;
            }
        }
    }

}
