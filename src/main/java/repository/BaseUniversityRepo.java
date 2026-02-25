package repository;

import domain.University;
import repository.interfaces.DefaultRepository;
import service.interfaces.UniversityServiceInt;

public abstract class BaseUniversityRepo<T, ID> implements DefaultRepository<T, ID> {
    protected final UniversityServiceInt universityService;

    protected BaseUniversityRepo(UniversityServiceInt universityService) {
        this.universityService = universityService;
    }

    protected University getUniversity() {
        return universityService.getUniversity();
    }
}
