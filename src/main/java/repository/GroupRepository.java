package repository;

import domain.Group;
import domain.Specialty;
import repository.interfaces.GroupRepositoryInt;
import service.interfaces.SpecialityServiceInt;

import java.util.List;
import java.util.Optional;

public class GroupRepository implements GroupRepositoryInt {
    private final SpecialityServiceInt specialityService;

    public GroupRepository(SpecialityServiceInt specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public void save(String specialtyTag, Group group) {
        Specialty specialty = specialityService.findByTag(specialtyTag);

        if (specialty != null) {
            specialty.getGroups().add(group);
        }
    }

    @Override
    public void save(Group entity) {
        throw new UnsupportedOperationException("Use save(specialtyTag, group) instead");
    }

    @Override
    public Optional<Group> findById(String name) {
        return specialityService.getAllSpecialties()
                .stream()
                .flatMap(s -> s.getGroups().stream())
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean existsById(String s) {
        return findById(s).isPresent();
    }

    @Override
    public List<Group> findAll() {
        return specialityService.getAllSpecialties().stream()
                .flatMap(specialty -> specialty.getGroups().stream())
                .toList();
    }

    @Override
    public void deleteById(String name) {
        specialityService.getAllSpecialties().
                forEach(s -> s.getGroups().
                        removeIf(g -> g.getName().equalsIgnoreCase(name)));
    }

    @Override
    public List<Group> findAllBySpecialty(String specialtyTag) {
        Specialty specialty = specialityService.findByTag(specialtyTag);
        if (specialty != null) {
            return specialty.getGroups();
        }

        return List.of();
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
