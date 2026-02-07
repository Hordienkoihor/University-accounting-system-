package service;

import Utilitys.Validator;
import domain.Specialty;
import exceptions.SpecialityAlreadyExistsException;
import repository.interfaces.SpecialityRepositoryInt;
import service.interfaces.SpecialityServiceInt;

import java.util.List;

public class SpecialityService implements SpecialityServiceInt {
    private final SpecialityRepositoryInt specialityRepository;

    public SpecialityService(SpecialityRepositoryInt specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public void register(String facultyCode, Specialty specialty) {
        if (existsByTag(specialty.getTag())) {
            throw new SpecialityAlreadyExistsException("Specialty with tag " + specialty.getTag() + " already exists");
        }

        specialityRepository.save(facultyCode, specialty);
    }

    @Override
    public void update(String newName, String tag) {
        Specialty specialty = findByTag(tag);

        if (specialty != null && Validator.isValidString(newName)) {
            specialty.setName(newName);
        }
    }

    @Override
    public Specialty findByTag(String tag) {
        return specialityRepository.findByTag(tag);
    }

    @Override
    public Specialty findByName(String name) {
        return specialityRepository.findByName(name);
    }

    @Override
    public List<Specialty> findAllOnFaculty(String facultyCode) {
        return specialityRepository.findAllOnFaculty(facultyCode);
    }

    @Override
    public boolean existsByTag(String tag) {
        return specialityRepository.existsByTag(tag);
    }

    @Override
    public void removeByTag(String tag) {
        if (!existsByTag(tag)) {
            System.out.println("Specialty with tag " + tag + " does not exist");
        }

        specialityRepository.deleteByTag(tag);
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialityRepository.findAll();
    }
}
