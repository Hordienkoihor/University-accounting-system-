package domain.abstractClasses;

import java.util.Date;

public abstract class Staff extends Person {
    private static int staffIdCounter = 0;

    private final int staffId;

    protected Staff(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            Date dateOfBirth
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);
        staffId = staffIdCounter++;
    }

    public int getStaffId() {
        return staffId;
    }

    //    @Override
//    public String toString() {
//        return "{" + '\'' +
//                "staffId=" + staffId + '\'' +
//                "}" + '\'' +
//                super.toString();
//    }


    @Override
    public String toString() {
        return "Staff {"  + '\n' +
                "   staffId=" + staffId + ',' + '\n' +
                "} " + super.toString();
    }
}
