package domain;

import domain.abstractClasses.Staff;
import domain.enums.ScientificDegree;
import domain.enums.UniversityPosition;
import exceptions.IllegalHourlyRateNumber;
import exceptions.IllegalHoursNumber;

import java.util.Date;

public class Teacher extends Staff {
    private UniversityPosition universityPosition;
    private ScientificDegree scientificDegree;

    private Date dateOfHiring;

    private double weeklyHours;
    private double hourlyRate;

    public Teacher(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            Date dateOfBirth,
            UniversityPosition universityPosition,
            ScientificDegree scientificDegree,
            double hours,
            double hourlyRate
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);
        setUniversityPosition(universityPosition);
        setScientificDegree(scientificDegree);

        setDateOfHiring(dateOfHiring);

        setWeeklyHours(weeklyHours);
        setHourlyRate(hourlyRate);
    }

    public UniversityPosition getUniversityPosition() {
        return universityPosition;
    }

    public ScientificDegree getScientificDegree() {
        return scientificDegree;
    }

    public Date getDateOfHiring() {
        return dateOfHiring;
    }

    public double getWeeklyHours() {
        return weeklyHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setUniversityPosition(UniversityPosition universityPosition) {
        this.universityPosition = universityPosition;
    }

    public void setScientificDegree(ScientificDegree scientificDegree) {
        this.scientificDegree = scientificDegree;
    }

    public void setDateOfHiring(Date dateOfHiring) {
        this.dateOfHiring = dateOfHiring;
    }

    public void setWeeklyHours(double weeklyHours) {
        if (weeklyHours < 0 || weeklyHours > 120) {
            throw new IllegalHoursNumber("Illegal hours number");
        }

        this.weeklyHours = weeklyHours;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalHourlyRateNumber("Illegal hourly rate number");
        }

        this.hourlyRate = hourlyRate;
    }

    public double getExpectedWeeklyPay() {
        return hourlyRate * weeklyHours;
    }

    @Override
    public String toString() {
        return "Teacher {"  + '\n' +
                "   universityPosition=" + universityPosition + ',' + '\n' +
                "   scientificDegree=" + scientificDegree + ',' + '\n' +
                "   dateOfHiring=" + dateOfHiring + ',' + '\n' +
                "   weeklyHours=" + weeklyHours + ',' + '\n' +
                "   hourlyRate=" + hourlyRate + ',' + '\n' +
                "} " + super.toString();
    }
}
