package repository.interfaces;

import domain.Student;

import java.util.Map;

public interface StudentRepositoryInt {
    void save(Student student);

    Student findById(int id);
    boolean existsById(int id);

    Student deleteById(int id);

    Map<Integer, Student> getAll();
}
