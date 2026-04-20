package repository;

import domain.Department;
import domain.Faculty;
import domain.Group;
import domain.Specialty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.dto.FacultyDto;
import repository.dto.GroupDto;
import repository.interfaces.GroupRepositoryInt;
import repository.io.PersistenceService;
import repository.mapper.GroupMapper;
import service.interfaces.SpecialityServiceInt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GroupRepository implements GroupRepositoryInt {
    private final Map<String, Group> groupMap = new ConcurrentHashMap<>();
    private final PersistenceService<GroupDto> persistence = new PersistenceService<>(GroupDto.class, "groupsDto.json");
    private final GroupMapper mapper = new GroupMapper();

    private final Logger log = LoggerFactory.getLogger(GroupRepository.class);

    public GroupRepository() {
        log.info("Initializing GroupRepository");
//        List<Group> loadedData = persistence.loadAll();
//        loadedData.forEach(g -> groupMap.put(g.getName(), g));
//        log.info("Initialized GroupRepository with {} groups", groupMap.size());
    }

    public void initData(List<Group> loadedGroups) {
        loadedGroups.forEach(g -> groupMap.put(g.getName(), g));
        log.info("Initialized GroupRepository with {} groups", groupMap.size());
    }

    public List<GroupDto> loadRawDtos() {
        return persistence.loadAll();
    }

    private void saveToFile() {
        List<GroupDto> dtos = groupMap.values().stream()
                .map(mapper::toDto)
                .toList();
        persistence.saveAll(dtos);
    }

    @Override
    public void save(Group group) {
        groupMap.put(group.getName(), group);
        log.debug("Group with name {} saved to memory", group.getName());
        saveToFile();

    }

    @Override
    public Optional<Group> findById(String name) {
        return Optional.ofNullable(groupMap.get(name));
    }

    @Override
    public boolean existsById(String s) {
        return findById(s).isPresent();
    }

    @Override
    public List<Group> findAll() {
        return groupMap.values().stream().toList();
    }

    @Override
    public void deleteById(String name) {
        if (groupMap.remove(name) != null) {
            log.debug("Group with name {} removed from memory", name);
            saveToFile();
        } else {
            log.debug("Attempted to delete non-existing group with name: {}", name);
        }

    }

    @Override
    public List<Group> findAllBySpecialty(String specialtyTag) {
        return new ArrayList<>(
                groupMap
                        .values()
                        .stream()
                        .filter(group -> group.getSpecialty().getTag().equals(specialtyTag))
                        .toList()
        );
    }

//    @Override
//    public Group findByName(String name) {
//        return specialityService.getAllSpecialties()
//                .stream()
//                .flatMap(s -> s.getGroups().stream())
//                .filter(g -> g.getName().equalsIgnoreCase(name))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public boolean existsByName(String name) {
//        return findByName(name) != null;
//    }
//

//
//    @Override
//    public void deleteByName(String name) {
//        specialityService.getAllSpecialties().
//                forEach(s -> s.getGroups().
//                        removeIf(g -> g.getName().equalsIgnoreCase(name)));
//    }

}
