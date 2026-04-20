package repository.mapper;

import domain.Department;
import domain.Faculty;
import repository.dto.DepartmentDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class DepartmentMapper implements ObjectMapper<Department, DepartmentDto, Faculty> {

    @Override
    public DepartmentDto toDto(Department t) {
        DepartmentDto dto = new DepartmentDto();

        dto.setName(t.getName());
        dto.setCode(t.getCode());
        if (t.getFaculty() != null) {
            dto.setFacultyCode(t.getFaculty().getCode());
        }

        if (t.getHeadOfDepartment() != null) {
            dto.setHeadId(t.getHeadOfDepartment().getStaffId());
        }

        return dto;
    }

    @Override
    public Department toEntity(DepartmentDto dto, List<Faculty> linkedEntities) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setCode(dto.getCode());

        if (dto.getFacultyCode() != null && linkedEntities != null) {
            Faculty matchedFaculty = linkedEntities.stream()
                    .filter(faculty -> faculty.getCode().equals(dto.getFacultyCode()))
                    .findFirst()
                    .orElse(null);
            department.setFaculty(matchedFaculty);
        }

        return null;
    }
}
