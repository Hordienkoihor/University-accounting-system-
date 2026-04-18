package service;

import Utilitys.Validator;
import domain.Specialty;
import exceptions.SpecialityAlreadyExistsException;
import exceptions.SpecialityDoesNotExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.SpecialityRepositoryInt;
import service.interfaces.SpecialityServiceInt;

import java.util.List;

public class SpecialityService implements SpecialityServiceInt {
    private final SpecialityRepositoryInt specialityRepository;

    private static final Logger log = LoggerFactory.getLogger(SpecialityService.class);

    public SpecialityService(SpecialityRepositoryInt specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public void register(Specialty specialty) {
        log.info("Attempting to register specialty: {} ({})", specialty.getName(), specialty.getTag());
        if (existsByTag(specialty.getTag())) {
            log.error("Registration failed: Specialty with tag '{}' already exists", specialty.getTag());
            throw new SpecialityAlreadyExistsException("Specialty with tag " + specialty.getTag() + " already exists");
        }

        specialityRepository.save(specialty);
        log.info("Specialty registered successfully: {}", specialty.getTag());
    }

    @Override
    public void update(String newName, String tag) {
        log.info("Updating specialty '{}' with new name: '{}'", tag, newName);
        Specialty specialty = findByTag(tag);

        if (Validator.isValidString(newName)) {
            specialty.setName(newName);
            specialityRepository.save(specialty);
            log.info("Specialty '{}' updated successfully", tag);
        } else {
            log.error("Update failed: Invalid name provided for specialty '{}'", tag);
        }
    }

    @Override
    public Specialty findByTag(String tag) {
        return specialityRepository.findById(tag)
                .orElseThrow(() -> {
                    log.warn("Search failed: Specialty with tag '{}' not found", tag);
                    return new SpecialityDoesNotExistsException("Specialty with tag " + tag + " does not exist");
                });
    }

    @Override
    public Specialty findByName(String name) {
        return specialityRepository.findByName(name)
                .orElseThrow(() -> new SpecialityDoesNotExistsException("Specialty with name " + name + " does not exist"));

    }

    @Override
    public List<Specialty> findAllOnDepartment(String departmentCode) {
        return specialityRepository.findAllOnDepartment(departmentCode);
    }

    @Override
    public boolean existsByTag(String tag) {
        return specialityRepository.existsById(tag);
    }

    @Override
    public void removeByTag(String tag) {
        log.info("Attempting to remove specialty with tag: {}", tag);
        if (!existsByTag(tag)) {
            log.warn("Removal failed: Specialty with tag '{}' does not exist", tag);
            throw new SpecialityDoesNotExistsException("Specialty with tag " + tag + " does not exist");
        }

        specialityRepository.deleteById(tag);
        log.info("Specialty '{}' removed successfully", tag);
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialityRepository.findAll();
    }
}
