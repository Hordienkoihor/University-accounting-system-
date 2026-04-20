package repository.dto;

import domain.records.StaffId;

public class DepartmentDto {
    private String name;
    private String code;
    private String facultyCode;
    private StaffId headId;

    public DepartmentDto(String name, String code, String facultyCode, StaffId headId) {
        this.name = name;
        this.code = code;
        this.facultyCode = facultyCode;
        this.headId = headId;
    }

    public DepartmentDto() {

    }

    public StaffId getHeadId() {
        return headId;
    }

    public void setHeadId(StaffId headId) {
        this.headId = headId;
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

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }
}
