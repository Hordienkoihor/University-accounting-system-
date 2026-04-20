package repository.dto;

import domain.records.StaffId;

public class FacultyDto {
    private String name;
    private String code;
    private StaffId deanId;

    public FacultyDto(String name, String code, StaffId deanId) {
        this.name = name;
        this.code = code;
        this.deanId = deanId;
    }

    public FacultyDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StaffId getDeanId() {
        return deanId;
    }

    public void setDeanId(StaffId deanId) {
        this.deanId = deanId;
    }
}
