package repository.interfaces;

import domain.University;

public interface UniversityRepository {
    void save(University university);
    boolean exists();
    University find();
}
