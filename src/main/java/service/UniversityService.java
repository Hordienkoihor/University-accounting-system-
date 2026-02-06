package service;

import domain.University;
import repository.UniversityRepository;
import repository.interfaces.UniversityRepositoryInt;
import service.interfaces.UniversityServiceInt;

public class UniversityService implements UniversityServiceInt {
    private UniversityRepositoryInt universityRepository;

    public UniversityService(UniversityRepositoryInt universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public boolean isUniversityLoaded() {
        return universityRepository.isInitialised();
    }

    @Override
    public void loadUniversity(String path) {
        universityRepository.load(path);
    }

    @Override
    public void createUniversity(String fullName, String shortName, String city, String address) {
        universityRepository.save(new University(fullName, shortName, city, address));

    }

    @Override
    public University getUniversity() {
        return universityRepository.get();
    }
}
