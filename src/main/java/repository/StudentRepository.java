package repository;

import domain.Faculty;
import domain.Student;
import domain.records.StudentId;
import repository.interfaces.StudentRepositoryInt;
import repository.io.PersistenceService;
import service.interfaces.UniversityServiceInt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StudentRepository implements StudentRepositoryInt {
    private final Map<StudentId, Student> studentMap = new ConcurrentHashMap<>();
    private final PersistenceService<Student> persistence = new PersistenceService<>(Student.class, "students.json");

    public StudentRepository() {
        List<Student> loadedData = persistence.loadAll();
        loadedData.forEach(s -> studentMap.put(s.getStudentId(), s));
    }

    @Override
    public void save(Student student) {
        studentMap.put(student.getStudentId(), student);
        persistence.saveAll(findAll());
    }

    @Override
    public Optional<Student> findById(StudentId id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    @Override
    public boolean existsById(StudentId id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(studentMap.values());
    }

    @Override
    public void deleteById(StudentId id) {
        studentMap.remove(id);
        /// transferred to service using group service
        //            universityService.getUniversity().getFacultyList().stream()
        //                    .flatMap(f -> f.getSpecialtyList().stream())
        //                    .flatMap(s -> s.getGroups().stream())
        //                    .forEach(group -> group.removeStudent(student));

        persistence.saveAll(findAll());
    }

    public Map<StudentId, Student> getAll() {
        return new HashMap<>(studentMap);
    }
}
