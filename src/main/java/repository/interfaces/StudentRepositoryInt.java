package repository.interfaces;

import domain.Student;
import domain.records.StudentId;

import java.util.Map;

public interface StudentRepositoryInt {
    void save(Student student);

    Student findById(StudentId id);
    boolean existsById(StudentId id);

    Student deleteById(StudentId id);

    Map<StudentId, Student> getAll();
}
