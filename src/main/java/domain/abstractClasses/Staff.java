package domain.abstractClasses;

import domain.records.StaffId;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public abstract class Staff extends Person {
    private final Random random = new Random();
    private  StaffId staffId;

    public Staff() {
        super();
    }

    protected Staff(
            String name,
            String surname,
            String fatherName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth
    ) {
        super(name, surname, fatherName, email, phoneNumber, dateOfBirth);
        staffId = new StaffId("ST-ID-" + new Date().getTime() * random.nextInt(1, 500));
    }

    public void setStaffId(StaffId staffId) {
        this.staffId = staffId;
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
        return "Staff {" + '\n' +
                "   staffId=" + staffId + ',' + '\n' +
                "} " + super.toString();
    }
}
