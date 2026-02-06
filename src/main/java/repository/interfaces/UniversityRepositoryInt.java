package repository.interfaces;

import domain.University;

public interface UniversityRepositoryInt {
    void save(University university);
    University get();
    boolean isInitialised();
    void load(String path);

}
