package repository;

import domain.Faculty;
import domain.Student;
import domain.University;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import repository.interfaces.UniversityRepositoryInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
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
            boolean loadedUniversity = false;
            for (String line : lines) {
                String[] parts = line.split(",");


                switch (parts[0]) {
                    case "UNIVERSITY": {
                        if (!loadedUniversity) {
                            result = new University(parts[1], parts[2], parts[3], parts[4]);
                            loadedUniversity = true;
                        }

                        break;
                    }
                    case "FACULTY": {
                        if (loadedUniversity) {
                            if (parts.length >= 3) {
                                result.addFaculty(new Faculty(parts[1], parts[2]));
                            }
                        }
                        break;
                    }
                    case "STUDENT": {
                        loadStudent(loadedUniversity, parts, result);
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.university = result;
    }

    private static void loadStudent(boolean loadedUniversity, String[] parts, University result) {
        if (loadedUniversity) {
            if (parts.length < 6) {
                return;
            }

            if (parts.length < 8) {
                StudyForm studyForm = parts[7].equalsIgnoreCase(StudyForm.TUITION.getDisplayName())
                        ? StudyForm.TUITION
                        : StudyForm.TUITION_FREE;


                result.addPerson(new Student(
                        parts[1],
                        parts[2],
                        parts[3],
                        Integer.parseInt(parts[4]),
                        parts[5],
                        parts[6],
                        new Date(),
                        studyForm,
                        StudyStatus.PENDING));

            } else {
                StudyStatus studyStatus = StudyStatus.PENDING;

                StudyForm studyForm = parts[7].equalsIgnoreCase(StudyForm.TUITION.getDisplayName())
                        ? StudyForm.TUITION
                        : StudyForm.TUITION_FREE;

                switch (parts[8].toUpperCase()) {
                    case "STUDYING": {
                        studyStatus = StudyStatus.STUDYING;
                        break;
                    }
                    case "EXPELLED": {
                        studyStatus = StudyStatus.EXPELLED;
                        break;
                    }
                    case "ACADEMIC_LEAVE": {
                        studyStatus = StudyStatus.ACADEMIC_LEAVE;
                        break;
                    }
                    case "PENDING": {
                        studyStatus = StudyStatus.PENDING;
                        break;
                    }
                    default: {
                        break;
                    }


                }

                result.addPerson(new Student(
                        parts[1],
                        parts[2],
                        parts[3],
                        Integer.parseInt(parts[4]),
                        parts[5],
                        parts[6],
                        new Date(),
                        studyForm,
                        studyStatus
                ));
            }
        }
    }
}
