package service.interfaces;

import domain.Group;
import java.util.List;

public interface GroupServiceInt {
    void registerGroup(String specialtyTag, String groupName);

    Group findByName(String name);
    List<Group> findAllBySpecialty(String specialtyTag);
    List<Group> findAll();

    void updateName(String oldName, String newName);
    void deleteByName(String name);
}