package service;

import domain.University;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.UniversityRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.nio.file.Files;
import java.nio.file.Path;

public class UniversityService implements UniversityServiceInt {
    private final UniversityRepositoryInt universityRepository;

    private static final Logger log = LoggerFactory.getLogger(UniversityService.class);

    public UniversityService(UniversityRepositoryInt universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public void save(University university) {
        log.info("Saving university data: {}", university.getFullName());
        universityRepository.save(university);
    }

    @Override
    public boolean isUniversityLoaded() {
        boolean initialized = universityRepository.isInitialised();
        log.debug("University loaded status check: {}", initialized);
        return initialized;
    }

    @Override
    public void loadUniversity(String path) {
        log.info("Attempting to load university from path: {}", path);
        Path filePath = Path.of(path);

        if (!Files.exists(filePath)) {
            log.error("Load failed: File does not exist at path '{}'", path);
            return;
        }

        try {
            universityRepository.load(path);
            log.info("University successfully loaded from {}", path);
        } catch (Exception e) {
            log.error("Unexpected error during university loading: {}", e.getMessage(), e);
        }
    }

    @Override
    public void createUniversity(String fullName, String shortName, String city, String address) {
        log.info("Creating new university: {} ({})", fullName, shortName);
        universityRepository.save(new University(fullName, shortName, city, address));
        log.info("University created and saved successfully");
    }

    @Override
    public University getUniversity() {
        log.debug("Fetching university object");
        return universityRepository.get();
    }
}
