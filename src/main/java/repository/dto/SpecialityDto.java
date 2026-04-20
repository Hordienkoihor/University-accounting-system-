package repository.dto;

public class SpecialityDto {
    private String name;
    private String specialtyTag;
    private String departmentCode;

    public SpecialityDto() {

    }

    public SpecialityDto(String name, String specialtyTag, String departmentCode) {
        this.name = name;
        this.specialtyTag = specialtyTag;
        this.departmentCode = departmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialtyTag() {
        return specialtyTag;
    }

    public void setSpecialtyTag(String specialtyTag) {
        this.specialtyTag = specialtyTag;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
