package domain.abstractClasses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import domain.Department;
import domain.Faculty;
import domain.Teacher;
import domain.records.StaffId;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "type"
//)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Teacher.class, name = "teacher")
//})
public abstract class Staff extends Person {
    private final Random random = new Random();
    private StaffId staffId;

    protected Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

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

    public StaffId getStaffId() {
        return staffId;
    }

    public void setStaffId(StaffId staffId) {
        this.staffId = staffId;
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
                "} ";
    }
}
