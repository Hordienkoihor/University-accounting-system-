package repository;

import domain.University;
import repository.interfaces.UniversityRepositoryInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UniversityRepository implements UniversityRepositoryInt {
    private University university = null;

    @Override
    public void save(University university) {
        this.university = university;
    }

    @Override
    public University get() {
        return university;
    }

    @Override
    public boolean isInitialised() {
        return university != null;
    }

    @Override
    public void load(String path) {
        University result = null;

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                String[] parts = line.split(",");

                switch (parts[0]) {
                    case "UNIVERSITY":
                        result = new University(parts[1], parts[2], parts[3], parts[4]);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.university = result;
    }
}
