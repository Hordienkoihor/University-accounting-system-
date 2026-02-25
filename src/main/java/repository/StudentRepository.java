package repository;

import domain.Student;
import domain.records.StudentId;
import repository.interfaces.StudentRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentRepository extends BaseUniversityRepo<Student, StudentId> implements StudentRepositoryInt {

    public StudentRepository(UniversityServiceInt universityServiceInt) {
        super(universityServiceInt);
    }

    @Override
    public void save(Student student) {
        getUniversity().addPerson(student);
    }

    @Override
    public Optional<Student> findById(StudentId id) {
        return getUniversity().findStudentById(id);
    }

    @Override
    public boolean existsById(StudentId id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Student> findAll() {
        return getUniversity().getStudentsAsList();
    }

    @Override
    public void deleteById(StudentId id) {
        Optional<Student> student = findById(id);
        student.ifPresent(value -> getUniversity().removeStudent(value));
        /// transferred to service using group service
        //            universityService.getUniversity().getFacultyList().stream()
        //                    .flatMap(f -> f.getSpecialtyList().stream())
        //                    .flatMap(s -> s.getGroups().stream())
        //                    .forEach(group -> group.removeStudent(student));
    }

    public Map<StudentId, Student> getAll() {
        return getUniversity().getStudentsAsMap();
    }
}
