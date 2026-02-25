package service;

import domain.Group;
import domain.Specialty;
import exceptions.GroupAlreadyExistsException;
import exceptions.SpecialityDoesNotExistsException;
import repository.interfaces.GroupRepositoryInt;
import repository.interfaces.SpecialityRepositoryInt;
import service.interfaces.GroupServiceInt;
import service.interfaces.SpecialityServiceInt;

import java.util.List;

public class GroupService implements GroupServiceInt {
    private final GroupRepositoryInt repository;
    private final SpecialityServiceInt specialityService;

    public GroupService(GroupRepositoryInt repository, SpecialityServiceInt specialityService) {
        this.repository = repository;
        this.specialityService = specialityService;
    }

    @Override
    public void registerGroup(String specialtyTag, String groupName) {
        if (repository.existsById(groupName)) {
            throw new GroupAlreadyExistsException("Group with name " + groupName + " already exists");
        }

        Specialty specialty = specialityService.findByTag(specialtyTag);

        if (specialty == null) {
            throw new SpecialityDoesNotExistsException("Specialty with tag " + specialtyTag + " does not exist");
        }

        Group group = new Group(specialty, groupName);
        repository.save(specialtyTag, group);
    }

    @Override
    public Group findByName(String name) {
        return repository.findById(name);
    }

    @Override
    public List<Group> findAllBySpecialty(String specialtyTag) {
        return repository.findAllBySpecialty(specialtyTag);
    }

    @Override
    public List<Group> findAll() {
        return repository.findAll();
    }


    @Override
    public void updateName(String oldName, String newName) {
        Group group = repository.findById(oldName);
        if (group != null) {
            group.setName(newName);
        }
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteById(name);
    }
}
