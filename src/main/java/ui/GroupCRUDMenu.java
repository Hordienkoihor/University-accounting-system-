package ui;

import Utilitys.InputHandler;
import domain.Group;
import domain.Specialty;
import service.interfaces.GroupServiceInt;
import service.interfaces.SpecialityServiceInt;

import java.util.List;
import java.util.Scanner;

public class GroupCRUDMenu {
    private final GroupServiceInt groupService;
    private final SpecialityServiceInt specialityService;
    private final InputHandler inputHandler;

    public GroupCRUDMenu(
            GroupServiceInt groupService,
            SpecialityServiceInt specialityService,
            InputHandler inputHandler
    ) {
        this.groupService = groupService;
        this.specialityService = specialityService;
        this.inputHandler = inputHandler;
    }

    public void handleGroupCRUD() {
        String[] options = {
                "1. Register New Group",
                "2. View All Groups",
                "3. Find Groups by Specialty",
                "4. Update Group Name",
                "5. Delete Group",
                "0. Back"
        };

        while (true) {
            System.out.println("\n--- Group Management ---");
            for (String opt : options) System.out.println(opt);

            int choice = this.inputHandler.getValidInt("action", 5);
            if (choice == 0) break;

            switch (choice) {
                case 1 -> createGroup();
                case 2 -> listAllGroups();
                case 3 -> listBySpecialty();
                case 4 -> updateGroupName();
                case 5 -> deleteGroup();
            }
        }
    }

    private void createGroup() {
        System.out.println("--- Registering New Group ---");
        try {
            Specialty selectedSpecialty = selectSpecialty();
            if (selectedSpecialty == null) {
                System.out.println("Operation cancelled: Speciality is required");
                return;
            }

            String tag = selectedSpecialty.getTag();
            System.out.println("Note: Group name MUST contain the specialty tag: [" + tag + "]");

            String groupName = this.inputHandler.getValidString("group name");

            groupService.registerGroup(tag, groupName);
            System.out.println("Group '" + groupName + "' successfully registered for specialty " + selectedSpecialty.getName());

        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    private void listAllGroups() {
        System.out.println("\n--- Current Groups List ---");
        List<Group> groups = groupService.findAll();
        if (groups.isEmpty()) {
            System.out.println("No groups found");
        } else {
            groups.forEach(System.out::println);
        }
    }

    private void listBySpecialty() {
        String tag = this.inputHandler.getValidString("specialty tag to filter by");
        List<Group> groups = groupService.findAllBySpecialty(tag);

        if (groups.isEmpty()) {
            System.out.println("No groups found for specialty tag: " + tag);
        } else {
            groups.forEach(System.out::println);
        }
    }

    private void updateGroupName() {
        String oldName = this.inputHandler.getValidString("current group name");
        String newName = this.inputHandler.getValidString("new group name");

        try {
            groupService.updateName(oldName, newName);
            System.out.println("Group name updated successfully");
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    private void deleteGroup() {
        String name = this.inputHandler.getValidString("group name to delete");
        try {
            groupService.deleteByName(name);
            System.out.println("Group '" + name + "' deleted");
        } catch (Exception e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    private Specialty selectSpecialty() {
        List<Specialty> specialties = specialityService.getAllSpecialties();
        if (specialties.isEmpty()) {
            System.out.println("Error: No specialties found please create a specialty first");
            return null;
        }

        System.out.println("Available Specialties:");
        specialties.forEach(s -> System.out.println("[" + s.getTag() + "] " + s.getName()));

        while (true) {
            String tag = this.inputHandler.getValidString("specialty tag (q to cancel)");
            if (tag.equalsIgnoreCase("q")) return null;

            try {
                Specialty s = specialityService.findByTag(tag);
                if (s != null) return s;
            } catch (Exception e) {
                System.out.println("Specialty with tag " + tag + " not found");
            }
        }
    }
}
