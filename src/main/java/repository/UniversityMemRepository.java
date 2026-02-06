package repository;

import domain.University;
import repository.interfaces.UniversityRepository;

public class UniversityMemRepository implements UniversityRepository {
    @Override
    public void save(University university) {

    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public University find() {
        return null;
    }
}
