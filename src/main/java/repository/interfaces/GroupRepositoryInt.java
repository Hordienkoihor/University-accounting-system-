package repository.interfaces;

import domain.Group;
import java.util.List;

public interface GroupRepositoryInt {
    void save(String specialtyTag, Group group);

    Group findByName(String name);
    boolean existsByName(String name);


    List<Group> findAllBySpecialty(String specialtyTag);

    void deleteByName(String name);
}