package repository.interfaces;

import domain.Group;
import domain.records.StudentId;

import java.util.List;

public interface GroupRepositoryInt extends DefaultRepository<Group, String> {
    void save(String specialtyTag, Group group);


    List<Group> findAllBySpecialty(String specialtyTag);

    default Group findByName(String name) {
        return findById(name);
    }

    default boolean existsByName(String name) {
        return findByName(name) != null;
    }
}