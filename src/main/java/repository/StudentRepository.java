package repository;

import domain.Student;
import repository.interfaces.StudentRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.util.Map;

public class StudentRepository implements StudentRepositoryInt {
    private final UniversityServiceInt universityService;

    public StudentRepository(UniversityServiceInt universityServiceInt) {
        this.universityService = universityServiceInt;
    }

    @Override
    public void save(Student student) {
        universityService.getUniversity().addStudent(student);
    }

    @Override
    public Student findById(int id) {
        return universityService.getUniversity().findStudentById(id);
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    @Override
    public Student deleteById(int id) {
        Student student = findById(id);

        if (student != null) {
            universityService.getUniversity().getFacultyList().stream()
                    .flatMap(f -> f.getSpecialtyList().stream())
                    .flatMap(s -> s.getGroups().stream())
                    .forEach(group -> group.removeStudent(student));

            return universityService.getUniversity().removeStudent(student);
        }

        System.out.println("Student not found");
        return null;
    }

    @Override
    public Map<Integer, Student> getAll() {
        return universityService.getUniversity().getStudentsAsMap();
    }
}
