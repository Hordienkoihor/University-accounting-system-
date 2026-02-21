package repository;

import domain.Student;
import domain.records.StudentId;
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
        universityService.getUniversity().addPerson(student);
    }

    @Override
    public Student findById(StudentId id) {
        return universityService.getUniversity().findStudentById(id);
    }

    @Override
    public boolean existsById(StudentId id) {
        return findById(id) != null;
    }

    @Override
    public Student deleteById(StudentId id) {
        Student student = findById(id);

        if (student != null) {
            /// transferred to service using group service
//            universityService.getUniversity().getFacultyList().stream()
//                    .flatMap(f -> f.getSpecialtyList().stream())
//                    .flatMap(s -> s.getGroups().stream())
//                    .forEach(group -> group.removeStudent(student));

            return universityService.getUniversity().removeStudent(student);
        }

        System.out.println("Student not found");
        return null;
    }

    @Override
    public Map<StudentId, Student> getAll() {
        return universityService.getUniversity().getStudentsAsMap();
    }
}
