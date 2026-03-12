package repository;

import domain.Faculty;
import domain.Student;
import domain.records.StudentId;
import repository.interfaces.StudentRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.util.*;

public class StudentRepository implements StudentRepositoryInt {
    private final Map<StudentId, Student> studentMap = new HashMap<>();


    public StudentRepository() {
    }

    @Override
    public void save(Student student) {
        studentMap.put(student.getStudentId(), student);
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
        Optional<Student> student = findById(id);
        student.ifPresent(value -> studentMap.remove(value.getStudentId()));
        /// transferred to service using group service
        //            universityService.getUniversity().getFacultyList().stream()
        //                    .flatMap(f -> f.getSpecialtyList().stream())
        //                    .flatMap(s -> s.getGroups().stream())
        //                    .forEach(group -> group.removeStudent(student));
    }

    public Map<StudentId, Student> getAll() {
        return new HashMap<>(studentMap);
    }
}
