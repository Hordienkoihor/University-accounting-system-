package service;

import domain.Group;
import domain.Specialty;
import exceptions.GroupAlreadyExistsException;
import exceptions.SpecialityDoesNotExistsException;
import repository.interfaces.GroupRepositoryInt;
import repository.interfaces.SpecialityRepositoryInt;
import service.interfaces.GroupServiceInt;

import java.util.List;

public class GroupService implements GroupServiceInt {
    private final GroupRepositoryInt repository;
    private final SpecialityRepositoryInt specialityRepository;

    public GroupService(GroupRepositoryInt repository, SpecialityRepositoryInt specialityRepository) {
        this.repository = repository;
        this.specialityRepository = specialityRepository;
    }

    @Override
    public void registerGroup(String specialtyTag, String groupName) {
        if (repository.existsByName(groupName)) {
            throw new GroupAlreadyExistsException("Group with name " + groupName + " already exists");
        }

        Specialty specialty = specialityRepository.findByTag(specialtyTag);

        if (specialty == null) {
            throw new SpecialityDoesNotExistsException("Specialty with tag " + specialtyTag + " does not exist");
        }

        Group group = new Group(specialty, groupName);
        repository.save(specialtyTag, group);
    }

    @Override
    public Group findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Group> findAllBySpecialty(String specialtyTag) {
        return repository.findAllBySpecialty(specialtyTag);
    }

    @Override
    public void updateName(String oldName, String newName) {
        Group group = repository.findByName(oldName);
        if (group != null) {
            group.setName(newName);
        }
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }
}
