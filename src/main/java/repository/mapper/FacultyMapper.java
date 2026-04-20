package repository.mapper;

import domain.Faculty;
import domain.Teacher;
import repository.dto.FacultyDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class FacultyMapper implements ObjectMapper<Faculty, FacultyDto,Teacher> {
    @Override
    public FacultyDto toDto(Faculty t) {
        FacultyDto dto = new FacultyDto();

        dto.setName(t.getName());
        dto.setCode(t.getCode());

        if (t.getDean() != null) {
            dto.setDeanId(t.getDean().getStaffId().staffId());
        }


        return dto;
    }

    @Override
    public Faculty toEntity(FacultyDto dto, List<Teacher> linkedEntities) {
        Faculty faculty = new Faculty();

        faculty.setName(dto.getName());
        faculty.setCode(dto.getCode());

        if (dto.getDeanId() != null && linkedEntities != null && !linkedEntities.isEmpty()) {
            Teacher dean = linkedEntities.stream()
                    .filter(teacher -> teacher.getStaffId().equals(dto.getDeanId()))
                    .findFirst()
                    .orElse(null);
            faculty.setDean(dean);
        }

        return faculty;
    }
}
