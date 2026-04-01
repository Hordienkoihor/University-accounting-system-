package repository.dto;

import domain.Specialty;

public class GroupDto {
    private String name;
    private String specialtyTag;

    public GroupDto(String name, String specialtyTag) {
        this.name = name;
        this.specialtyTag = specialtyTag;
    }

    public GroupDto() {
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
}
