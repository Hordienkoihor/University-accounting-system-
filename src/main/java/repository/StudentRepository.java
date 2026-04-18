package repository;

import domain.Faculty;
import domain.Student;
import domain.records.StudentId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.StudentRepositoryInt;
import repository.io.PersistenceService;
import service.interfaces.UniversityServiceInt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StudentRepository implements StudentRepositoryInt {
    private final Map<StudentId, Student> studentMap = new ConcurrentHashMap<>();
    private final PersistenceService<Student> persistence = new PersistenceService<>(Student.class, "students.json");

    private final Logger log = LoggerFactory.getLogger(StaffRepository.class);

    public StudentRepository() {
        log.info("Initializing StudentRepository");
        List<Student> loadedData = persistence.loadAll();
        loadedData.forEach(s -> studentMap.put(s.getStudentId(), s));
        log.info("Initialized StudentRepository with {} students", studentMap.size());
    }

    @Override
    public void save(Student student) {
        studentMap.put(student.getStudentId(), student);
        log.debug("Student with studentId {} saved to memory", student.getStudentId());
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
        /// transferred to service using group service
        //            universityService.getUniversity().getFacultyList().stream()
        //                    .flatMap(f -> f.getSpecialtyList().stream())
        //                    .flatMap(s -> s.getGroups().stream())
        //                    .forEach(group -> group.removeStudent(student));

        if (studentMap.remove(id) != null) {
            log.debug("Student with studentId {} deleted from memory", id);
            persistence.saveAll(findAll());
        } else {
            log.debug("Attempted to delete non-existing student with id: {}", id);
        }


    }

    public Map<StudentId, Student> getAll() {
        return new HashMap<>(studentMap);
    }
}
