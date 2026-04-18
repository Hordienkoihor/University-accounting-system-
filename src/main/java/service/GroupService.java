package service;

import domain.Group;
import domain.Specialty;
import exceptions.GroupAlreadyExistsException;
import exceptions.GroupDoesNotExistException;
import exceptions.SpecialityDoesNotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.GroupRepositoryInt;
import service.interfaces.GroupServiceInt;
import service.interfaces.SpecialityServiceInt;

import java.util.List;

public class GroupService implements GroupServiceInt {
    private final GroupRepositoryInt repository;
    private final SpecialityServiceInt specialityService;

    private static final Logger log = LoggerFactory.getLogger(GroupService.class);

    public GroupService(GroupRepositoryInt repository, SpecialityServiceInt specialityService) {
        this.repository = repository;
        this.specialityService = specialityService;
    }

    @Override
    public void registerGroup(String specialtyTag, String groupName) {
        log.info("Attempting to register group '{}' for specialty '{}'", groupName, specialtyTag);

        if (repository.existsById(groupName)) {
            log.error("Registration failed: Group '{}' already exists", groupName);
            throw new GroupAlreadyExistsException("Group with name " + groupName + " already exists");
        }

        Specialty specialty = specialityService.findByTag(specialtyTag);

        if (specialty == null) {
            log.warn("Registration failed: Specialty '{}' not found", specialtyTag);
            throw new SpecialityDoesNotExistsException("Specialty with tag " + specialtyTag + " does not exist");
        }

        Group group = new Group(specialty, groupName);
        repository.save(group);
        log.info("Group '{}' successfully registered for specialty '{}'", groupName, specialtyTag);
    }

    @Override
    public Group findByName(String name) {
        return repository.findById(name)
                .orElseThrow(() -> {
                    log.warn("Search failed: Group '{}' not found", name);
                    return new GroupDoesNotExistException("Group not found with name: " + name);
                });
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
        log.info("Updating group name from '{}' to '{}'", oldName, newName);
        Group group = repository.findById(oldName)
                .orElseThrow(() -> {
                    log.error("Update failed: Group '{}' not found", oldName);
                    return new GroupDoesNotExistException("Group not found with name: " + oldName);
                });
        group.setName(newName);
    }

    @Override
    public void deleteByName(String name) {
        log.info("Attempting to delete group '{}'", name);
        if (!repository.existsById(name)) {
            log.warn("Delete failed: Group '{}' does not exist", name);
            throw new GroupDoesNotExistException("Cannot delete: Group " + name + " not found");
        }
        repository.deleteById(name);
        log.info("Group '{}' deleted successfully", name);
    }
}
