package Utilitys;

import domain.Faculty;
import domain.Student;
import domain.University;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import service.interfaces.FacultyServiceInt;
import service.interfaces.StudentServiceInt;
import service.interfaces.UniversityServiceInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ConfigLoader {
    private final UniversityServiceInt universityService;
    private final FacultyServiceInt facultyService;
    private final StudentServiceInt studentService;

    public ConfigLoader(UniversityServiceInt uniService, StudentServiceInt studService, FacultyServiceInt facService) {
        this.universityService = uniService;
        this.facultyService = facService;
        this.studentService = studService;
    }

    private void loadStudent(String[] parts) {
        if (parts.length < 8) return;

        try {
            int[] dateArray = Arrays.stream(parts[6].split("\\."))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            LocalDate dob = LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);


            StudyForm studyForm;
            try {
                studyForm = StudyForm.valueOf(parts[7].toUpperCase());
            } catch (IllegalArgumentException e) {
                studyForm = StudyForm.TUITION_FREE;
            }

            StudyStatus studyStatus = StudyStatus.PENDING;
            if (parts.length > 8) {
                try {
                    studyStatus = StudyStatus.valueOf(parts[8].toUpperCase());
                } catch (IllegalArgumentException e) {
                    studyStatus = StudyStatus.PENDING;
                }
            }

            Student student = new Student(
                    parts[1], // name
                    parts[2], // surname
                    parts[3], // fatherName
                    parts[4], // email
                    parts[5], // phoneNumber
                    dob,
                    studyForm,
                    studyStatus
            );

            studentService.add(student);

        } catch (Exception e) {
            System.err.println("Помилка обробки рядка студента: " + String.join(",", parts));
            System.err.println("Причина: " + e.getMessage());
        }
    }

    public void load(String path) {
        University university = null;

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");

                switch (parts[0].toUpperCase()) {
                    case "UNIVERSITY" -> {
                        university = new University(parts[1], parts[2], parts[3], parts[4]);
                        universityService.save(university);
                    }
                    case "FACULTY" -> {
                        facultyService.register(new Faculty(parts[1], parts[2]));
                    }
                    case "STUDENT" -> {
                        loadStudent(parts);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}