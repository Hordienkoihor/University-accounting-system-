package repository.mapper;

import domain.Group;
import domain.Specialty;
import repository.dto.GroupDto;
import repository.mapper.interfaces.ObjectMapper;

import java.util.List;

public class GroupMapper implements ObjectMapper<Group, GroupDto, Specialty> {
    @Override
    public GroupDto toDto(Group group) {
        String specialtyTag = (group.getSpecialty() != null) ? group.getSpecialty().getTag() : null;
        return new GroupDto(group.getName(), specialtyTag);
    }

    @Override
    public Group toEntity(GroupDto dto, List<Specialty> specialties) {
        Group group = new Group();
        group.setName(dto.getName());

        group.setSpecialty(specialties.stream()
                .filter(specialty -> specialty.getTag().equals(dto.getSpecialtyTag()))
                .findFirst()
                .orElse(null));

        return group;
    }
}
