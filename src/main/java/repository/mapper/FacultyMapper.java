package repository.mapper;

import domain.Faculty;
import repository.dto.FacultyDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class FacultyMapper implements ObjectMapper<Faculty, FacultyDto, List<Integer>> {
    @Override
    public FacultyDto toDto(Faculty t) {
        FacultyDto dto = new FacultyDto();

        dto.setName(t.getName());
        dto.setCode(t.getCode());

        if (t.getDean() != null) {
            dto.setDeanId(t.getDean().getStaffId());
        }


        return dto;
    }

    @Override
    public Faculty toEntity(FacultyDto dto, List<List<Integer>> linkedEntities) {
        Faculty faculty = new Faculty();

        faculty.setName(dto.getName());
        faculty.setCode(dto.getCode());


        return null;
    }
}
