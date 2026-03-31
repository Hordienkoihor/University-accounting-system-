package ui;

import Utilitys.InputHandler;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import service.interfaces.StaffServiceInt;

public class StaffSelectorHelper {
    public static Staff selectStaff(StaffServiceInt staffService, InputHandler inputHandler) {
        staffService.findAll().values().forEach(s ->
                System.out.println(s.getName() + " id: " + s.getStaffId()));
        while (true) {
            String input = inputHandler.getValidString("staffId (q to quit)");
            if (input.equals("q")) return null;
            Staff found = staffService.findById(new StaffId(input));
            if (found != null) return found;
            System.out.println("Staff not found");
        }
    }
}