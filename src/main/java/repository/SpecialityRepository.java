package repository;

import domain.Specialty;
import repository.interfaces.SpecialityRepositoryInt;
import repository.io.PersistenceService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SpecialityRepository implements SpecialityRepositoryInt {
    private Map<String, Specialty> specialtyMap = new ConcurrentHashMap<>();
    private final PersistenceService<Specialty> persistence = new PersistenceService<>(Specialty.class, "specialties.json");

    public SpecialityRepository() {
        List<Specialty> loadedData = persistence.loadAll();
        loadedData.forEach(s -> specialtyMap.put(s.getTag(), s));
    }

    @Override
    public void save(Specialty specialty) {
        specialtyMap.put(specialty.getTag(), specialty);
        persistence.saveAll(findAll());
    }


//    @Override
//    public Specialty findByTag(String tag) {
//        return facultyService.getAllAsList().stream()
//                .flatMap(f -> f.getSpecialtyList().stream())
//                .filter(s -> s.getTag().equalsIgnoreCase(tag))
//                .findFirst()
//                .orElse(null);
//    }

    @Override
    public Optional<Specialty> findByName(String name) {
        return specialtyMap.values().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

//    @Override
//    public boolean existsByTag(String tag) {
//        return findByTag(tag) != null;
//    }

    @Override
    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

//    @Override
//    public void deleteByTag(String tag) {
//        facultyService.getAllAsList().forEach(f -> f.removeSpecialty(tag));
//    }

    @Override
    public List<Specialty> findAllOnDepartment(String code) {
        return new ArrayList<>(
                specialtyMap
                        .values()
                        .stream()
                        .filter(
                                specialty -> specialty
                                        .getDepartment()
                                        .getCode()
                                        .equals(code)
                        ).toList()
        );
    }

    @Override
    public Optional<Specialty> findById(String tag) {
        return Optional.ofNullable(specialtyMap.get(tag));
    }

    @Override
    public boolean existsById(String tag) {
        return findById(tag).isPresent();
    }

    @Override
    public List<Specialty> findAll() {
        return specialtyMap
                .values()
                .stream()
                .toList();
    }

    @Override
    public void deleteById(String tag) {
        specialtyMap.remove(tag);
        persistence.saveAll(findAll());
    }


}
