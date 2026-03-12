package domain.abstractClasses;

import domain.records.StaffId;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public abstract class Staff extends Person {
    private final Random random = new Random();
    private final StaffId staffId;

    protected Staff(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);
        staffId = new StaffId( "ST-ID-" + new Date().getTime() * random.nextInt(1, 500));
    }

    public StaffId getStaffId() {
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
                "} " ;
    }
}
