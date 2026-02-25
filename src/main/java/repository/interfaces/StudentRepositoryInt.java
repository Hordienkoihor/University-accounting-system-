package repository.interfaces;

import domain.Student;
import domain.records.StudentId;

import java.util.Map;

public interface StudentRepositoryInt extends DefaultRepository<Student, StudentId> {
    Map<StudentId, Student> getAll();
}
