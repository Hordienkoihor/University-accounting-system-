package repository.mapper;

import domain.Department;
import domain.Group;
import domain.Specialty;
import repository.dto.GroupDto;
import repository.dto.SpecialityDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class SpecialityMapper implements ObjectMapper<Specialty, SpecialityDto, Department> {

    @Override
    public SpecialityDto toDto(Specialty t) {
        SpecialityDto dto = new SpecialityDto();

        dto.setName(t.getName());
        dto.setSpecialtyTag(t.getTag());
        dto.setDepartmentCode(t.getDepartment() != null ? t.getDepartment().getCode() : null);
        return dto;
    }

    @Override
    public Specialty toEntity(SpecialityDto dto, List<Department> linkedEntities) {
        Specialty entity = new Specialty();

        entity.setName(dto.getName());
        entity.setTag(dto.getSpecialtyTag());

        Department department = linkedEntities.stream()
                .filter(department1 -> department1.getCode().equals(dto.getDepartmentCode()))
                .findFirst()
                .orElse(null);
        entity.setDepartment(department);

        return entity;
    }
}
