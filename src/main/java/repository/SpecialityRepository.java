package repository;

import domain.Group;
import domain.Specialty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.dto.GroupDto;
import repository.dto.SpecialityDto;
import repository.interfaces.SpecialityRepositoryInt;
import repository.io.PersistenceService;
import repository.mapper.SpecialityMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SpecialityRepository implements SpecialityRepositoryInt {
    private Map<String, Specialty> specialtyMap = new ConcurrentHashMap<>();
    private final PersistenceService<SpecialityDto> persistence = new PersistenceService<>(SpecialityDto.class, "specialtiesDto.json");
    private final SpecialityMapper mapper = new SpecialityMapper();

    private final Logger log = LoggerFactory.getLogger(SpecialityRepository.class);

    public SpecialityRepository() {
        log.info("Initializing SpecialityRepository");
//        List<Specialty> loadedData = persistence.loadAll();
//        loadedData.forEach(s -> specialtyMap.put(s.getTag(), s));
//        log.info("Initialized SpecialityRepository with {} specialities", specialtyMap.size());
    }

    public void initData(List<Specialty> loadedSpecialties) {
        loadedSpecialties.forEach(s -> specialtyMap.put(s.getTag(), s));
        log.info("Initialized SpecialityRepository with {} specialities", specialtyMap.size());
    }

    public List<SpecialityDto> loadRawDtos() {
        return persistence.loadAll();
    }

    private void saveToFile() {
        List<SpecialityDto> dtos = specialtyMap.values().stream()
                .map(mapper::toDto)
                .toList();
        persistence.saveAll(dtos);
    }

    @Override
    public void save(Specialty specialty) {
        specialtyMap.put(specialty.getTag(), specialty);
        log.debug("Speciality with tag {} saved to memory", specialty.getTag());
        saveToFile();
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
        if (specialtyMap.remove(tag) != null) {
            log.debug("Speciality with tag {} deleted from the memory", tag);
            saveToFile();
        } else {
            log.debug("Attempted to delete non-existing speciality with tag: {}", tag);
        }

    }


}
