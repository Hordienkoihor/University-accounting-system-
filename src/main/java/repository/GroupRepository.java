package repository;

import domain.Group;
import domain.Specialty;
import repository.interfaces.GroupRepositoryInt;
import repository.io.PersistenceService;
import service.interfaces.SpecialityServiceInt;

import java.util.*;
import java.util.stream.Collectors;

public class GroupRepository implements GroupRepositoryInt {
    private final Map<String, Group> groupMap = new HashMap<>();
    private final PersistenceService<Group> persistence = new PersistenceService<>(Group.class, "groups.json");

    public GroupRepository() {
        persistence.loadAll().forEach(this::save);
    }

    @Override
    public void save(Group group) {
        groupMap.put(group.getName(), group);
        persistence.saveAll(findAll());
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
        groupMap.remove(name);
        persistence.saveAll(findAll());
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
