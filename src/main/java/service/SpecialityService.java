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
        return specialityRepository.findById(tag).get();
    }

    @Override
    public Specialty findByName(String name) {
        return specialityRepository.findByName(name).get();
    }

    @Override
    public List<Specialty> findAllOnFaculty(String facultyCode) {
        return specialityRepository.findAllOnFaculty(facultyCode);
    }

    @Override
    public boolean existsByTag(String tag) {
        return specialityRepository.existsById(tag);
    }

    @Override
    public void removeByTag(String tag) {
        if (!existsByTag(tag)) {
            System.out.println("Specialty with tag " + tag + " does not exist");
        }

        specialityRepository.deleteById(tag);
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialityRepository.findAll();
    }
}
