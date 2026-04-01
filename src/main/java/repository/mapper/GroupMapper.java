package repository.mapper;

import domain.Group;
import domain.Specialty;
import repository.dto.GroupDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class GroupMapper implements ObjectMapper<Group, GroupDto, Specialty> {
    @Override
    public GroupDto toDto(Group group) {
        return null;
    }

    @Override
    public Group toEntity(GroupDto dto, List<Specialty> specialties) {
        return null;
    }
}
